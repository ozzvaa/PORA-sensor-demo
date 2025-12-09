# Android Sensor Framework
---

## Utemeljitev izbire

Večina Android naprav ima danes vgrajenih veliko število različnih senzorjev, ki omogočajo
pridobivanje podatkov in meritev neposredno z naprave. Ti podatki so lahko zelo uporabni, uporabimo
pa jih lahko v najrazličnejših
aplikacijah - video igrah, zaznavanju gest, merilnikih...

Platforma Android podpira tri večje skupine senzorjev:

- Senzorji
  gibanja - [Motion sensors](https://developer.android.com/guide/topics/sensors/sensors_motion)
  (pospeškomeri, gravitacijski senzorji, žiroskopi)

- Okoljski
  senzorji - [Environmental sensors](https://developer.android.com/guide/topics/sensors/sensors_environment)
  (merilniki temperature, tlaka, osvetlitve, vlage)

- Senzorji
  pozicije - [Position sensors](https://developer.android.com/guide/topics/sensors/sensors_position)
  (merilniki fizične pozicije naprave, senzorji za orientacijo in magnetomeri)

--- 

## Prednosti in slabosti

| Prednosti                                                               | Slabosti                                                                     |
|-------------------------------------------------------------------------|------------------------------------------------------------------------------|
| + Vgrajen API — ni dodatnega zunanjih knjižnic.                         | - Senzorji posameznih naprav se razlikujejo med proizvajalci.                |
| + Širok nabor senzorjev.                                                | - Nekateri senzorji ne delujejo v emulatorju.                                |
| + Optimizirano za nizko porabo energije.                                | - Točnost podatkov variira med različnimi telefoni.                          |
| + Dober nadzor nad frekvenco posodobitev (SENSOR_DELAY_*).              | - Power-saving načini pogosto onemogočajo senzorje (Samsung, Xiaomi).        |
| + Del standardnega Android SDK → dobra dokumentacija in kompatibilnost. | - Ročno upravljanje z življenjskim ciklom (registracija/odjava listenerjev). |
| + Dogodkovni model omogoča delo v realnem času.                         |                                                                              |

---

## Licenca

- Android Open Source Project (AOSP)

- Apache License 2.0

---

## Število uporabnikov

- Ni ločena knjižnica ampak je del Android sistema, zato ga v določeni meri uporabljajo vse Android
  naprave. Število uporabnikov/naprav je tako ocenjeno na okoli 4 milijarde.

---

## Časovna in prostorska zahtevnost

- Časovna zahtevnost: O(1) - dogodki prihajajo asinhrono preko sistema, ki jih zgolj posreduje
  aplikaciji.
- Prostorska zahtevnost: zelo majhna, saj uporablja nativne sistemske storitve naprave.

---

## Vzdrževanje - št. razvijalcev, zadnja sprememba

- Aktivno vzdrževan, razvijajo ga razvijalci AOSP projekta
- Posodobljen z vsako posodobitvijo Android sistema
- Zadnja posodobitev: 10. 6. 2025 (Android 16)

---

## Primeri uporabe

| **Seznam senzorjev**       |
|----------------------------|
| `TYPE_ACCELEROMETER`       |
| `TYPE_AMBIENT_TEMPERATURE` |
| `TYPE_GRAVITY`             |
| `TYPE_GYROSCOPE`           |
| `TYPE_LIGHT`               |
| `TYPE_LINEAR_ACCELERATION` |
| `TYPE_MAGNETIC_FIELD`      |
| `TYPE_ORIENTATION`         |
| `TYPE_PRESSURE`            |
| `TYPE_PROXIMITY`           |
| `TYPE_RELATIVE_HUMIDITY`   |
| `TYPE_ROTATION_VECTOR`     |
| `TYPE_TEMPERATURE`         |

---

### 1. Pridobivanje SensorManager in določene vrste senzorja

Za začetek mora aplikacija pridobiti `SensorManager` in izbrati senzor, ki ga želi uporabljati.

```kotlin
val sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
val gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
```

---

### 2. Implementacija SensorEventListener

Za delo s senzorji mora razred implementirati metodi:

```onSensorChanged()``` → prejme nove podatke senzorja

```onAccuracyChanged()``` → obvesti o spremembi natančnosti

```kotlin
class MainActivity : AppCompatActivity(), SensorEventListener {

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0] // tilt top-down
            val y = it.values[1] // tilt left-right
            val z = it.values[2] // movement left-right
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // opcijsko
    }
}
```

---

### 3. Registracija listenerja v onResume()

Da aplikacija začne poslušati senzor:

```kotlin
override fun onResume() {
    super.onResume()
    sensorManager.registerListener(
        this,
        accelerometer, // senzor
        SensorManager.SENSOR_DELAY_NORMAL // hitrost osveževanja
    )
}
```

**Frekvence posodabljanja**

| Konstanta              | Namen                              |
|------------------------|------------------------------------|
| `SENSOR_DELAY_NORMAL`  | privzeto, nizka poraba             |
| `SENSOR_DELAY_UI`      | dovolj hitro za UI                 |
| `SENSOR_DELAY_GAME`    | hitrejše posodobitve               |
| `SENSOR_DELAY_FASTEST` | najhitrejše, večja poraba baterije |

---

### 4. Odjava listenerja v onPause()

Da preprečimo izgubo baterije ali “memory leak”:

```kotlin
override fun onPause() {
    super.onPause()
    sensorManager.unregisterListener(this)
}
```

---

## Primer števca korakov
![step_counter_DEMO.gif](Resources/Screen_Recording_20251209_230321_GyroApp_1_1_1.gif)
[StepCounterActivity.kt](app/src/main/java/si/um/feri/gyroapp/StepCounterActivity.kt)
