package com.example.gario

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gario.databinding.ActivityRegistroGarioUserBinding
import androidx.core.widget.doAfterTextChanged
import android.content.Intent
import android.widget.Toast
import android.graphics.Color
import android.util.Log
import androidx.core.content.ContextCompat

// IMPORTS NECESARIOS PARA RETROFIT
import com.example.gario.conexion.ClienteRetrofit
import com.example.gario.conexion.UserRegister
import com.example.gario.conexion.MessageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.log


class registro_gario_user : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroGarioUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroGarioUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Validaciones en tiempo real
        binding.nombreUsuario.doAfterTextChanged { validarCampos() }
        binding.email.doAfterTextChanged { validarCampos() }
        binding.contrasena.doAfterTextChanged { validarCampos() }
        binding.confirmarContrasena.doAfterTextChanged { validarCampos() }
        binding.edad.doAfterTextChanged { validarCampos() }

        binding.registrate.isEnabled = false

        binding.registrate.setOnClickListener {

            val nombreUsuario = binding.nombreUsuario.text.toString().trim()
            val email = binding.email.text.toString().trim()
            val edadInt = binding.edad.text.toString().toIntOrNull() ?: 0
            val contrasena = binding.contrasena.text.toString()

            // Crear objeto UserRegister con los datos del formulario
            val user = UserRegister(
                username = nombreUsuario,
                email = email,
                psw = contrasena,
                edad = edadInt
            )
// USO DE LAS COMPROBACIONES DE CONEXION
            ClienteRetrofit.instance.ping().enqueue(object : Callback<MessageResponse> {
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    if (response.isSuccessful) {
                        Log.d("Ping", "Respuesta: ${response.body()?.mensaje}")
                    } else {
                        Log.e("Ping", "Falló con código ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Log.e("Ping", "Error: ${t.message}")
                }
            })

            // Llamar al endpoint signup con Retrofit
            ClienteRetrofit.instance.signup(user).enqueue(object : Callback<MessageResponse> {
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    if (response.isSuccessful) {
                        val mensaje = response.body()?.mensaje ?: "Registro exitoso"
                        Toast.makeText(this@registro_gario_user, mensaje, Toast.LENGTH_SHORT).show()

                        // Redirigir al login
                        val intent = Intent(this@registro_gario_user, Inicio_sesion_user::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this@registro_gario_user, "Error en el registro", Toast.LENGTH_SHORT).show()

                    }
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Toast.makeText(this@registro_gario_user, "Fallo: ${t.message}", Toast.LENGTH_LONG).show()
                    Log.e("RegistroError", "Fallo en la conexión: ${t.message}", t)

                }
            })
        }

        // CREE UN BOTON PARA HACER EL PING EN LA IMAGEN, SOLO POR TEST
        binding.Garioping.setOnClickListener {
            ClienteRetrofit.ping.ping().enqueue(object : Callback<MessageResponse> {
                override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                    if (response.isSuccessful) {
                        Toast.makeText(this@registro_gario_user, "Conexión OK: ${response.body()?.mensaje}", Toast.LENGTH_SHORT).show()
                        Log.d("Ping", "Respuesta: ${response.body()?.mensaje}")
                    } else {
                        Toast.makeText(this@registro_gario_user, "Error en el backend: ${response.code()}", Toast.LENGTH_SHORT).show()
                        Log.e("Ping", "Error HTTP: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                    Toast.makeText(this@registro_gario_user, "Fallo de conexión: ${t.message}", Toast.LENGTH_LONG).show()
                    Log.e("Ping", "Fallo al conectar: ${t.message}", t)
                }
            })
        }

        binding.iniciarSesion.setOnClickListener {
            val intent = Intent(this, Inicio_sesion_user::class.java)
            startActivity(intent)
        }


    }

    private fun validarCampos() {
        val nombreUsuario = binding.nombreUsuario.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val edadStr = binding.edad.text.toString()
        val contrasena = binding.contrasena.text.toString()
        val confirmarContrasena = binding.confirmarContrasena.text.toString()

        val emailValido = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val camposLlenos = nombreUsuario.isNotEmpty() && email.isNotEmpty() && edadStr.isNotEmpty() && contrasena.isNotEmpty() && confirmarContrasena.isNotEmpty()
        val contrasIguales = contrasena == confirmarContrasena
        val contrasenaValida = contrasena.length >= 8

        // Validar email
        if (!emailValido && email.isNotEmpty()) {
            binding.email.error = "Correo inválido"
        } else {
            binding.email.error = null
        }

        // Validar edad de forma segura
        if (edadStr.isEmpty()) {
            binding.edad.error = "Ingresa tu Edad"
        } else {
            val edadInt = edadStr.toIntOrNull()
            if (edadInt == null || edadInt <= 0 || edadInt >= 100) {
                binding.edad.error = "Edad inválida"
            } else {
                binding.edad.error = null
            }
        }

        // Validar contraseñas
        if (!contrasIguales && confirmarContrasena.isNotEmpty()) {
            binding.confirmarContrasena.error = "Las contraseñas no coinciden"
        } else {
            binding.confirmarContrasena.error = null
        }

        if (!contrasenaValida && contrasena.isNotEmpty()) {
            binding.contrasena.error = "Mínimo 8 caracteres"
        } else {
            binding.contrasena.error = null
        }

        // Habilitar botón
        val todoValido = emailValido && camposLlenos && contrasIguales && contrasenaValida && binding.edad.error == null
        binding.registrate.isEnabled = todoValido

        // Cambiar color del botón
        if (todoValido) {
            binding.registrate.setBackgroundColor(ContextCompat.getColor(this, R.color.gario_naranja))
        } else {
            binding.registrate.setBackgroundColor(Color.GRAY)
        }

    }
}
