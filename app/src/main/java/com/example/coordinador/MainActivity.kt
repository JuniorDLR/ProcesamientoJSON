package com.example.coordinador

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import com.example.coordinador.databinding.ActivityMainBinding
import com.google.gson.JsonArray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import org.json.JSONArray
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

                val cadena = response.toString()
                val objectsJ =JSONObject(cadena)
                val datos = objectsJ.getJSONArray("Data")

                var Rid:String=""
                var Rnombre:String=""
                var Rapellidos:String=""
                var RfechaNac:String=""
                var Rtitulo:String=""
                var Remail:String=""
                var Rfacultad:String=""

                for (i in 0 until  datos.length()){

                    val respuesta = datos.getJSONObject(i)

                    val id = respuesta.getString("idC")
                    val nombre = respuesta.getString("nombres")
                    val apellidos = respuesta.getString("apellidos")
                    val fechaNac = respuesta.getString("fechaNac")
                    val titulo = respuesta.getString("titulo")
                    val email = respuesta.getString("email")
                    val facultad = respuesta.getString("facultad")

                    Rid = id
                    Rnombre =nombre
                    Rapellidos=apellidos
                    RfechaNac =fechaNac
                    Rtitulo =titulo
                    Remail=email
                    Rfacultad=facultad
                }

                val resultado ="Nombre: $Rid"


                Log.d("TAG", "Solicitud HTTP realizada correctamente")
                withContext(Dispatchers.Main) {
                    textView.text = resultado // Muestra la respuesta en el TextView
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