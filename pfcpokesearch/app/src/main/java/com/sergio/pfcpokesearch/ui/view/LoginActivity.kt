package com.sergio.pfcpokesearch.ui.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.sergio.pfcpokesearch.databinding.LoginActivityBinding
import dagger.hilt.android.HiltAndroidApp

// Anotación que prepara la clase para ser inyectada e inyectar dependencias de la librería de DaggerHilt
@HiltAndroidApp
class LoginActivity: AppCompatActivity() {
    // Instanciación del binding (Clase generada automáticamente al crear el XML)
    // Haciendo uso de la funcionalidad ViewBinding de Kotlin
    private lateinit var binding: LoginActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light_NoActionBar)
        super.onCreate(savedInstanceState)

        // Inflamos la vista del binding
        binding = LoginActivityBinding.inflate(layoutInflater)

        // asignamos la vista del binding
        setContentView(binding.root)
    }

    private fun navigatetoHome(email: String, provider: ProviderType){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("email", email)
        intent.putExtra("provider", provider.name)
        startActivity(intent)
        finish()
    }

    private fun mensajeError(mensaje: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}