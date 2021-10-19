package com.oilcreto.httpconnect

import android.os.AsyncTask
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class DescargaURL(var completadoListener:CompletadoListener?):AsyncTask<String, Void, String>() {
    override fun doInBackground(vararg p0: String): String? {
        try{
            return descargarDato(p0[0])
        }catch (e:IOException){
            return null
        }
    }

    override fun onPostExecute(result: String) {
        try {
            completadoListener?.descargaCompleta(result)
        }catch (e:Exception){

        }
    }

    @Throws(IOException::class)
    private fun descargarDato(url:String):String{

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