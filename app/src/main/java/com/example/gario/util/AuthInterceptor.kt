package com.example.gario.util


import okhttp3.Interceptor
import okhttp3.Response

    // INTERCEPTADOR PARA EL TOKEN DEL USUARIO LOGUEADO
    // ESTE SE COMUNICA CON TokenManager PARA VERIFICAR EL TOKEN, GUARDARLO Y RECUPERARLO.
class AuthInterceptor(private val tokenManager: TokenManager): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = tokenManager.getToken()
        val newRequest = if (token != null) {
            chain.request().newBuilder()
                .addHeader("x-access-token", token)
                .build()
        } else {
            chain.request()
        }
        return chain.proceed(newRequest)
    }
}