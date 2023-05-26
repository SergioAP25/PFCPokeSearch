package com.sergio.pfcpokesearch.domain

import com.sergio.pfcpokesearch.data.PokemonRepository
import com.sergio.pfcpokesearch.domain.model.FilteredPokemon
import javax.inject.Inject

// Caso de uso que llama al repositorio para obtener un pokemon aleatorio

class GetRandomPokemon @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(): FilteredPokemon {
        return repository.getRandomPokemonFromDatabase()
    }
}