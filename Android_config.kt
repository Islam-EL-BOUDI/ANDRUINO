package com.example.bluetootharduino

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.io.IOException
import java.io.OutputStream
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var btnConnect: Button
    private lateinit var btnSend: Button
    private lateinit var tvStatus: TextView

    private var bluetoothAdapter: BluetoothAdapter? = null
    private var bluetoothSocket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null

    // Replace this with your HC-05 or HC-06 device address
    private val deviceAddress: String = "00:11:22:33:44:55"
    private val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // Standard SerialPortService ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnConnect = findViewById(R.id.btnConnect)
        btnSend = findViewById(R.id.btnSend)
        tvStatus = findViewById(R.id.tvStatus)

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        btnConnect.setOnClickListener {
            connectToBluetoothDevice()
        }

        btnSend.setOnClickListener {
            sendData("Hello, Arduino!")
        }
    }

    @SuppressLint("MissingPermission")
    private fun connectToBluetoothDevice() {
        val device: BluetoothDevice? = bluetoothAdapter?.getRemoteDevice(deviceAddress)

        try {
            // Create a Bluetooth socket for communication
            bluetoothSocket = device?.createRfcommSocketToServiceRecord(uuid)
            bluetoothSocket?.connect()

            outputStream = bluetoothSocket?.outputStream

            tvStatus.text = "Status: Connected"
            btnSend.isEnabled = true

        } catch (e: IOException) {
            Log.e("Bluetooth", "Error connecting to device", e)
            tvStatus.text = "Status: Connection Failed"
        }
    }

    private fun sendData(data: String) {
        try {
            outputStream?.write(data.toByteArray())
            tvStatus.text = "Status: Data Sent"
        } catch (e: IOException) {
            Log.e("Bluetooth", "Error sending data", e)
            tvStatus.text = "Status: Send Failed"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        try {
            bluetoothSocket?.close()
        } catch (e: IOException) {
            Log.e("Bluetooth", "Error closing socket", e)
        }
    }
}
