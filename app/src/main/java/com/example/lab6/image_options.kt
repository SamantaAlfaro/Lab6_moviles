package com.example.lab6

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.location.*
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.Exception


class image_options : AppCompatActivity(), LocationListener {

    var imgCapture: ImageView? = null
    val Image_Capture_Code = 1
    var description: TextView? = null
    var location_manager: LocationManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_options)



        imgCapture = findViewById(R.id.preview_image)

        val cInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cInt, Image_Capture_Code)
        getLocation()


    }


    @SuppressLint("MissingPermission")
    private fun getLocation() {
        try {
            location_manager = applicationContext.getSystemService(LOCATION_SERVICE) as LocationManager
            location_manager!!.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5f,this)
        }catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == Image_Capture_Code) {
            if (resultCode == RESULT_OK) {

                val bp = data!!.extras!!["data"] as Bitmap?
                imgCapture!!.setImageBitmap(bp)

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show()
                val i = Intent(this, MainActivity::class.java)
                startActivity(i)
            }
        }
    }

    override fun onLocationChanged(location: Location) {
        //Toast.makeText(this,"Latitud: "+location.latitude+"\nLongitud: "+location.longitude,Toast.LENGTH_SHORT).show()

        try {
            var position: Geocoder = Geocoder(this, Locale.getDefault())
            var address: List<Address> = position.getFromLocation(location.latitude,location.longitude,1)
            var text_address: String = address.get(0).getAddressLine(0)


            description = findViewById(R.id.tv_description)
            description!!.text = text_address


        }catch (e: Exception){
            e.printStackTrace()
        }

    }
}