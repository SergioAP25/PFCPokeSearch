package com.sergio.pfcpokesearch.ui.view

import android.content.Context
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

        //Recoge los valores pasados por el intent
        val bundle = intent.extras
        val email = bundle?.getString("email")
        val provider = bundle?.getString("provider")

        // Almacena la info de la sesión en las sharedpreferences
        val prefs = getSharedPreferences("PokeSearch", Context.MODE_PRIVATE).edit()
        prefs.putString("email", email)
        prefs.putString("provider", provider)
        prefs.apply()

        replaceFragment(favoriteFragment)

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

    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_activity_frame_layout, fragment)
        transaction.commit()
    }
}