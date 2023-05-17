package com.sergio.pfcpokesearch.data.model

import com.google.gson.annotations.SerializedName

// Modelo de dato creado para transformar la info recibida de la API en algo usable por la app
data class PokemonModel (
    // Anotación de la librería gson convierte la info JSON recibida en un tipo de dato
    // del lenguaje
    @SerializedName("results") val results: List<PokemonResults>
)

data class PokemonResults(
    @SerializedName("name") var name: String,
    @SerializedName("url") val url: String
)