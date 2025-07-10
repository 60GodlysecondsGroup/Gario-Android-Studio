package com.example.gario

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gario.conexion.ClienteRetrofit
import com.example.gario.conexion.UserLogin
import com.example.gario.conexion.LoginResponse
import com.example.gario.databinding.ActivityInicioSesionUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Inicio_sesion_user : AppCompatActivity() {

    private lateinit var binding: ActivityInicioSesionUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioSesionUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val psw = binding.contrasena.text.toString().trim()

            if (email.isNotEmpty() && psw.isNotEmpty()) {
                val userLogin = UserLogin(email, psw)

                ClienteRetrofit.instance.login(userLogin).enqueue(object : Callback<LoginResponse> {
                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            if (loginResponse != null && loginResponse.auth) {
                                Toast.makeText(this@Inicio_sesion_user, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show()
                                val intent = Intent(this@Inicio_sesion_user, inicio_gario::class.java)
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(this@Inicio_sesion_user, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
                            }
                        } else if (response.code() == 401) {
                            // Código 401 significa "no autorizado", probablemente email o password incorrectos
                            Toast.makeText(this@Inicio_sesion_user, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
                        } else {
                            // Otro código de error
                            Toast.makeText(this@Inicio_sesion_user, "Error en el servidor (${response.code()})", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@Inicio_sesion_user, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })

            } else {
                Toast.makeText(this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        binding.registrate.setOnClickListener {
            val intent = Intent(this, registro_gario_user::class.java)
            startActivity(intent)
        }
    }
}
