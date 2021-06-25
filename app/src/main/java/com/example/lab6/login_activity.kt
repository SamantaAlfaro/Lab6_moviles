package com.example.lab6

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricManager
import android.hardware.biometrics.BiometricPrompt
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.Button
import android.widget.Toast

class login_activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        var Auth_btn : Button = findViewById<Button>(R.id.Auth_btn)

        Auth_btn.setOnClickListener{
            showBiometricPrompt()
        }

    }


    fun showBiometricPrompt(){
        //fist, check if the device support Biometric prompt API
        if(packageManager.hasSystemFeature(PackageManager.FEATURE_FINGERPRINT)){
            val prompt = BiometricPrompt.Builder(this)
                .setTitle("Autentciación")
                .setDescription("Usa tu huella para ingresar a la app")
                .setNegativeButton("Cancelar", mainExecutor, DialogInterface.OnClickListener { _, _ ->  })
                .build()

            prompt.authenticate(getCancellationSignal(), mainExecutor,
                object: BiometricPrompt.AuthenticationCallback(){
                    override fun onAuthenticationSucceeded(
                        result: BiometricPrompt.AuthenticationResult?) {
                        super.onAuthenticationSucceeded(result)
                        //Action success display here
                        fingerPrintAction()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        badFingerPrintAction()
                    }
                }

            )
        }
    }

    fun fingerPrintAction(){
        Toast.makeText(this, "Scanner de huella exitoso", Toast.LENGTH_LONG).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
    fun badFingerPrintAction(){
        Toast.makeText(this, "Scanner de huella fallido", Toast.LENGTH_LONG).show()
        //val intent = Intent(this, MainActivity::class.java)
        //startActivity(intent)
    }



    fun getCancellationSignal(): CancellationSignal{
        //with this signal, biometric prompt operations get cancel

        val cancellationSignal= CancellationSignal()
        cancellationSignal.setOnCancelListener {
            //handle result action if you want here

        }
        return cancellationSignal
    }

}