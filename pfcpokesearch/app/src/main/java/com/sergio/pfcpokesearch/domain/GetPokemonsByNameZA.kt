package com.sergio.pfcpokesearch.domain

import com.sergio.pfcpokesearch.data.PokemonRepository
import com.sergio.pfcpokesearch.domain.model.FilteredPokemon
import javax.inject.Inject

// Caso de uso que llama al repositorio para conseguir la lista de pokemons
// filtrados por nombre y ordenados de la Z-A

class GetPokemonsByNameZA @Inject constructor(
    private val repository: PokemonRepository
){
    suspend operator fun invoke(name: String): List<FilteredPokemon> {
        val pokemons = repository.getPokemonsFromDatabaseByNameZA(name)
        return pokemons
    }
}