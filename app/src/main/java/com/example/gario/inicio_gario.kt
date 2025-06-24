package com.example.gario

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import com.example.gario.databinding.ActivityInicioGarioBinding

class inicio_gario : AppCompatActivity() {

    private lateinit var binding: ActivityInicioGarioBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioGarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar como ActionBar
        setSupportActionBar(binding.toolbar)

        // Icono de las tres rayitas (toggle)
        toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.abrir_menu,
            R.string.cerrar_menu
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Acciones cuando se toca un ítem del menú
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_inicio -> {
                    // Acción para Inicio
                }
                R.id.nav_historial -> {
                    // Acción para Historial
                }
                R.id.nav_informes -> {
                    // Acción para Informes
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
    }
}
