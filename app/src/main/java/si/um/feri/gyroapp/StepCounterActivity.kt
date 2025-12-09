package si.um.feri.gyroapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import si.um.feri.gyroapp.databinding.ActivityStepCounterBinding

class StepCounterActivity : AppCompatActivity(), SensorEventListener {

    private var sensorManager: SensorManager? = null
    lateinit var binding: ActivityStepCounterBinding
    private var totalSteps = 0f
    private var initialSteps = -1f


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityStepCounterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }

    override fun onResume() {
        super.onResume()

        val stepCounter = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        val stepDetector = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR)

        if (stepCounter == null) {
            Toast.makeText(this, "TYPE_STEP_COUNTER missing!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "TYPE_STEP_COUNTER available!", Toast.LENGTH_SHORT).show()
        }

        if (stepDetector == null) {
            Toast.makeText(this, "TYPE_STEP_DETECTOR missing!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "TYPE_STEP_DETECTOR available!", Toast.LENGTH_SHORT).show()
        }

        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Toast.makeText(this, "No sensor detected!", Toast.LENGTH_SHORT).show()
        }
        else {
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {

        if (event == null) {
            binding.tvStepCounter.text = "NULL"

            return
        }

        val steps = event.values[0]

        if (initialSteps < 0) {
            initialSteps = steps
        }

        val currentSteps = steps - initialSteps

        binding.tvStepCounter.text = currentSteps.toInt().toString()
        binding.cpbStepCounter.setProgressWithAnimation(currentSteps)
    }

}