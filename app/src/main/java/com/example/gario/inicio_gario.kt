package com.example.gario

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.gario.databinding.ActivityInicioGarioBinding
import com.example.gario.databinding.ItemMovimientoBinding
import android.content.Intent


class inicio_gario : AppCompatActivity() {

    private lateinit var binding: ActivityInicioGarioBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioGarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

            // Configurar Toolbar
            setSupportActionBar(binding.toolbar)

            // Configurar DrawerToggle
            toggle = ActionBarDrawerToggle(
                this,
                binding.drawerLayout,
                binding.toolbar,
                R.string.abrir_menu,
                R.string.cerrar_menu
            )
            binding.drawerLayout.addDrawerListener(toggle)
            toggle.syncState()

        // Menú lateral (Navigation Drawer)
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_inicio -> { /* Acción */ }
                R.id.nav_historial -> { /* Acción */ }
                R.id.nav_informes -> { /* Acción */ }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }


        // Datos simulados
        val montoActual = 120000
        val gastosMes = 32000

        val movimientos = listOf(
            Triple("Transferencia", "pavos del fornais", -10000),
            Triple("Efectivo", "Apuesta de quien perdía el semestre", 20000),
            Triple("Tarjeta", "Gasolina de la moto", -12000)
        )

        // Mostrar monto y gastos
        binding.textMonto.text = getString(R.string.monto_actual, montoActual)
        binding.textGastos.text = getString(R.string.gastos_mes, gastosMes)

        // Mostrar movimientos usando ViewBinding del item_movimiento.xml
        val inflater = layoutInflater
        for (mov in movimientos) {
            val itemBinding = ItemMovimientoBinding.inflate(inflater, binding.listaMovimientos, false)

            itemBinding.tituloMovimiento.text = mov.first
            itemBinding.subtituloMovimiento.text = mov.second
            itemBinding.montoMovimiento.text = getString(R.string.monto_formato, mov.third)

            // Colorear dependiendo del signo
            if (mov.third < 0) {
                itemBinding.montoMovimiento.setTextColor(Color.RED)
            } else {
                itemBinding.montoMovimiento.setTextColor(Color.parseColor("#0F0D3E"))
            }

            binding.listaMovimientos.addView(itemBinding.root)
        }
    }

    private fun cerrar_sesion(){
        val intent = Intent(this, inicio_sesion_principal_gario::class.java)
        startActivity(intent)
        finish()
    }

    // Inflar el menú superior (icono de usuario y más opciones)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_usuario, menu)
        return true
    }

    // Manejar clics en el menú superior
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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
                // Acción par a Cerrar sesión
                cerrar_sesion()
                true
            }
            R.id.plan_premium -> {
                // Acción para Plan premium
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
