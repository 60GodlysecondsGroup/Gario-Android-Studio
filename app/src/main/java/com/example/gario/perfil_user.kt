package com.example.gario
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import com.example.gario.databinding.ActivityPerfilUserBinding



class perfil_user : AppCompatActivity() {

    private lateinit var binding: ActivityPerfilUserBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPerfilUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar Toolbar
        setSupportActionBar(binding.toolbar)

        // Configurar Drawer Toggle
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.abrir_menu,
            R.string.cerrar_menu
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.mi_perfil -> {
                    // Acción para Mi perfil
                    true
                }
                R.id.configuracion -> {
                    // Acción para Configuración
                    true
                }
                R.id.cambio_divisa -> {
                    // Acción para Cambio divisa
                    true
                }
                R.id.cerrar_sesion -> {
                    // Acción para Cerrar sesión
                    cerrar_sesion()
                    true
                }
                R.id.plan_premium -> {
                    // Acción para Plan premium
                    true
                }
                else -> false
            }
        }
    }

    private fun cerrar_sesion() {
        val intent = Intent(this, inicio_sesion_principal_gario::class.java)
        startActivity(intent)
        finish()
    }
}
