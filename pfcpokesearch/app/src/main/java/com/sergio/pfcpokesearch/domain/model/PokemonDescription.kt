package com.sergio.pfcpokesearch.domain.model

import com.sergio.pfcpokesearch.data.model.Description
import com.sergio.pfcpokesearch.data.model.DescriptionPokemonModel

data class PokemonDescription(
    val descriptions: List<Description>
)

fun DescriptionPokemonModel.toDomain() = PokemonDescription(flavor_text_entries)
