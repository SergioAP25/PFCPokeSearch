package com.sergio.pfcpokesearch.domain

import com.sergio.pfcpokesearch.data.PokemonRepository
import com.sergio.pfcpokesearch.domain.model.FilteredPokemon
import javax.inject.Inject

// Caso de uso que llama al repositorio para conseguir la lista de los pokemons
// filtrados por nombre y ordenados por tipo

class GetPokemonsByNameFilteredByType @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(name: String, type1: String): List<FilteredPokemon> {
        val pokemons = repository.getPokemonsFromDatabaseByNameFilteredByType(name, type1)
        return pokemons
    }
}