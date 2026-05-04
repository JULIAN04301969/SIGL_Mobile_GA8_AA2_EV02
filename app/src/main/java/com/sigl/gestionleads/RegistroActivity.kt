package com.sigl.gestionleads

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.sigl.gestionleads.api.RegistroRequest
import com.sigl.gestionleads.api.RetrofitClient
import com.sigl.gestionleads.data.DatabaseHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistroActivity : AppCompatActivity() {

    private lateinit var etNombre: TextInputEditText
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var btnRegistrar: MaterialButton
    private lateinit var tvIrLogin: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        etNombre = findViewById(R.id.etNombre)
        etEmail = findViewById(R.id.etEmailRegistro)
        etPassword = findViewById(R.id.etPasswordRegistro)
        btnRegistrar = findViewById(R.id.btnRegistrar)
        tvIrLogin = findViewById(R.id.tvIrLogin)

        btnRegistrar.setOnClickListener { registrar() }
        tvIrLogin.setOnClickListener { finish() }
    }

    private fun registrar() {
        val nombre = etNombre.text.toString().trim()
        val email = etEmail.text.toString().trim()
        val password = etPassword.text.toString().trim()

        if (nombre.isEmpty() || email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Complete todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        lifecycleScope.launch {
            try {
                val respuesta = withContext(Dispatchers.IO) {
                    RetrofitClient.apiService.registro(RegistroRequest(nombre, email, password))
                }
                if (respuesta.isSuccessful) {
                    Toast.makeText(this@RegistroActivity, "Registro exitoso. Inicia sesión.", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    val msg = respuesta.body()?.message ?: "Error en registro"
                    Toast.makeText(this@RegistroActivity, msg, Toast.LENGTH_LONG).show()
                }
            } catch (e: Exception) {
                val dbHelper = DatabaseHelper(this@RegistroActivity)
                if (dbHelper.insertarUsuario(nombre, email, password, "asesor")) {
                    Toast.makeText(this@RegistroActivity, "Registrado localmente (sin conexión).", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this@RegistroActivity, "Error al guardar localmente.", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}