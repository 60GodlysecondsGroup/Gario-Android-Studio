package com.example.gario

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.gario.databinding.ActivityInicioGarioBinding
import com.example.gario.databinding.ItemMovimientoBinding
import android.content.Intent
import android.content.res.ColorStateList
import android.view.View
// Importe toast para hacer una prueba de respuesta con el back end y verlo mas claramente
// Se va quitar luego
import android.widget.Toast
import androidx.core.content.ContextCompat

// Tambien todas estas librerias se van a quitar luego
// Es tema de conexion con el back-end/endpoints

import com.example.gario.conexion.CMGastoIngreso
import com.example.gario.conexion.ClienteRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


// Cambie el nombre de la clase a Inicio_gario que el debugger tiro un warning por inciar en minuscula
class Inicio_gario : AppCompatActivity() {

    private lateinit var binding: ActivityInicioGarioBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private var menuVisible = false

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

        binding.btnPrincipal.setOnClickListener {

            val naranja = ContextCompat.getColor(this, R.color.gario_naranja)
            val azul = ContextCompat.getColor(this, R.color.azul_btn)

            if (menuVisible) {
                binding.btnMenu.visibility = View.GONE
                binding.btnPrincipal.setImageResource(R.drawable.dinero)
                binding.btnPrincipal.backgroundTintList = ColorStateList.valueOf(azul)
                menuVisible = false
            } else {
                binding.btnMenu.visibility = View.VISIBLE
                binding.btnPrincipal.backgroundTintList = ColorStateList.valueOf(naranja)
                binding.btnPrincipal.setImageResource(R.drawable.cerrar)
                menuVisible = true
            }
        }
        binding.btnNuevoGasto.setOnClickListener {

            
//  HACEMOS LA PETICION DE LOS DATOS DESDE ANTES DE CARGAR EL ACTIVITY "REGISTRO_GASTO"
            ClienteRetrofit.catalogo.CMGastos().enqueue(object : Callback<List<CMGastoIngreso>> {
                override fun onResponse(
                    call: Call<List<CMGastoIngreso>>,
                    response: Response<List<CMGastoIngreso>>
                ) {
                    if (response.isSuccessful) {
                        val items = response.body() ?: emptyList()

                        val tiposGasto = items.filter { it.SECCION == 1 }.map { it.NOMBRE }
                        val metodosPago = items.filter { it.SECCION == 2 }.map { it.NOMBRE }

                        val intent = Intent(this@Inicio_gario, registro_gasto::class.java).apply {
                            putStringArrayListExtra("tiposGasto", ArrayList(tiposGasto))
                            putStringArrayListExtra("metodosPago", ArrayList(metodosPago))
                        }

                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            this@Inicio_gario,
                            "Error al cargar catálogo",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
                override fun onFailure(call: Call<List<CMGastoIngreso>>, t: Throwable) {
                    Toast.makeText(
                        this@Inicio_gario,
                        "Fallo de conexión: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        }
        binding.btnNuevoIngreso.setOnClickListener {

            // Acción para nuevo ingreso

            //  HACEMOS LA PETICION DE LOS DATOS DESDE ANTES DE CARGAR EL ACTIVITY "REGISTRO_INGRESO"
            ClienteRetrofit.catalogo.CMIngresos().enqueue(object : Callback<List<CMGastoIngreso>> {
                override fun onResponse(
                    call: Call<List<CMGastoIngreso>>,
                    response: Response<List<CMGastoIngreso>>
                ) {
                    if (response.isSuccessful) {
                        val items = response.body() ?: emptyList()

                        val tiposIngreso = items.filter { it.SECCION == 1 }.map { it.NOMBRE }
                        val metodosPago = items.filter { it.SECCION == 2 }.map { it.NOMBRE }

                        val intent = Intent(this@Inicio_gario, registro_ingreso::class.java).apply {
                            putStringArrayListExtra("tiposGasto", ArrayList(tiposIngreso))
                            putStringArrayListExtra("metodosPago", ArrayList(metodosPago))
                        }

                        startActivity(intent)
                    } else {
                        Toast.makeText(this@Inicio_gario, "Error al cargar catálogo", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<List<CMGastoIngreso>>, t: Throwable) {
                    Toast.makeText(this@Inicio_gario, "Fallo de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        binding.btnCamara.setOnClickListener {
            // Acción para cámara o PDF (futuro)
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

    private fun mi_perfil(){
        val intent = Intent(this, perfil_user::class.java)
        startActivity(intent)
    }
/*  CREE OTRO INTENT DENTRO DEL BOTON, LUEGO VOLVEREMOS A USAR ESTOS
    FUE POR COMODIDAD DE PRUEBAS DE LA CONEXION CON EL BACKEND
    private fun registrar_gasto(){

        val intent = Intent(this, registro_gasto::class.java)
        startActivity(intent)
    }

    private fun registrar_ingreso(){
        val intent = Intent(this, registro_ingreso::class.java)
        startActivity(intent)
    }
*/
    // Inflar el menú superior (icono de usuario y más opciones)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_usuario, menu)
        return true
    }

    // Manejar clics en el menú superior
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_user -> {
                mi_perfil()
                true
            }
            R.id.mi_perfil -> {
                // Acción para Mi perfil
                mi_perfil()
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
            else -> super.onOptionsItemSelected(item)
        }
    }

}

