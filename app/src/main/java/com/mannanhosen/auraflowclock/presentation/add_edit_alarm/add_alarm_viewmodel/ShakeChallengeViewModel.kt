package com.mannanhosen.auraflowclock.presentation.add_edit_alarm.add_alarm_viewmodel

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import kotlin.math.sqrt

@HiltViewModel
class ShakeChallengeViewModel @Inject constructor(
    @ApplicationContext private val context: Context
) : ViewModel(), SensorEventListener {

    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    private val requiredShakes = 15

    private val _shakeCount = MutableStateFlow(0)
    val shakeCount: StateFlow<Int> = _shakeCount.asStateFlow()

    private val _isComplete = MutableStateFlow(false)
    val isComplete: StateFlow<Boolean> = _isComplete.asStateFlow()

    private var lastUpdate = 0L
    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f

    fun startListening() {
        if (_isComplete.value) return
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_GAME)
        }
    }

    fun stopListening() {
        sensorManager.unregisterListener(this)
    }

    // ✅ পুরনো Screen কোডের সাথে backward-compatible রাখার জন্য রাখা হলো
    fun isChallengeComplete(): Boolean = _shakeCount.value >= requiredShakes

    override fun onSensorChanged(event: SensorEvent) {
        if (_isComplete.value) return

        val curTime = System.currentTimeMillis()
        if (curTime - lastUpdate > 100) {
            val diffTime = curTime - lastUpdate
            lastUpdate = curTime

            val x = event.values[0]
            val y = event.values[1]
            val z = event.values[2]

            val speed = sqrt(
                ((x - lastX) * (x - lastX) +
                        (y - lastY) * (y - lastY) +
                        (z - lastZ) * (z - lastZ)).toDouble()
            ) / diffTime * 10000

            if (speed > 800) {
                val newCount = (_shakeCount.value + 1).coerceAtMost(requiredShakes)
                _shakeCount.value = newCount

                if (newCount >= requiredShakes) {
                    _isComplete.value = true // ✅ flag true — আগে এটা মিসিং ছিল
                    stopListening()
                }
            }

            lastX = x
            lastY = y
            lastZ = z
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    override fun onCleared() {
        stopListening()
        super.onCleared()
    }
}