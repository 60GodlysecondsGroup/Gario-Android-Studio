package com.example.gario

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.widget.doAfterTextChanged
import com.example.gario.databinding.ActivityRegistroIngresoBinding

class registro_ingreso : AppCompatActivity() {

    private lateinit var binding: ActivityRegistroIngresoBinding
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroIngresoBinding.inflate(layoutInflater)
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


        val calendar = java.util.Calendar.getInstance()

        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
            val fechaSeleccionada = "${dayOfMonth}/${month + 1}/${year}"
            binding.editFecha.setText(fechaSeleccionada)
        }

        binding.editFecha.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(java.util.Calendar.YEAR),
                calendar.get(java.util.Calendar.MONTH),
                calendar.get(java.util.Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.iconCalendar.setOnClickListener {
            DatePickerDialog(
                this,
                dateSetListener,
                calendar.get(java.util.Calendar.YEAR),
                calendar.get(java.util.Calendar.MONTH),
                calendar.get(java.util.Calendar.DAY_OF_MONTH)
            ).show()
        }

        // VARIABLES PARA RECIBIR LOS DATOS DESDE ACTIVITY "INICIO_GARIO"
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
