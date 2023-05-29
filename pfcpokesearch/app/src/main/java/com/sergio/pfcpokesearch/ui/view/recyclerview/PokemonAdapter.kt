package com.sergio.pfcpokesearch.ui.view.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sergio.pfcpokesearch.R
import com.sergio.pfcpokesearch.domain.model.FilteredPokemon
import kotlin.reflect.KSuspendFunction1

class PokemonAdapter(
    var pokemonData: List<FilteredPokemon> = emptyList(),
    // Función lambda (función que puede ser pasada como parámetro)
    private val onItemSelected:(String)->Unit,
    // Funciones lambda (funciones que pueden ser pasadas como parámetro) en este caso
    // siendo corrutinas
    private val addFavorite: KSuspendFunction1<String, Unit>,
    private val removeFavorite: KSuspendFunction1<String, Unit>,
    private val isFavorite: KSuspendFunction1<String, Boolean>
): RecyclerView.Adapter<PokemonViewHolder>(){

    // Función que asigna una lista de pokemons al recycler
    fun setData(data: List<FilteredPokemon>) {
        pokemonData = data
        notifyDataSetChanged()
    }

    // Asignación del archivo xml a la vista
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PokemonViewHolder(layoutInflater.inflate(R.layout.pokemon_cell,parent,false))
    }

    // Asignación de la info a mostrar a cada celda
    override fun onBindViewHolder(viewholder: PokemonViewHolder, position: Int) {
        val item = pokemonData
        viewholder.bind(pokemonData[position], onItemSelected, addFavorite, removeFavorite, isFavorite)
    }


    // Cantidad de items totales
    override fun getItemCount(): Int {
        return pokemonData.size
    }
}