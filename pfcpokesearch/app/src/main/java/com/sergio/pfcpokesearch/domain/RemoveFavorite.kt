package com.sergio.pfcpokesearch.domain

import com.sergio.pfcpokesearch.data.PokemonRepository
import javax.inject.Inject

// Caso de uso que llama al repositorio para eliminar un favorito

class RemoveFavorite @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(name: String) {
        repository.removeFavorite(name)
    }
}