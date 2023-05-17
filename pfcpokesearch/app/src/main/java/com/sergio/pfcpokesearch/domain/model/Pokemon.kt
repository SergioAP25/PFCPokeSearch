package com.sergio.pfcpokesearch.domain.model

import com.sergio.pfcpokesearch.data.model.PokemonModel
import com.sergio.pfcpokesearch.data.model.PokemonResults

data class Pokemon(
    val results: List<PokemonResults>
)

fun PokemonModel.toDomain() = Pokemon(results)
