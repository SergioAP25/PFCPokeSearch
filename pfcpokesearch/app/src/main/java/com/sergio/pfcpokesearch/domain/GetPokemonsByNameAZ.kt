package com.sergio.pfcpokesearch.domain

import com.sergio.pfcpokesearch.data.PokemonRepository
import com.sergio.pfcpokesearch.domain.model.FilteredPokemon
import javax.inject.Inject

// Caso de uso que llama al repositorio para conseguir la lista de pokemons
// filtrados por nombre y ordenados de la A-Z

class GetPokemonsByNameAZ @Inject constructor(
    private val repository: PokemonRepository
){
    suspend operator fun invoke(name: String): List<FilteredPokemon> {
        val pokemons = repository.getPokemonsFromDatabaseByNameAZ(name)
        return pokemons
    }
}