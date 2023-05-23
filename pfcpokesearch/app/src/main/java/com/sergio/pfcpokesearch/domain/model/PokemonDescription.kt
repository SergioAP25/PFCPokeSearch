package com.sergio.pfcpokesearch.domain.model

import com.sergio.pfcpokesearch.data.database.entities.DescriptionEntity
import com.sergio.pfcpokesearch.data.model.Description
import com.sergio.pfcpokesearch.data.model.DescriptionPokemonModel

// Modelo de datos de la capa de dominio
data class PokemonDescription(
    val descriptions: List<Description>
)

// Función que convierte la info del modelo de datos de la API al de la capa de dominio
fun DescriptionPokemonModel.toDomain() = PokemonDescription(flavor_text_entries)

fun DescriptionEntity.toDomain() = PokemonDescription(descriptions)
