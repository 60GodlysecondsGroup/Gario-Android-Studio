package com.example.gario

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gario.databinding.ActivityInicioSesionGarioBinding

class Inicio_Sesion_Gario : AppCompatActivity() {

    private lateinit var binding: ActivityInicioSesionGarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enlaza el binding con el layout
        binding = ActivityInicioSesionGarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configura el botón para ir a la pantalla de registro
        binding.btnRegistro.setOnClickListener {
            val intent = Intent(this, registro_gario_user::class.java)
            startActivity(intent)
        }

        // Configura el botón para ir a la pantalla de inicio de sesion
        binding.btnLogin.setOnClickListener{
            val intent = Intent(this, Inicio_sesion_user::class.java)
            startActivity(intent)
        }
    }
}
