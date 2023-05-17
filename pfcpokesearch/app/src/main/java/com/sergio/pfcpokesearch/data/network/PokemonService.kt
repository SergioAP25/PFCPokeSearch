package com.sergio.pfcpokesearch.data.network

import com.sergio.pfcpokesearch.data.model.DescriptionPokemonModel
import com.sergio.pfcpokesearch.data.model.FilteredPokemonModel
import com.sergio.pfcpokesearch.data.model.PokemonModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

// Inject sirve para inyectar dependencias
class PokemonService @Inject constructor(
    private val api: PokemonApiClient
) {
    // Función que llama a la api y recupera todos los pokemons
    suspend fun getPokemons(): PokemonModel?{
        return try {
            // Inicia una corrutina que puede ser cancelable (un hilo a parte)
            withContext(Dispatchers.IO){
                val response: Response<PokemonModel> = api.getAllPokemons()
                response.body()
            }
        } catch (t: Throwable){
            PokemonModel(emptyList())
        }
    }

    // Función que llama a la api y recupera todos los pokemons que contengan un nombre u id
    suspend fun getPokemonsByName(name: String): FilteredPokemonModel?{
        var response: Response<FilteredPokemonModel>? = null
        return withContext(Dispatchers.IO){
            try{
                response = api.getAllPokemonsByName(name)
            }catch (t: Throwable){

            }
            response?.body()
        }
    }

    // Función que llama a la api y recupera todas las descripcciones de los pokemons que contengan
    // un nombre u id
    suspend fun getPokemonsDescriptionByName(name: String): DescriptionPokemonModel?{
        var response: Response<DescriptionPokemonModel>? = null
        return withContext(Dispatchers.IO){
            try{
                response = api.getPokemonDescriptionByName(name)
            }catch (t: Throwable){

            }
            response?.body()
        }
    }


}