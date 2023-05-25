package com.sergio.pfcpokesearch.domain

import com.sergio.pfcpokesearch.data.PokemonRepository
import com.sergio.pfcpokesearch.domain.model.FilteredPokemon
import javax.inject.Inject

// Caso de uso que llama al repositorio para conseguir la lista de los pokemons favoritos
// filtrados por nombre, ordenados por multi tipo y de la A-Z
class GetFavoritePokemonsByNameFilteredByMultiTypeAZ @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(name: String, type1: String, type2: String): List<FilteredPokemon> {
        val pokemons = repository.getFavoritePokemonsFromDatabaseByNameFilteredByMultiTypeAZ(name, type1, type2)
        return pokemons
    }
}