package com.example.gario

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.gario.databinding.ActivityInicioSesionUserBinding

class Inicio_sesion_user : AppCompatActivity() {

    private lateinit var binding: ActivityInicioSesionUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioSesionUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {
            val email = binding.email.text.toString().trim()
            val password = binding.contrasena.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show()
                val intent = Intent(this, inicio_gario::class.java)
                startActivity(intent)
                finish()
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
