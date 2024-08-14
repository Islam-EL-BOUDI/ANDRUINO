package com.example.sensordata

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.coroutines.*

class Sensors : AppCompatActivity(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    private lateinit var lineChart: LineChart

    private val sensorDataX = mutableListOf<Entry>()
    private val sensorDataY = mutableListOf<Entry>()
    private val sensorDataZ = mutableListOf<Entry>()

    private var time = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lineChart = findViewById(R.id.lineChart)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)

        setupChart()
    }

    private fun setupChart() {
        val data = LineData()
        lineChart.data = data
        lineChart.setTouchEnabled(true)
        lineChart.setPinchZoom(true)
    }

    override fun onSensorChanged(event: SensorEvent) {
        if (event.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            time += 0.1f

            val xValue = event.values[0]
            val yValue = event.values[1]
            val zValue = event.values[2]

            sensorDataX.add(Entry(time, xValue))
            sensorDataY.add(Entry(time, yValue))
            sensorDataZ.add(Entry(time, zValue))

            val dataSetX = LineDataSet(sensorDataX, "X-axis")
            dataSetX.color = ColorTemplate.getHoloBlue()
            dataSetX.setDrawCircles(false)
            dataSetX.lineWidth = 2f

            val dataSetY = LineDataSet(sensorDataY, "Y-axis")
            dataSetY.color = ColorTemplate.JOYFUL_COLORS[0]
            dataSetY.setDrawCircles(false)
            dataSetY.lineWidth = 2f

            val dataSetZ = LineDataSet(sensorDataZ, "Z-axis")
            dataSetZ.color = ColorTemplate.JOYFUL_COLORS[1]
            dataSetZ.setDrawCircles(false)
            dataSetZ.lineWidth = 2f

            val data = LineData(dataSetX, dataSetY, dataSetZ)
            lineChart.data = data
            lineChart.invalidate()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // You can ignore this for now
    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }
}
