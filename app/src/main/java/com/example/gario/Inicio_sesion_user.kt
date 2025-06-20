package com.example.gario

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.gario.databinding.ActivityInicioSesionUserBinding

class Inicio_sesion_user : AppCompatActivity() {
   private lateinit var binding: ActivityInicioSesionUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioSesionUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
