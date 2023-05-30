package com.sergio.pfcpokesearch.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sergio.pfcpokesearch.R
import com.sergio.pfcpokesearch.databinding.FragmentHomeBinding
import com.sergio.pfcpokesearch.domain.model.FilteredPokemon
import com.sergio.pfcpokesearch.ui.view.FullScreenImageActivity
import com.sergio.pfcpokesearch.ui.viewmodel.PokemonDetailViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class HomeFragment @Inject constructor(): Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var pokemonDetailViewModel: PokemonDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        pokemonDetailViewModel = ViewModelProvider(this).get(PokemonDetailViewModel::class.java)

        return binding.root
    }

    // Función que al hacer swipe recarga el fragment
    private fun configSwipe(){
        binding.homeSwipe.setOnRefreshListener {
            parentFragmentManager.beginTransaction()
                .detach(this).commit()
            parentFragmentManager.beginTransaction()
                .attach(this).commit()
            binding.homeSwipe.isRefreshing = false
        }
    }

    // Función que asigna los tipos dependiendo de la cantidad de tipos que tenga ese pokemon en específico
    private fun bindTypes(pokemon: FilteredPokemon?){
        binding.type1.setImageResource(0)
        binding.type2.setImageResource(0)
        if (pokemon?.types?.size==1){
            binding.type1.setImageResource(getTypeImage(pokemon.types.get(0).type.name))
        }
        else{
            binding.type1.setImageResource(getTypeImage(pokemon?.types?.get(0)?.type?.name))
            binding.type2.setImageResource(getTypeImage(pokemon?.types?.get(1)?.type?.name))
        }
    }

    // Función que consigue la imagen dependiendo del tipo
    private fun getTypeImage(type: String?): Int {
        var result: Int = 0
        when(type) {
            "bug" -> result = R.drawable.bug
            "dark" -> result = R.drawable.dark
            "dragon" -> result = R.drawable.dragon
            "electric" -> result = R.drawable.electric
            "fairy" -> result = R.drawable.fairy
            "fighting" -> result = R.drawable.fighting
            "fire" -> result = R.drawable.fire
            "flying" -> result = R.drawable.flying
            "ghost" -> result = R.drawable.ghost
            "grass" -> result = R.drawable.grass
            "ground" -> result = R.drawable.ground
            "ice" -> result = R.drawable.ice
            "normal" -> result = R.drawable.normal
            "poison" -> result = R.drawable.poison
            "psychic" -> result = R.drawable.psychic
            "rock" -> result = R.drawable.rock
            "steel" -> result = R.drawable.steel
            "water" -> result = R.drawable.water
        }
        return result
    }

    // Función que actualiza las barras de stats
    private fun updateStatBar(view: View, stat: Int?){
        val params = view.layoutParams
        params.height = pxToDp(stat?.toFloat())
        view.layoutParams = params
    }

    // Función que convierte píxeles a dp
    private fun pxToDp(px :Float?): Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px!!, resources.displayMetrics)
            .roundToInt()
    }

    // Función que se encarga de llevar a la ventana de imagen completa
    private fun navigateToFullImage(image: String){
        val intent = Intent(context, FullScreenImageActivity::class.java)
        intent.putExtra(FullScreenImageActivity.EXTRA_IMAGE, image)
        startActivity(intent)
    }
}