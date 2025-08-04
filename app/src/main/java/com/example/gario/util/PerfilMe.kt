package com.example.gario.util

import android.content.Context
import com.example.gario.conexion.ClienteRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.example.gario.conexion.userInfoResponse

// FUTURO TEST NO EN USO

object PerfilHelper {
    fun verificarToken(context: Context, onSuccess: (Int) -> Unit, onError: (String) -> Unit) {
        val api = ClienteRetrofit.instance

        api.getUserInfo().enqueue(object : Callback<userInfoResponse> {
            override fun onResponse(call: Call<userInfoResponse>, response: Response<userInfoResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    onSuccess(response.body()!!.user_id)
                } else {
                    onError("Código de error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<userInfoResponse>, t: Throwable) {
                onError("Fallo: ${t.localizedMessage}")
            }
        })
    }
}
