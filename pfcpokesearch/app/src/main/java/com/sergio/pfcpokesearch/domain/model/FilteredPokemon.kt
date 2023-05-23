package com.sergio.pfcpokesearch.domain.model

import com.sergio.pfcpokesearch.data.database.entities.PokemonEntity
import com.sergio.pfcpokesearch.data.model.*

// Modelo de datos de la capa de dominio
data class FilteredPokemon(
    var name: String,
    val species: Species,
    val sprites: Sprites,
    val stats: List<Stats>,
    val types: List<Types>,
    val height: Int,
    val weight: Int
)

// Función que convierte la info del modelo de datos de la API al de la capa de dominio
fun FilteredPokemonModel.toDomain() = FilteredPokemon(name, species, sprites,
    stats, types, height, weight)
fun PokemonEntity.toDomain() = FilteredPokemon(name, species, sprites, stats, types, height, weight)
