package com.example.gario.conexion

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

// MODELOS DE DATOS

/* Aqui es donde los nombres despues del "val" tienen que ser igual
 a los nombres de los campos en la base de datos*/


data class UserRegister(
    val username: String,
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

data class userInfoResponse(
    val message: String,
    val user_id: Int
)

//MODELO DE DATOS PARA CATALOGO

data class tipoGasto( // MODELO DE DATOS DIFERENTE AL DE INGRESOS
    val ID: Int,
    val CLASE: String,
    val NOMBRE : String,
)
// Las Categorias de Gastos y Metodos de Pago de ahi viene "CM"
data class CMGastoIngreso( //MODELO DE DATOS PARA GASTOS Y INGRESOS // YA QUE USAN EL MISMO MODELO DE DATOS
    val SECCION: Int,
    val ID: Int,
    val NOMBRE : String,
)

data class tiposIngreso( // MODELO DE DATOS DIFERENTE AL DE GASTOS
    val id: Int,
    val clase: Int,
    val nombre : String,
)


// INTERFACE DE ENDPOINTS
interface UserApi {

    @POST("signup")
    fun signup(@Body user: UserRegister): Call<MessageResponse>

    @POST("login")
    fun login(@Body user: UserLogin): Call<LoginResponse>

    // NUEVO ENDPOINT PARA EL PARA VERIFICAR PROBLEMAS CON LA VERIFICACION.
    // ME DIO MUCHOS PROBLEMAS ASI QUE LA HICE. (PROXIMAMENTE BORRARLA)

    @GET("ping")
    fun ping(): Call<MessageResponse>

    @GET("logout")
    fun logout(): Call<MessageResponse>

    @GET("me")
    fun getUserInfo(): Call<userInfoResponse>

}
// ENDPOINTS DE CATALOGO, DECIDI DEJARLOS APARTE :P
interface CatalogoApi {

    @GET("tiposGastos")
    fun tiposGastos(): Call<List<tipoGasto>>

    @GET("CMGastos")
    fun CMGastos(): Call<List<CMGastoIngreso>>

    @GET("tiposIngresos")
    fun tiposIngresos(): Call<tiposIngreso>

    @GET("CMIngresos")
    fun CMIngresos(): Call<List<CMGastoIngreso>>

}

interface MovimientosApi {






}
