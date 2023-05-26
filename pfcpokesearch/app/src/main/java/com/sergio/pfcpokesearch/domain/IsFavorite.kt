package com.sergio.pfcpokesearch.domain

import com.sergio.pfcpokesearch.data.PokemonRepository
import javax.inject.Inject

// Caso de uso que llama al repositorio para ejecutar la función
// isFavorite, que comprueba si un pokemon es favorito

class IsFavorite @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(name: String): Boolean {
        return repository.isFavority(name)
    }
}