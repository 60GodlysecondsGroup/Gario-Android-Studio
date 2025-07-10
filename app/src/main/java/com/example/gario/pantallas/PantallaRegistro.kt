package com.example.gario.pantallas

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.gario.conexion.ClienteRetrofit
import com.example.gario.conexion.UserRegister
import com.example.gario.conexion.MessageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun PantallaRegistro() {

    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var psw by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {

        TextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre") })
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = email, onValueChange = { email = it }, label = { Text("Email") })
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = psw, onValueChange = { psw = it }, label = { Text("Contraseña") })
        Spacer(modifier = Modifier.height(8.dp))

        TextField(value = edad, onValueChange = { edad = it }, label = { Text("Edad") })
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            val user = UserRegister(nombre, email, psw, edad.toIntOrNull() ?: 0)
            ClienteRetrofit.instance.signup(user).enqueue(object : Callback<MessageResponse> {
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    if (response.isSuccessful) {
                        mensaje = response.body()?.mensaje ?: "Registrado"
                    } else {
                        mensaje = "Error en registro: ${response.code()} - ${response.errorBody()?.string()}"
                    }
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    mensaje = "Fallo: ${t.message}"
                }
            })
        }) {
            Text("Registrarse")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = mensaje)
    }
}
