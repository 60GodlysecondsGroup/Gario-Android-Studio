package com.example.gario

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.gario.databinding.ActivityRegistroGarioUserBinding
import androidx.core.widget.doAfterTextChanged
import android.content.Intent
import android.widget.Toast
import android.graphics.Color
import androidx.core.content.ContextCompat

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
            Toast.makeText(this, "Registro exitoso. Ahora inicia sesión.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Inicio_sesion_user::class.java)
            startActivity(intent)
            finish()
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
        val contrasenaValida = contrasena.length >= 6

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
            binding.contrasena.error = "Mínimo 6 caracteres"
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
