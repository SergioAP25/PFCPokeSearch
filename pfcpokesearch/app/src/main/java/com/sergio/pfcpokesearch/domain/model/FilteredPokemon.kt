package com.sergio.pfcpokesearch.domain.model

import com.sergio.pfcpokesearch.data.model.*

data class FilteredPokemon(
    var name: String,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stats>,
    val types: List<Types>,
    val height: Int,
    val weight: Int
)

fun FilteredPokemonModel.toDomain() = FilteredPokemon(name, species, sprites,
    stats, types, height, weight)
