package com.example.gario

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.gario.databinding.ActivityRegistroGastoBinding
import android.app.DatePickerDialog
import android.graphics.Color
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import java.util.Calendar


class registro_gasto : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroGastoBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroGastoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnAceptar.isEnabled = false

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
                R.id.nav_inicio -> { /* Acción */
                }

                R.id.nav_historial -> { /* Acción */
                }

                R.id.nav_informes -> { /* Acción */
                }
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        val calendar = Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val fechaSeleccionada = "${dayOfMonth}/${month + 1}/${year}"
            binding.editFecha.setText(fechaSeleccionada)
        }

        binding.editFecha.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.iconCalendar.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }
// VARIABLES PARA RECIBIR LOS DATOS DESDE ACTIVITY "INICIO_GARIO" PARA EL SPINNER
// QUEDA PENDIENTE EL DE AUTO-RELLENADO DE LOS CAMPOS
        val tiposGasto = intent.getStringArrayListExtra("tiposGasto") ?: arrayListOf()
        val metodosPago = intent.getStringArrayListExtra("metodosPago") ?: arrayListOf()

        val adapterTipos = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            tiposGasto
        )
        adapterTipos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerTipoGasto.adapter = adapterTipos

        val adapterMetodos = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            metodosPago
        )
        adapterMetodos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerMetodoPago.adapter = adapterMetodos

        binding.editMotivo.doAfterTextChanged { texto ->
            val monto = texto.toString()
            if (monto.length > 200) {
                binding.editMotivo.error = "No se permiten más de 200 caracteres"
                binding.btnAceptar.isEnabled = false
                binding.btnAceptar.setBackgroundColor(Color.GRAY)
            } else {
                binding.editMotivo.error = null
                binding.btnAceptar.setBackgroundColor(ContextCompat.getColor(this, R.color.color_naranja))
            }
        }


    }


}
