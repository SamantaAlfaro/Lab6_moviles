package com.example.lab6

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.ByteArrayOutputStream


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