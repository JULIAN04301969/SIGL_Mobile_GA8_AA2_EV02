package com.sigl.gestionleads

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.sigl.gestionleads.api.LoginRequest
import com.sigl.gestionleads.api.RetrofitClient
import com.sigl.gestionleads.data.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnIngresar: MaterialButton
    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        etEmail = findViewById(R.id.etUsuario)
        etPassword = findViewById(R.id.etContrasena)
        btnIngresar = findViewById(R.id.btnIngresar)

        dbHelper = DatabaseHelper(this)

        btnIngresar.setOnClickListener {
            validarCredenciales()
        }
    }

    private fun validarCredenciales() {
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.error_campos_vacios, Toast.LENGTH_SHORT).show()
            return
        }

        // Intenta login contra la API en segundo plano
        lifecycleScope.launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.login(LoginRequest(email, password))
                }
                if (respuesta.isSuccessful && respuesta.body()?.token != null) {
                    // API respondió OK
                    Toast.makeText(this@LoginActivity, R.string.bienvenido, Toast.LENGTH_SHORT).show()
                    navegarDashboard(email)
                } else {
                    // La API respondió pero con error (credenciales incorrectas)
                    throw Exception(respuesta.body()?.message ?: "Error de autenticación")
                }
            } catch (e: Exception) {
                // Si falla la API (red, error del servidor, credenciales), usa SQLite local
                if (dbHelper.validarUsuario(email, password)) {
                    Toast.makeText(this@LoginActivity, R.string.bienvenido, Toast.LENGTH_SHORT).show()
                    navegarDashboard(email)
                } else {
                    Toast.makeText(this@LoginActivity, R.string.error_credenciales, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun navegarDashboard(email: String) {
        val intent = Intent(this, DashboardActivity::class.java)
        intent.putExtra("usuario", email)
        startActivity(intent)
        finish()
    }
}