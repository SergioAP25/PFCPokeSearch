package com.sergio.pfcpokesearch.ui.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.sergio.pfcpokesearch.R
import com.sergio.pfcpokesearch.databinding.ActivityMainBinding
import com.sergio.pfcpokesearch.ui.view.fragments.FavoriteFragment
import com.sergio.pfcpokesearch.ui.view.fragments.HomeFragment
import com.sergio.pfcpokesearch.ui.view.fragments.OptionsFragment
import com.sergio.pfcpokesearch.ui.view.fragments.SearchFragment
import com.sergio.pfcpokesearch.ui.viewmodel.PokemonViewModel
import dagger.hilt.android.AndroidEntryPoint

// Enum para cada tipo de proveedor de login
enum class ProviderType {
    BASIC,
    GOOGLE
}
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    // Instancia del viewmodel
    private lateinit var pokemonViewModel: PokemonViewModel
    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val favoriteFragment = FavoriteFragment()
    private val optionsFragment = OptionsFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Asignación del viewmodel a la variable
        pokemonViewModel = ViewModelProvider(this).get(PokemonViewModel::class.java)

        //Recoge los valores pasados por el intent
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        // Almacena la info de la sesión en las sharedpreferences
        val prefs = getSharedPreferences("PokeSearch", Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()

        replaceFragment(homeFragment)

        // Asigna a cada click del bottom nav bar una función, en este caso reemplazar la vista
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.homeFragment -> replaceFragment(homeFragment)
                R.id.favoriteFragment -> replaceFragment(favoriteFragment)
                R.id.searchFragment -> replaceFragment(searchFragment)
                R.id.optionsFragment -> replaceFragment(optionsFragment)
            }
            true
        }
    }

    // Haciendo override de onResume asegura que la actualización a la base de datos solo
    // se ejecute una vez el usuario se haya logeado y cada vez que la aplicación
    // se reanude
    override fun onResume() {
        super.onResume()
        // Si ya hay un hilo lo cancela
        if(pokemonViewModel.updateScope!=null){
            pokemonViewModel.updateScope!!.cancel()
        }

        // Llama a la función updatedatabase del viewmodel que se encarga
        // de gestionar la actualización de la base de datos local
        pokemonViewModel.updateDatabase()
    }

    // Función de conveniencia que recibe un fragment y reemplaza la vista actual por dicho fragment
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_activity_frame_layout, fragment)
        transaction.commit()
    }
}