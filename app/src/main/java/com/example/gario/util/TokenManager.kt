package com.example.gario.util

import android.content.Context
import android.widget.Toast
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


// Clase para gestionar el token de autenticación
// Que este quede guardado en un archivo de preferencia encriptado
// TAMBIEN TIENE LAS FUNCIONES DE GUARDADO Y RECUPERACION DEL TOKEN
class TokenManager(context: Context) {
    private val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    private val sharedPrefs = EncryptedSharedPreferences.create(
        "secure_token_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    // Funciones para Guardar y recuperar el token de autenticación
    fun saveToken(token: String, context: Context) {
        sharedPrefs.edit().putString("auth_token", token).apply()
        Toast.makeText(context, "Token guardado ✅", Toast.LENGTH_SHORT).show()
    }
    // Recuperar el token de autenticación
    fun getToken(): String? = sharedPrefs.getString("auth_token", null)
    // Limpiar el token de autenticación
    fun clearToken() {
        sharedPrefs.edit().remove("auth_token").apply()
    }


}