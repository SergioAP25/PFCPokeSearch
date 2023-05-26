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


}