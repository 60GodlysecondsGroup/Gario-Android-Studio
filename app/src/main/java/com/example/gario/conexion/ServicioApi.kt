package com.example.gario.conexion

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// MODELOS DE DATOS
data class UserRegister(
    val name_user: String,
    val email: String,
    val psw: String,
    val edad: Int
)

data class UserLogin(
    val email: String,
    val psw: String
)

data class LoginResponse(
    val auth: Boolean,
    val token: String
)

data class MessageResponse(
    val mensaje: String
)

// INTERFACE DE ENDPOINTS
interface ServicioApi {

    @POST("signup")
    fun signup(@Body user: UserRegister): Call<MessageResponse>

    @POST("login")
    fun login(@Body user: UserLogin): Call<LoginResponse>
}
