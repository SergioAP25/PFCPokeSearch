package com.sergio.pfcpokesearch.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sergio.pfcpokesearch.domain.*
import com.sergio.pfcpokesearch.domain.model.FilteredPokemon
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

// Anotación que sirve para indicarle a dagger Hilt que se trata de un viewmodel y
// permitir su inyección
@HiltViewModel
class PokemonViewModel @Inject constructor(
    // Inyección de todos los casos de uso que utilizará el viewmodel
    private val getPokemons: GetPokemons,
    private val getPokemonsByName: GetPokemonsByName,
    private val getPokemonsByNameAZ: GetPokemonsByNameAZ,
    private val getPokemonsByNameZA: GetPokemonsByNameZA,
    private val getPokemonsByNameFilteredByType: GetPokemonsByNameFilteredByType,
    private val getPokemonsByNameFilteredByTypeAZ: GetPokemonsByNameFilteredByTypeAZ,
    private val getPokemonsByNameFilteredByTypeZA: GetPokemonsByNameFilteredByTypeZA,
    private val getPokemonsByNameFilteredByMultiType: GetPokemonsByNameFilteredByMultiType,
    private val getPokemonsByNameFilteredByMultiTypeAZ: GetPokemonsByNameFilteredByMultiTypeAZ,
    private val getPokemonsByNameFilteredByMultiTypeZA: GetPokemonsByNameFilteredByMultiTypeZA,
    private val getFavoritePokemon: GetFavoritePokemon,
    private val getFavoritePokemonAZ: GetFavoritePokemonAZ,
    private val getFavoritePokemonZA: GetFavoritePokemonZA,
    private val getFavoritePokemonsByNameFilteredByType: GetFavoritePokemonsByNameFilteredByType,
    private val getFavoritePokemonsByNameFilteredByTypeAZ: GetFavoritePokemonsByNameFilteredByTypeAZ,
    private val getFavoritePokemonsByNameFilteredByTypeZA: GetFavoritePokemonsByNameFilteredByTypeZA,
    private val getFavoritePokemonsByNameFilteredByMultiType: GetFavoritePokemonsByNameFilteredByMultiType,
    private val getFavoritePokemonsByNameFilteredByMultiTypeAZ: GetFavoritePokemonsByNameFilteredByMultiTypeAZ,
    private val getFavoritePokemonsByNameFilteredByMultiTypeZA: GetFavoritePokemonsByNameFilteredByMultiTypeZA,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite,
    private val isFavorite: IsFavorite
): ViewModel() {
    // Tipo de variable que sirve de holder para que luego un observer observe si hay un
    // cambio en la variable que holdea
    var pokemonModel = MutableLiveData<List<FilteredPokemon>>()
    // Lo mismo pero con un booleano
    val isLoading = MutableLiveData<Boolean>()
    // Variables que almacenan hilos para luego poder utilizar métodos sobre ellos
    var updateScope: Job? = null
    var scope: Job? = null

    // Función encargada de gestionar la búsqueda de pokemons en la base de datos
    fun pokemonSearch(pokemonName: String, ordering: String, types: List<String>) {
        // Inicia una corrutina en un hilo a parte y se almacena en scope
        scope = viewModelScope.launch {
            // Postea valores en el holder en este caso el de carga para indicar que comienza
            // la petición a la BD
            isLoading.postValue(true)
            // Petición de lista de pokemons a través de la función typeFilteredSearch
            val pokemons = typeFilteredSearch(pokemonName, ordering, types)
            // Postea valores en el holder de la lista de pokemons
            pokemonModel.postValue(pokemons)
            // Postea valores en el holder en este caso el de carga para indicar que finalizó
            // la petición a la BD
            isLoading.postValue(false)

        }
    }

    // Función encargada de gestionar la búsqueda de pokemons favoritos en la base de datos
    fun favoritePokemonSearch(pokemonName: String, ordering: String, types: List<String>){
        scope = viewModelScope.launch {
            isLoading.postValue(true)
            val pokemons = typeFilteredFavoriteSearch(pokemonName, ordering, types)
            pokemonModel.postValue(pokemons)
            isLoading.postValue(false)
        }
    }

    // Función encargada de llamar al caso de uso que actualiza la base de datos
    fun updateDatabase(){
        updateScope = viewModelScope.launch {
            getPokemons()
        }
    }

    // Función que devuelve la respuesta en función del tipo de filtrado que se necesita aplicar
    // según los datos recibidos
    suspend fun typeFilteredSearch(pokemonName: String, ordering: String, types: List<String>): List<FilteredPokemon> {
        var pokemons: List<FilteredPokemon> = emptyList()
        when(ordering){
            "" -> {
                when(types.size){
                    0 -> pokemons = getPokemonsByName(pokemonName)
                    1 -> pokemons = getPokemonsByNameFilteredByType(pokemonName, types[0])
                    2 -> pokemons = getPokemonsByNameFilteredByMultiType(pokemonName, types[0], types[1])
                }
            }

            "az" -> {
                when(types.size){
                    0 -> pokemons = getPokemonsByNameAZ(pokemonName)
                    1 -> pokemons = getPokemonsByNameFilteredByTypeAZ(pokemonName, types[0])
                    2 -> pokemons = getPokemonsByNameFilteredByMultiTypeAZ(pokemonName, types[0], types[1])
                }
            }

            "za" -> {
                when(types.size){
                    0 -> pokemons = getPokemonsByNameZA(pokemonName)
                    1 -> pokemons = getPokemonsByNameFilteredByTypeZA(pokemonName, types[0])
                    2 -> pokemons = getPokemonsByNameFilteredByMultiTypeZA(pokemonName, types[0], types[1])
                }
            }
        }
        return pokemons
    }

    // Función que devuelve la respuesta en función del tipo de filtrado que se necesita aplicar
    // según los datos recibidos
    suspend fun typeFilteredFavoriteSearch(pokemonName: String, ordering: String, types: List<String>): List<FilteredPokemon> {
        var pokemons: List<FilteredPokemon> = emptyList()

        when(ordering){
            "" -> {
                when(types.size){
                    0 -> pokemons = getFavoritePokemon(pokemonName)
                    1 -> pokemons = getFavoritePokemonsByNameFilteredByType(pokemonName, types[0])
                    2 -> pokemons = getFavoritePokemonsByNameFilteredByMultiType(pokemonName, types[0], types[1])
                }
            }

            "az" -> {
                when(types.size){
                    0 -> pokemons = getFavoritePokemonAZ(pokemonName)
                    1 -> pokemons = getFavoritePokemonsByNameFilteredByTypeAZ(pokemonName, types[0])
                    2 -> pokemons = getFavoritePokemonsByNameFilteredByMultiTypeAZ(pokemonName, types[0], types[1])
                }
            }

            "za" -> {
                when(types.size){
                    0 -> pokemons = getFavoritePokemonZA(pokemonName)
                    1 -> pokemons = getFavoritePokemonsByNameFilteredByTypeZA(pokemonName, types[0])
                    2 -> pokemons = getFavoritePokemonsByNameFilteredByMultiTypeZA(pokemonName, types[0], types[1])
                }
            }
        }
        return pokemons
    }

    // Función encargada de llamar al caso de uso que añade favoritos a la base de datos
    suspend fun addFavoritePokemon(name: String){
        addFavorite(name)
    }

    // Función encargada de llamar al caso de uso que elimina favoritos de la base de datos
    suspend fun removeFavoritePokemon(name: String){
        removeFavorite(name)
    }

    // Función encargada de llamar al caso de uso que comprueba si un pokemon es favorito en la base de datos
    suspend fun isFavoritePokemon(name: String): Boolean{
        return isFavorite(name)
    }

}