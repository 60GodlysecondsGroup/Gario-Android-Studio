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


        binding.nombreUsuario.doAfterTextChanged { validarCampos() }
        binding.email.doAfterTextChanged { validarCampos() }
        binding.contrasena.doAfterTextChanged { validarCampos() }
        binding.confirmarContrasena.doAfterTextChanged { validarCampos() }


        binding.btnRegistro.isEnabled = false

        // Cuando se presiona el botón como CAMAVALINGO
        binding.btnRegistro.setOnClickListener {
            Toast.makeText(this, "Registro exitoso. Ahora inicia sesión.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, Inicio_sesion_user::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun validarCampos() {
        val nombreUsuario = binding.nombreUsuario.text.toString().trim()
        val email = binding.email.text.toString().trim()
        val contrasena = binding.contrasena.text.toString()
        val confirmarContrasena = binding.confirmarContrasena.text.toString()

        val emailValido = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val camposLlenos = nombreUsuario.isNotEmpty() && email.isNotEmpty() && contrasena.isNotEmpty() && confirmarContrasena.isNotEmpty()
        val contrasIguales = contrasena == confirmarContrasena
        val contrasenaValida = contrasena.length >= 6

        // Mostrar errores si existen en el Registro Gario
        if (!emailValido && email.isNotEmpty()) {
            binding.email.error = "Correo inválido"
        } else {
            binding.email.error = null
        }

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

        val todoValido = emailValido && camposLlenos && contrasIguales && contrasenaValida
        binding.btnRegistro.isEnabled = todoValido

        // Cambia el color del botón  (Especifico esto lo hizo el todopoderoso no yo)
        binding.btnRegistro.setBackgroundColor(
            if (todoValido) ContextCompat.getColor(this, R.color.gario_naranja) else Color.GRAY
        )
    }







}

