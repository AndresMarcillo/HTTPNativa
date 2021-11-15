package com.oilcreto.httpconnect

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class MainActivity : AppCompatActivity(), CompletadoListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        val btnValidarRed = findViewById<Button>(R.id.btnValidarRed)
        val btnSolicitudHTTP = findViewById<Button>(R.id.btnSolicitudHTTP)
        val btnSolicitudHTTPVolley = findViewById<Button>(R.id.btnSolicitudHTTPVolley)
        val btnSolicitudHTTPOkHTTP = findViewById<Button>(R.id.btnSolicitudHTTPOkHTTP)

        btnValidarRed.setOnClickListener {
            if(Network.hayRed(this)){
                Toast.makeText(this, "Tenemos red Chavales", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "No red :'v efe we", Toast.LENGTH_SHORT).show()
            }
        }

        btnSolicitudHTTP.setOnClickListener {
            if(Network.hayRed(this)){
                /*Log.d("btnSolicitudOnClick",descargarDato("https://www.google.com"))*/
                DescargaURL(this).execute("https://www.google.com")
                Toast.makeText(this, "Funcionó we", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "No red :'v efe we", Toast.LENGTH_SHORT).show()
            }
        }

        btnSolicitudHTTPVolley.setOnClickListener{
            if(Network.hayRed(this)){
                solicitudHTTPVolley("https://www.google.com")
            }else{
                Toast.makeText(this, "No red :'v efe we", Toast.LENGTH_SHORT).show()
            }
        }

        btnSolicitudHTTPOkHTTP.setOnClickListener{
            if(Network.hayRed(this)){
                solicitudHTTPOkHTTP("https://www.google.com")
            }else{
                Toast.makeText(this, "No red :'v efe we", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun descargaCompleta(resultado: String) {
        Log.d("descargaCompleta", resultado)
    }

    //Método para Volley
    private fun solicitudHTTPVolley(url:String){
        val queue = Volley.newRequestQueue(this)

        val solicitud = StringRequest(Request.Method.GET, url, {
            response ->
            try {
                Log.d("solicitudHTTOVolley", response)
            }catch (e:Exception){

            }
        }, {  })

        queue.add(solicitud)
    }

    //Método para OkHTTP
    private fun solicitudHTTPOkHTTP(url:String){
        val cliente = OkHttpClient()
        val solicitud = okhttp3.Request.Builder().url(url).build()

        cliente.newCall(solicitud).enqueue(object: okhttp3.Callback{
            override fun onFailure(call: Call, e: IOException) {
                //Implementar error
            }

            override fun onResponse(call: Call, response: Response) {
                val result = response.body()?.string()

                this@MainActivity.runOnUiThread{
                    try {
                        Log.d("solicitudHTTPOkHTTP", result.toString())
                    }catch (e: Exception){

                    }
                }
            }
        })
    }

}