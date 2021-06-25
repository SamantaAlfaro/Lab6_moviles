package com.example.lab6

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnCapture: ImageButton = findViewById(R.id.btn_camera)

        btnCapture!!.setOnClickListener {
            val i = Intent(this, image_options::class.java)
            startActivity(i)
        }
    }
}