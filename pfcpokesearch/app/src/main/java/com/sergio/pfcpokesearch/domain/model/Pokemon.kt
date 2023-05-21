package com.sergio.pfcpokesearch.domain.model

import com.sergio.pfcpokesearch.data.model.PokemonModel
import com.sergio.pfcpokesearch.data.model.PokemonResults

// Modelo de datos de la capa de dominio
data class Pokemon(
    val results: List<PokemonResults>
)

// Función que convierte la info del modelo de datos de la API al de la capa de dominio
fun PokemonModel.toDomain() = Pokemon(results)
