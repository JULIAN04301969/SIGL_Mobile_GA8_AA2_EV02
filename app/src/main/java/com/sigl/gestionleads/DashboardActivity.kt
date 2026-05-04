package com.sigl.gestionleads

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class DashboardActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var tvBienvenida: TextView
    private lateinit var tvNombreUsuarioMenu: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Obtener referencias
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        tvBienvenida = findViewById(R.id.tvBienvenida)

        // Configurar Toolbar
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Cabecera del menú lateral
        val headerView = navView.getHeaderView(0)
        tvNombreUsuarioMenu = headerView.findViewById(R.id.tvNombreUsuarioMenu)

        // Recuperar email del usuario
        val usuario = intent.getStringExtra("usuario") ?: "Invitado/a"
        tvBienvenida.text = getString(R.string.bienvenido) + ", $usuario"
        tvNombreUsuarioMenu.text = usuario

        // Configurar el toggle (hamburguesa)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,                         // ← Toolbar como ancla
            R.string.app_name,
            R.string.app_name
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Manejar clics del menú
        navView.setNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.nav_dashboard -> {
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_registro -> {
                    startActivity(Intent(this, RegistroActivity::class.java))
                    drawerLayout.closeDrawers()
                    true
                }
                R.id.nav_cerrar_sesion -> {
                    cerrarSesion()
                    drawerLayout.closeDrawers()
                    true
                }
                else -> false
            }
        }
    }

    // Al tocar el icono de hamburguesa, el toggle maneja el evento
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // El evento se maneja automáticamente por el ActionBarDrawerToggle
        return super.onOptionsItemSelected(item)
    }

    private fun cerrarSesion() {
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
}