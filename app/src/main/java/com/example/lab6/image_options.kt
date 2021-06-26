package com.example.lab6

import android.R.attr.bitmap
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.*
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.graphics.drawable.toBitmap
import java.io.File
import java.io.FileOutputStream
import java.util.*
import kotlin.Exception


class image_options : AppCompatActivity(), LocationListener, SensorEventListener{

    var imgCapture: ImageView? = null
    val Image_Capture_Code = 1
    var description: TextView? = null
    var location_manager: LocationManager? = null
    var share: Button? = null


    //ambient temp sensor
    private lateinit var sensorManager: SensorManager
    private lateinit var tempSensor: Sensor
    private var tempSensorBool: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image_options)



        sensorManager= getSystemService(Context.SENSOR_SERVICE) as SensorManager
        if(sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)!=null){
            tempSensor= sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
            tempSensorBool=true
        }else{
            tempSensorBool=false
            Toast.makeText(this, "Temperature Sensor isn´t available", Toast.LENGTH_LONG).show()
        }


        imgCapture = findViewById(R.id.preview_image)
        share = findViewById(R.id.send)

        val cInt = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cInt, Image_Capture_Code)
        getLocation()


        share!!.setOnClickListener {
            val bitmap = imgCapture!!.getDrawable().toBitmap()
            shared(bitmap)
        }


    }


    private fun shared(bitmap: Bitmap){
        val uri: Uri = image(bitmap)
        val intent = Intent(Intent.ACTION_SEND)

        intent.putExtra(Intent.EXTRA_STREAM, uri)

        intent.putExtra(Intent.EXTRA_TEXT, "Sharing Image")

        intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")

        intent.type = "image/png"

        startActivity(Intent.createChooser(intent, "Share Via"))
    }



    private fun image(bitmap: Bitmap): Uri{

        var imagefolder: File = File(getCacheDir(), "images");
        var uri: Uri? = null
        try {
            imagefolder.mkdirs();
            var file = File(imagefolder, "shared_image.png");
            var outputStream = FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, outputStream);
            outputStream.flush();
            outputStream.close();
            uri = FileProvider.getUriForFile(this, "com.anni.shareimage.fileprovider", file);
        } catch (e: Exception) {
            Toast.makeText(this, "" + e.message, Toast.LENGTH_LONG).show();
        }
        return uri!!
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


    override fun onResume() {
        super.onResume()
        // Register a listener for the sensor.
        if(tempSensorBool){
            sensorManager.registerListener(this, tempSensor, SensorManager.SENSOR_DELAY_NORMAL)

        }

    }

    override fun onPause() {
        super.onPause()
        if(tempSensorBool){
            sensorManager.unregisterListener(this, tempSensor)
        }
    }


    override fun onSensorChanged(event: SensorEvent?) {
        var tmp: TextView = findViewById(R.id.temperature_txv)
        tmp!!.text = event!!.values[0].toString()+" °C"
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        return
    }




}