package si.um.feri.gyroapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.widget.TextView
import androidx.core.content.ContextCompat
import si.um.feri.gyroapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), SensorEventListener {
    private lateinit var binding: ActivityMainBinding

    private lateinit var sensorManager: SensorManager
    private var gyro: Sensor? = null

    private lateinit var tvGyroData: TextView
    private lateinit var tvDirection: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        tvGyroData = binding.gyroText
        tvDirection = binding.direction
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val x = it.values[0] // tilt top-down
            val y = it.values[1] // tilt left-right
            val z = it.values[2] // movement left-right

            // Update text
            tvGyroData.text = "Gyroscope:\nX: $x\nY: $y\nZ: $z"

            // Determine direction from the Z-axis rotation
            val directionThreshold = 1.0f  // adjust sensitivity

            for (axis in it.values) {


                when {
                    axis > directionThreshold -> {
                        // Left rotation (counter-clockwise)
                        binding.root.setBackgroundColor(
                            ContextCompat.getColor(this, android.R.color.holo_blue_dark)
                        )
                        when {
                            axis == x -> {
                                tvDirection.text = "Tilting UP"
                            }

                            axis == y -> {
                                tvDirection.text = "Tilting RIGHT"
                            }

                            axis == z -> {
                                tvDirection.text = "Moving LEFT"
                            }
                        }
                    }

                    axis < -directionThreshold -> {
                        // Right rotation (clockwise)
                        binding.root.setBackgroundColor(
                            ContextCompat.getColor(this, android.R.color.holo_purple)
                        )
                        when {
                            axis == x -> {
                                tvDirection.text = "Tilting DOWN"
                            }

                            axis == y -> {
                                tvDirection.text = "Tilting LEFT"
                            }

                            axis == z -> {
                                tvDirection.text = "Moving RIGHT"
                            }
                        }
                    }


                    else -> {
                        // Not rotating noticeably
                        binding.root.setBackgroundColor(
                            ContextCompat.getColor(this, android.R.color.holo_green_dark)
                        )
                    }
                }
            }
        }

    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // not needed
    }
}