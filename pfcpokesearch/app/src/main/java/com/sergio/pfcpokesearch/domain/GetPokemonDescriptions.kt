package com.sergio.pfcpokesearch.domain

import com.sergio.pfcpokesearch.data.PokemonRepository
import com.sergio.pfcpokesearch.domain.model.PokemonDescription
import javax.inject.Inject

// Caso de uso que llama al repositorio para conseguir la descripción de un determinado pokemon
class GetPokemonDescriptions @Inject constructor(
    private val repository: PokemonRepository
){
    suspend operator fun invoke(name: String): PokemonDescription {
        val pokemonDescriptions = repository.getPokemonDescriptionsFromDatabase(name)
        return pokemonDescriptions
    }
}