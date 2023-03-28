package com.example.coordinador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.coordinador.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET


class MainActivity : AppCompatActivity() {

    private lateinit var Binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        Binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(Binding.root)

        obtenerDatos(Binding.textView)

    }


    interface ApiService {
        @GET("UCA/mostrarC.php")
        suspend fun obtenerDatos(): ResponseBody
    }

    object RetrofitClient {
        private const val BASE_URL = "http://10.0.2.2:80/"
        private val client = OkHttpClient.Builder().build()


        val apiService: ApiService = Retrofit.Builder().baseUrl(BASE_URL).client(client)
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(ApiService::class.java)
    }

    fun obtenerDatos(textView: TextView) {
        CoroutineScope(Dispatchers.IO).launch {
            var response: ResponseBody? = null
            try {
                response = RetrofitClient.apiService.obtenerDatos()
                val jsonResponse = response.string() // Extrae la respuesta JSON como cadena de texto
                val jsonObject = JSONObject(jsonResponse) // Crea un objeto JSON a partir de la cadena de texto
                val jsonFormatted = jsonObject.toString(4) // Convierte el objeto JSON a una cadena de texto con formato legible
                Log.d("TAG", "Solicitud HTTP realizada correctamente")
                withContext(Dispatchers.Main) {
                    textView.text = jsonFormatted // Muestra la respuesta en el TextView
                }
            } catch (e: Exception) {
                Log.e("TAG", "Error al realizar la solicitud HTTP", e)
                // Maneja el error si es necesario
            } finally {
                response?.close()
            }
        }

    }
}