package com.example.gario

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gario.databinding.ActivityInicioSesionPrincipalGarioBinding

class inicio_sesion_principal_gario : AppCompatActivity() {

    private lateinit var binding: ActivityInicioSesionPrincipalGarioBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflar el layout usando ViewBinding
        binding = ActivityInicioSesionPrincipalGarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Botón para iniciar sesión
        binding.btnLogin.setOnClickListener {
            val intent = Intent(this, Inicio_sesion_user::class.java)
            startActivity(intent)
        }

        // Botón para registrarse
        binding.registrate.setOnClickListener {
            val intent = Intent(this, registro_gario_user::class.java)
            startActivity(intent)
        }
    }
}
