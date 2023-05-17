package com.sergio.pfcpokesearch.data.model

import com.google.gson.annotations.SerializedName

// Modelo de dato creado para transformar la info recibida de la API en algo usable por la app
data class DescriptionPokemonModel (
    // Anotación de la librería gson convierte la info JSON recibida en un tipo de dato
    // del lenguaje
    @SerializedName("flavor_text_entries") val flavor_text_entries: List<Description>
)

data class Description (
    @SerializedName("flavor_text") val flavor_text: String,
    @SerializedName("language") val language: Language
)

data class Language (
    @SerializedName("name") val name: String
)