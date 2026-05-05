// Autor: jocampo-backend – Integración con JWT
package com.sigl.gestionleads.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Helper para la base de datos SQLite del SIGL.
 * Gestiona la tabla de usuarios y la validación de credenciales.
 */
class DatabaseHelper(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,      // nombre del archivo .db
    null,               // cursor factory (por defecto)
    DATABASE_VERSION    // versión inicial
) {
    // ──────────────────────────────────────────────
    // Constantes de la base de datos
    // ──────────────────────────────────────────────
    companion object {
        private const val DATABASE_NAME = "leads.db"
        private const val DATABASE_VERSION = 1

        // Tabla usuarios
        private const val TABLE_USUARIOS = "usuarios"
        private const val COL_ID = "id"
        private const val COL_NOMBRE = "nombre"
        private const val COL_EMAIL = "email"
        private const val COL_PASSWORD = "password_hash"   // en texto plano para demo
        private const val COL_ROL = "rol"
        private const val COL_ACTIVO = "activo"
        private const val COL_CREADO_EN = "creado_en"
        private const val COL_ACTUALIZADO_EN = "actualizado_en"
    }

    // ──────────────────────────────────────────────
    // Ciclo de vida de la base de datos
    // ──────────────────────────────────────────────
    override fun onCreate(db: SQLiteDatabase) {
        // Crear la tabla usuarios con la estructura del backend web
        val sql = """
            CREATE TABLE IF NOT EXISTS $TABLE_USUARIOS (
                $COL_ID              INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_NOMBRE          TEXT NOT NULL,
                $COL_EMAIL           TEXT NOT NULL UNIQUE,
                $COL_PASSWORD        TEXT NOT NULL,
                $COL_ROL             TEXT NOT NULL DEFAULT 'asesor',
                $COL_ACTIVO          INTEGER NOT NULL DEFAULT 1,
                $COL_CREADO_EN       TEXT NOT NULL DEFAULT (datetime('now')),
                $COL_ACTUALIZADO_EN  TEXT NOT NULL DEFAULT (datetime('now'))
            )
        """.trimIndent()
        db.execSQL(sql)

        // Insertar el usuario administrador de prueba
        val valoresAdmin = ContentValues().apply {
            put(COL_NOMBRE, "Administrador Sistema")
            put(COL_EMAIL, "admin@leads.com")
            put(COL_PASSWORD, "Admin2024*")   // contraseña en texto plano para pruebas
            put(COL_ROL, "admin")
            put(COL_ACTIVO, 1)
        }
        db.insert(TABLE_USUARIOS, null, valoresAdmin)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Política simple: eliminar la tabla antigua y volver a crear.
        // En producción se usaría migración incremental.
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        onCreate(db)
    }

    // ──────────────────────────────────────────────
    // Método de validación de credenciales
    // ──────────────────────────────────────────────
    /**
     * Verifica si existe un usuario activo con el email y contraseña dados.
     * @return true si las credenciales son correctas, false en caso contrario.
     */
    fun validarUsuario(email: String, password: String): Boolean {
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_USUARIOS WHERE $COL_EMAIL = ? AND $COL_PASSWORD = ? AND $COL_ACTIVO = 1",
            arrayOf(email, password)
        )
        val valido = cursor.count > 0   // si hay al menos un registro, es válido
        cursor.close()
        db.close()
        return valido

    }
    /**
     * Inserta un nuevo usuario en SQLite.
     * @return true si se insertó correctamente, false si el email ya existe.
     */
    fun insertarUsuario(nombre: String, email: String, password: String, rol: String): Boolean {
        val db = writableDatabase
        val content = ContentValues().apply {
            put(COL_NOMBRE, nombre)
            put(COL_EMAIL, email)
            put(COL_PASSWORD, password)
            put(COL_ROL, rol)
            put(COL_ACTIVO, 1)
        }
        val resultado = db.insert(TABLE_USUARIOS, null, content)
        db.close()
        return resultado != -1L
        // Integración por jocampo-backend
    }
}