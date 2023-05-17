package com.sergio.pfcpokesearch.data.model

import com.google.gson.annotations.SerializedName

// Modelo de dato creado para transformar la info recibida de la API en algo usable por la app
data class FilteredPokemonModel (
    // Anotación de la librería gson convierte la info JSON recibida en un tipo de dato
    // del lenguaje
    @SerializedName("name") val name: String,
    @SerializedName("species") val species: Species,
    @SerializedName("sprites") val sprites: Sprites,
    @SerializedName("stats") val stats: List<Stats>,
    @SerializedName("types") val types: List<Types>,
    @SerializedName("height") val height: Int,
    @SerializedName("weight") val weight: Int
)

data class Species (
    @SerializedName("url") val url: String
)

data class Sprites (
    @SerializedName("front_default") val front_default: String
)

data class Stats(
    @SerializedName("base_stat") val base_stat: Int
)

data class Types(
    @SerializedName("slot") val slot: Int,
    @SerializedName("type") val type: Type
)

data class Type(
    @SerializedName("name") val name: String,
)