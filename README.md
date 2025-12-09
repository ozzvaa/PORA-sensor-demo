# Android Sensor Framework
---

## Utemeljitev izbire

Večina Android naprav ima danes vgrajenih veliko število različnih senzorjev, ki omogočajo
pridobivanje podatkov in meritev neposredno z naprave. Te podatke lahko uporabimo v najrazličnejših
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

## Prednosti in slabosti

| Prednosti                                                               | Slabosti                                                                     |
|-------------------------------------------------------------------------|------------------------------------------------------------------------------|
| + Vgrajen API — ni dodatnega zunanjih knjižnic.                         | - Senzorji posameznih naprav se razlikujejo med proizvajalci.                |
| + Širok nabor senzorjev.                                                | - Nekateri senzorji ne delujejo v emulatorju.                                |
| + Optimizirano za nizko porabo energije.                                | - Točnost podatkov variira med različnimi telefoni.                          |
| + Dober nadzor nad frekvenco posodobitev (SENSOR_DELAY_*).              | - Power-saving načini pogosto onemogočajo senzorje (Samsung, Xiaomi).        |
| + Del standardnega Android SDK → dobra dokumentacija in kompatibilnost. | - Ročno upravljanje z življenjskim ciklom (registracija/odjava listenerjev). |
| + Dogodkovni model omogoča delo v realnem času.                         | —                                                                            |

## Licenca
Android Open Source Project (AOSP)
    
Apache License 2.0 

## Število uporabnikov
Ni ločena knjižnica ampak je del Android sistema, zato ga v določeni meri uporabljajo vse Android naprave.
Število uporabnikov/naprav je tako ocenjeno na okoli 4 milijarde.

## Časovna in prostorska zahtevnost
- Časovna zahtevnost: O(1) - dogodki prihajajo asinhrono preko sistema, ki jih zgolj posreduje aplikaciji.
- Prostorska zahtevnost: zelo majhna, saj uporablja nativne sistemske storitve naprave.


## Vzdrževanje - št. razvijalcev, zadnja sprememba
- Aktivno vzdrževan, razvijajo ga razvijalci AOSP projekta
- Posodobljen z vsako posodobitvijo Android sistema
- Zadnja posodobitev: 10. 6. 2025 (Android 16)

---

## Sensor Demo

- osnovne funkcionalnosti
- izjeme

