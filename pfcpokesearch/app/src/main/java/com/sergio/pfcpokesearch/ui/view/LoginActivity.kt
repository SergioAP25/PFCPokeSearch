package com.sergio.pfcpokesearch.ui.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.sergio.pfcpokesearch.R
import com.sergio.pfcpokesearch.databinding.LoginActivityBinding
import dagger.hilt.android.AndroidEntryPoint

// Anotación que prepara la clase para ser inyectada e inyectar dependencias de la librería de DaggerHilt
@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {
    // Instanciación del binding (Clase generada automáticamente al crear el XML)
    // Haciendo uso de la funcionalidad ViewBinding de Kotlin
    private lateinit var binding: LoginActivityBinding


    // Variable para comprobar si el requestCode es equivalente en el login con Google
    // Su función es hacer el código más legible y evitar confusiones para no tener que
    // hardcodear directamente el 100
    private val GOOGLE_SIGN_IN = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(androidx.appcompat.R.style.Theme_AppCompat_Light_NoActionBar)
        super.onCreate(savedInstanceState)

        // Infla la vista del binding
        binding = LoginActivityBinding.inflate(layoutInflater)

        // Asigna la vista del binding
        setContentView(binding.root)
        initUI()
    }

    private fun initUI() {
        //Set del listener al hacer click del register
        binding.register.setOnClickListener {
            if (binding.email.text!!.isNotEmpty() && binding.password.text!!.isNotEmpty()) {
                // Hace uso de los métodos que nos provee Firebase para poder realizar un registro en su plataforma
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                    binding.email.text.toString(),
                    binding.password.text.toString()
                ).addOnCompleteListener {
                    // Maneja las posibles respuestas
                    if (it.isSuccessful) {
                        navigatetoHome(it.result.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        mensajeError("Ha habido un error con el registro, por favor, inténtelo de nuevo")
                    }
                }
            }
        }

        //Set del listener al hacer click del login
        binding.login.setOnClickListener {
            if (binding.email.text!!.isNotEmpty() && binding.password.text!!.isNotEmpty()) {
                // Hace uso de los métodos que nos provee Firebase para poder realizar un login en su plataforma
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    binding.email.text.toString(),
                    binding.password.text.toString()
                ).addOnCompleteListener {
                    // Manejo de las posibles respuestas
                    if (it.isSuccessful) {
                        navigatetoHome(it.result.user?.email ?: "", ProviderType.BASIC)
                    } else {
                        mensajeError("Se ha producido un error en la autenticación del usuario")
                    }
                }
            }
        }

        //Set del listener al hacer click del login con google
        binding.loginGoogle.setOnClickListener {
            // Hace uso de los métodos que nos provee Firebase para poder realizar un login con google
            val googleConf = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            val googleClient = GoogleSignIn.getClient(this, googleConf)
            googleClient.signOut()

            // Inicia la actividad con startActivityForResult para que la actividad
            // de login con google se superponga a la actual
            startActivityForResult(googleClient.signInIntent, GOOGLE_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Durante esa actividad aplicamos la lógica del login aplicando los métodos
        // proporcionados para ello
        if (requestCode == GOOGLE_SIGN_IN){
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try{
                val account = task.getResult(ApiException::class.java)

                // Control de excepciones
                if (account!=null){
                    val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                    FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener {
                        if(it.isSuccessful){
                            navigatetoHome(account.email ?: "", ProviderType.GOOGLE)
                        }
                        else{
                            mensajeError("Se ha producido un error en la autenticación del usuario")
                        }
                    }
                }
            }
            catch (e: ApiException){
                mensajeError("Se ha producido un error en la autenticación del usuario")
            }
        }
    }

    // Función encargada de comprobar la sesión
    private fun session(){
        val prefs = getSharedPreferences("PokeSearch", Context.MODE_PRIVATE)
        val email = prefs.getString("email", null)
        val provider = prefs.getString("provider", null)

        if (email!=null && provider!=null){
            navigatetoHome(email, ProviderType.valueOf(provider))
        }
    }

    // Función que encapsula la lógica del cambio de pantalla y almacena parámetros en el intent
    private fun navigatetoHome(email: String, provider: ProviderType){
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("email", email)
        intent.putExtra("provider", provider.name)
        startActivity(intent)
        finish()
    }

    // Función diseñada para mostrar un mensaje pasado por parámetro
    private fun mensajeError(mensaje: String){
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setMessage(mensaje)
        builder.setPositiveButton("Aceptar", null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}