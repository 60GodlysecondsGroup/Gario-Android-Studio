package com.example.gario

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.gario.databinding.ActivityInicioGarioBinding
import com.example.gario.databinding.ItemMovimientoBinding

class inicio_gario : AppCompatActivity() {

    private lateinit var binding: ActivityInicioGarioBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInicioGarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

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

        // Mostrar monto
        binding.textMonto.text = getString(R.string.monto_actual, montoActual)
        binding.textGastos.text = getString(R.string.gastos_mes, gastosMes)

        // Mostrar movimientos
        val inflater = layoutInflater
        for (mov in movimientos) {
            val itemBinding = ItemMovimientoBinding.inflate(inflater, binding.listaMovimientos, false)

            itemBinding.tituloMovimiento.text = mov.first
            itemBinding.subtituloMovimiento.text = mov.second
            itemBinding.montoMovimiento.text = getString(R.string.monto_formato, mov.third)

            if (mov.third < 0) {
                itemBinding.montoMovimiento.setTextColor(Color.RED)
            } else {
                itemBinding.montoMovimiento.setTextColor(Color.parseColor("#0F0D3E"))
            }

            binding.listaMovimientos.addView(itemBinding.root)
        }
    }
}
