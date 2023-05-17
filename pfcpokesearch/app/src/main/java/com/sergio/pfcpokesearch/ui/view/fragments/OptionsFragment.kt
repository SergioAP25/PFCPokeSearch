package com.sergio.pfcpokesearch.ui.view.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.sergio.pfcpokesearch.databinding.FragmentOptionsBinding
import com.sergio.pfcpokesearch.ui.view.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OptionsFragment : Fragment() {


    private lateinit var binding: FragmentOptionsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOptionsBinding.inflate(layoutInflater)
        // Recoge la info del intent
        val email = activity?.intent?.extras?.getString("email", null)
        val provider = activity?.intent?.extras?.getString("provider", null)
        initUI(email ?: "", provider ?: "")

        return binding.root
    }

    // Función para separar más el código que se dedica a inicializar la interfaz de usuario
    private fun initUI(email: String, provider: String){
        binding.email.text = email
        binding.provider.text = provider
        // Lógica de LogOut utilizado los métodos proporcionados por firebase y que limpia las sharedpreferences
        binding.logOutButton.setOnClickListener {
            val prefs = getActivity()?.getSharedPreferences("PokeSearch", Context.MODE_PRIVATE)?.edit()
            prefs?.clear()
            prefs?.apply()
            FirebaseAuth.getInstance().signOut()
            navigateToLogin()
        }
    }

    // Función que te redirige al login
    private fun navigateToLogin(){
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
        getActivity()?.finish()
    }
}