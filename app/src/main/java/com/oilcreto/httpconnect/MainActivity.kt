package com.oilcreto.httpconnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.util.Log
import android.widget.Button
import android.widget.Toast
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val btnValidarRed = findViewById<Button>(R.id.btnValidarRed)
        val btnSolicitudHTTP = findViewById<Button>(R.id.btnSolicitudHTTP)

        btnValidarRed.setOnClickListener {
            if(Network.hayRed(this)){
                Toast.makeText(this, "Tenemos red Chavales", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "No red :'v efe we", Toast.LENGTH_SHORT).show()
            }
        }

        btnSolicitudHTTP.setOnClickListener {
            if(Network.hayRed(this)){
                Log.d("btnSolicitudOnClick",descargarDato("https://www.google.com"))
                Toast.makeText(this,"Si valio we",Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "No red :'v efe we", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @Throws(IOException::class)
    private fun descargarDato(url:String):String{

        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        var inputStream:InputStream? = null

        try {
            val url = URL(url)
            val conexion = url.openConnection() as HttpURLConnection
            conexion.requestMethod = "GET"
            conexion.connect()

            inputStream = conexion.inputStream
            return inputStream.bufferedReader().use {
                it.readText()
            }
        }finally {
            if(inputStream != null){
                inputStream.close()
            }
        }
    }
}