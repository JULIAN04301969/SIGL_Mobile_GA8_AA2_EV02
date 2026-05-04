package com.sigl.gestionleads.api

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    @SerializedName("email")    val email: String,
    @SerializedName("password") val password: String
)

data class RegistroRequest(
    @SerializedName("nombre")   val nombre: String,
    @SerializedName("email")    val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("rol")      val rol: String = "asesor"
)

data class ApiResponse(
    @SerializedName("message") val message: String?,
    @SerializedName("token")   val token: String?,
    @SerializedName("usuario") val usuario: UsuarioDto?
)

data class UsuarioDto(
    @SerializedName("id")     val id: Int,
    @SerializedName("nombre") val nombre: String,
    @SerializedName("email")  val email: String,
    @SerializedName("rol")    val rol: String
)