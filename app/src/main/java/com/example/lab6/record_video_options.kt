package com.example.lab6

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView

class record_video_options : AppCompatActivity() {

    var video: VideoView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record_video_options)

        video = findViewById(R.id.preview_record)



        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        startActivityForResult(intent, 1)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {

                //val bp = data!!.extras!!["data"] as Bitmap?
                val mediaController = MediaController(this)
                mediaController.setAnchorView(video)

                video!!.setMediaController(mediaController)

                video!!.setVideoURI(data!!.getData())
                video!!.requestFocus()
                video!!.start()

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
        }
    }
}