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

        configSwipe()

        // Si el hilo del detailviewmodel no es nulo, lo cancela
        if(pokemonDetailViewModel.scope!=null){
            pokemonDetailViewModel.scope!!.cancel()
        }
        // Invoca a la función del viewmodel
        pokemonDetailViewModel.homeFragmentCreate()

        // Observa cambios en el live data pokemonModel y ejecuta el código en consecuencia
        pokemonDetailViewModel.pokemonModel.observe(viewLifecycleOwner, Observer {pokemon ->
            // Aquí dentro se adjudica la información necesaria al XML
            Picasso.get().load(pokemon?.sprites?.front_default).into(binding.pokemonImage)
            binding.pokemonName.text = pokemon?.name

            updateStatBar(binding.hp, pokemon?.stats?.get(0)?.base_stat)
            updateStatBar(binding.attack, pokemon?.stats?.get(1)?.base_stat)
            updateStatBar(binding.defense, pokemon?.stats?.get(2)?.base_stat)
            updateStatBar(binding.specialAttack, pokemon?.stats?.get(3)?.base_stat)
            updateStatBar(binding.specialDefense, pokemon?.stats?.get(4)?.base_stat)
            updateStatBar(binding.speed, pokemon?.stats?.get(5)?.base_stat)

            bindTypes(pokemon)

            binding.height1.text = (pokemon?.height?.toFloat()?.div(10)).toString()+" m"
            binding.weight1.text = (pokemon?.weight?.toFloat()?.div(10)).toString()+" kg"

            // Click listener del botón para ir a la ventana de fullscreenimage
            binding.pokemonImage.setOnClickListener {
                navigateToFullImage(pokemon?.sprites?.front_default!!)
            }

            // Corrutina encargada de ver si un pokemon es favorito y marcar o desmarcar la estrella
            // en consecuencia
            CoroutineScope(Dispatchers.IO).launch {
                if(!pokemonDetailViewModel.isFavoritePokemon(pokemon!!.name)){
                    binding.boton.setBackgroundResource(R.drawable.baseline_star_border_24)
                }
                else{
                    binding.boton.setBackgroundResource(R.drawable.baseline_star_24)
                }
            }

            // Click listener del botón de favorito encargado de marcarla y añadir a favoritos
            // o desmarcarla y eliminar de favoritos dentro de una corrutina
            binding.boton.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    if(!pokemonDetailViewModel.isFavoritePokemon(pokemon!!.name)){
                        pokemonDetailViewModel.addFavoritePokemon(pokemon.name)
                        binding.boton.setBackgroundResource(R.drawable.baseline_star_24)
                    }
                    else{
                        pokemonDetailViewModel.removeFavoritePokemon(pokemon.name)
                        binding.boton.setBackgroundResource(R.drawable.baseline_star_border_24)
                    }
                }
            }
        })

        // Observer que detecta cambios en el live data pokemonDescription y ejecuta el código
        pokemonDetailViewModel.pokemonDescription.observe(viewLifecycleOwner, Observer { pokemonDescription ->
            binding.description.text = pokemonDescription
        })

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