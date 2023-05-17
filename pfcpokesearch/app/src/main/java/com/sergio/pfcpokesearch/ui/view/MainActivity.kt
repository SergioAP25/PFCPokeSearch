package com.sergio.pfcpokesearch.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sergio.pfcpokesearch.R
import com.sergio.pfcpokesearch.databinding.ActivityMainBinding
import com.sergio.pfcpokesearch.ui.view.fragments.FavoriteFragment
import com.sergio.pfcpokesearch.ui.view.fragments.HomeFragment
import com.sergio.pfcpokesearch.ui.view.fragments.OptionsFragment
import com.sergio.pfcpokesearch.ui.view.fragments.SearchFragment
import dagger.hilt.android.AndroidEntryPoint

// Enum para cada tipo de proveedor de login
enum class ProviderType {
    BASIC,
    GOOGLE
}
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val favoriteFragment = FavoriteFragment()
    private val homeFragment = HomeFragment()
    private val searchFragment = SearchFragment()
    private val optionsFragment = OptionsFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        replaceFragment(favoriteFragment)

        // Asigna a cada click del bottom nav bar una función, en este caso reemplazar la vista
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.favoriteFragment -> replaceFragment(favoriteFragment)
                R.id.homeFragment -> replaceFragment(homeFragment)
                R.id.searchFragment -> replaceFragment(searchFragment)
                R.id.optionsFragment -> replaceFragment(optionsFragment)
            }
            true
        }
    }

    // Función de conveniencia que recibe un fragment y reemplaza la vista actual por dicho fragment
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_activity_frame_layout, fragment)
        transaction.commit()
    }
}