package com.example.gario.conexion

//
import android.app.Application
import com.example.gario.util.AuthInterceptor
import com.example.gario.util.TokenManager
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}

object ClienteRetrofit {

    private const val BASE_URL = "http://10.0.2.2:3000/user/"
    // NUEVAS RUTAS DE CONEXION CON EL BACKEND
    private const val PING_URL = "http://10.0.2.2:3000/"
    private const val MOVIMIENTOS_URL = "http://10.0.2.2:3000/movimientos/"
    private const val CATALOGO_URL = "http://10.0.2.2:3000/catalogo/"

    // AQUI SE CREA LA INSTANCIA DEL RETROFIT
    // AGREGUE EL INTERCEPTOR PARA LA VERIFICACION DEL TOKEN DE AUTENTICACION
    // ERA PRUEBA DE QUE SE RECIBIA Y GUARDABA EL TOKEN UNA VEZ INCIA SESION
    val instance: UserApi by lazy {
        val tokenManager = TokenManager(App.instance.applicationContext)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(AuthInterceptor(tokenManager))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(UserApi::class.java)
    }

    //  PARTE DE LA PRUEBA QUE MANDA EL PING AL BACKEND Y MUESTRA EL TOKEN RECIBIDO
    val ping: UserApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(PING_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(UserApi::class.java)
    }

    // AUN NO IMPLEMENTADO
    val movimientos: MovimientosApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(MOVIMIENTOS_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(MovimientosApi::class.java)
    }
    // SE CREA LA INSTANCIA DEL RETROFIT PARA EL CATALOGO
    val catalogo: CatalogoApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(CATALOGO_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(CatalogoApi::class.java)
    }

}
