package com.sergio.pfcpokesearch.ui.view.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.sergio.pfcpokesearch.R
import com.sergio.pfcpokesearch.databinding.PokemonCellBinding
import com.sergio.pfcpokesearch.domain.model.FilteredPokemon
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.reflect.KSuspendFunction1

class PokemonViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = PokemonCellBinding.bind(view)

    // Función que asigna a la interfaz la información
    fun bind(
        // "KSuspendFuction1" son funciones lambda, funciones que pueden ser como parámetro y en
        // este caso pudiendo ser corrutinas
        pokemon: FilteredPokemon, onItemSelected: (String) -> Unit, addFavorite: KSuspendFunction1<String, Unit>,
        removeFavorite: KSuspendFunction1<String, Unit>, isFavorite: KSuspendFunction1<String, Boolean>
    ){
        Picasso.get().load(pokemon.sprites.front_default).into(binding.pokemonimage)
        binding.pokemonname.text = pokemon.name
        binding.root.setOnClickListener{
            onItemSelected(pokemon.name)
        }

        bindTypes(pokemon)

        // Corrutina que comprueba si el pokemon es favorito y le asigna el icono correspondiente
        CoroutineScope(Dispatchers.IO).launch {
            if(!isFavorite(pokemon.name)){
                binding.boton.setBackgroundResource(R.drawable.baseline_star_border_24)
            }
            else{
                binding.boton.setBackgroundResource(R.drawable.baseline_star_24)
            }
        }

        // Asigna al botón la funcionalidad de añadir y eliminar favoritos
        binding.boton.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                if(!isFavorite(pokemon.name)){
                    addFavorite(pokemon.name)
                    binding.boton.setBackgroundResource(R.drawable.baseline_star_24)
                }
                else{
                    removeFavorite(pokemon.name)
                    binding.boton.setBackgroundResource(R.drawable.baseline_star_border_24)
                }
            }
        }

    }

    // Función encargada de asignar los tipos a la interfaz dependiendo del número de tipos
    private fun bindTypes(pokemon: FilteredPokemon){
        binding.pokemontype1.setImageResource(0)
        binding.pokemontype2.setImageResource(0)
        if (pokemon.types.size == 1) {
            binding.pokemontype1.setImageResource(getTypeImage(pokemon.types.get(0).type.name))
        } else if (pokemon.types.size == 2) {
            binding.pokemontype1.setImageResource(getTypeImage(pokemon.types.get(0).type.name))
            binding.pokemontype2.setImageResource(getTypeImage(pokemon.types.get(1).type.name))
        }
    }

    // Función encargada de obtener la imagen necesaria en función del tipo
    private fun getTypeImage(type: String): Int {
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
}