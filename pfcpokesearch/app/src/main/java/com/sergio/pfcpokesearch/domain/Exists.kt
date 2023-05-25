package com.sergio.pfcpokesearch.domain

import com.sergio.pfcpokesearch.data.PokemonRepository
import javax.inject.Inject

// Caso de uso que llama al repositorio para llamar a la query de la BD exists
class Exists @Inject constructor(
    private val repository: PokemonRepository
) {
    suspend operator fun invoke(name: String): Boolean {
        return repository.exists(name)
    }
}