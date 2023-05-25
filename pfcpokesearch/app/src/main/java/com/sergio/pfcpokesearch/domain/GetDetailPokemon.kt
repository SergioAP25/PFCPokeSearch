package com.sergio.pfcpokesearch.domain

import com.sergio.pfcpokesearch.data.PokemonRepository
import com.sergio.pfcpokesearch.domain.model.FilteredPokemon
import javax.inject.Inject

// Caso de uso que llama al repositorio para conseguir el pokemon de la ventana de detalle
class GetDetailPokemon @Inject constructor(
    private val repository: PokemonRepository
){
    suspend operator fun invoke(name: String): FilteredPokemon {
        val pokemons = repository.getDetailPokemonFromDatabase(name)
        return pokemons
    }
}