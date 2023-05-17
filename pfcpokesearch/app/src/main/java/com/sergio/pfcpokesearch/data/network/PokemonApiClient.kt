package com.sergio.pfcpokesearch.data.network

import com.sergio.pfcpokesearch.data.model.DescriptionPokemonModel
import com.sergio.pfcpokesearch.data.model.FilteredPokemonModel
import com.sergio.pfcpokesearch.data.model.PokemonModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

// Interfaz para lanzar peticiones a la api
interface PokemonApiClient {
    // Utilizando la anotación GET de la libreria retrofit se lanza una petición a la api
    @GET("pokemon?limit=100000")
    // La palabra reservada suspend indica que la petición ha de lanzarse en un hilo a parte del main
    suspend fun getAllPokemons(): Response<PokemonModel>

    @GET("pokemon/{name}")
    // La anotación path indica que es un path param
    suspend fun getAllPokemonsByName(@Path("name") url: String): Response<FilteredPokemonModel>

    @GET("pokemon-species/{name}")
    suspend fun getPokemonDescriptionByName(@Path("name") url: String): Response<DescriptionPokemonModel>
}