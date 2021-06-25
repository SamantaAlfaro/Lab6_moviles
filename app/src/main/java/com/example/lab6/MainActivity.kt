package com.example.lab6

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var btnCapture: ImageButton = findViewById(R.id.btn_camera)

        btnCapture!!.setOnClickListener {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    100)

                //val i = Intent(this, image_options::class.java)
                //startActivity(i)
            }

            Handler().postDelayed(Runnable {
                val i = Intent(this, image_options::class.java)
                startActivity(i)
            }, 2000)

        }

    }


}