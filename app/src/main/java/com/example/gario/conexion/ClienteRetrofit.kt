package com.example.gario.conexion

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ClienteRetrofit {

    private const val BASE_URL = "http://10.0.2.2:3000/user/"

    val instance: ServicioApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(ServicioApi::class.java)
    }
}
