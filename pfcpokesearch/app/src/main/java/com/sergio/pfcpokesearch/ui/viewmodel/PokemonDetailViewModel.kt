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
class PokemonDetailViewModel @Inject constructor(
    // Inyección de todos los casos de uso que utilizará el viewmodel
    private val getDetailPokemon: GetDetailPokemon,
    private val getPokemonDescriptions: GetPokemonDescriptions,
    private val getRandomPokemon: GetRandomPokemon,
    private val addFavorite: AddFavorite,
    private val removeFavorite: RemoveFavorite,
    private  val isFavorite: IsFavorite
): ViewModel(){
    // Tipo de variable que sirve de holder para que luego un observer observe si hay un
    // cambio en la variable que holdea
    val pokemonModel = MutableLiveData<FilteredPokemon?>()
    // Live data que permite cambios pero para un string
    val pokemonDescription = MutableLiveData<String?>()
    // Lo mismo pero para un booleano
    val isLoading = MutableLiveData<Boolean>()
    // Variables que almacenan corrutinas
    var scope: Job? = null
    var randomScope: Job? = null
    var pokemonName: String = ""

    // Función encargada de obtener el pokemon seleccionado para la ventana de detalle y postearlo
    // en el live data
    fun onCreate(pokemonName: String) {
        scope = viewModelScope.launch {
            isLoading.postValue(true)

            val result = getDetailPokemon(pokemonName)

            pokemonModel.postValue(result)

            // Obtención de la descripción en inglés ya que no siempre es la posición 0, además
            // de limpiar el String de saltos de línea con los que viene la Api por defecto
            var description = ""
            var list = getPokemonDescriptions(pokemonName).descriptions
            if(!list.isEmpty()){
                for(i in 0 until list.size){
                    if (list[i].language.name.equals("en")){
                        description = getPokemonDescriptions(pokemonName).descriptions[i].flavor_text.replace("\n", " ")
                        break
                    }
                }
            }
            else{
                description = "This pokemon has no description known"
            }

            // Post de la descrión al live data
            pokemonDescription.postValue(description)

            isLoading.postValue(false)
        }
    }

    // Función encargada de obtener el pokemon seleccionado para la ventana de detalle y postearlo
    fun homeFragmentCreate(){
        scope = viewModelScope.launch {
            isLoading.postValue(true)
            // Llamada a la función randomPokemon
            randomPokemon()
            // Une el hilo que postea el pokemon random para a este
            randomScope?.join()
            // Llama al caso de uso para obtener la info del pokemon del detalle utilizando
            // la variable pokemonName que es un live data en el que previamente se ha posteado
            // el pokemon random
            val result = getDetailPokemon(pokemonName)

            pokemonModel.postValue(result)

            // Obtención de la descripción en inglés ya que no siempre es la posición 0, además
            // de limpiar el String de saltos de línea con los que viene la Api por defecto
            var description = ""
            var list = getPokemonDescriptions(pokemonName).descriptions
            if(!list.isEmpty()){
                for(i in 0 until list.size){
                    if (list[i].language.name.equals("en")){
                        description = getPokemonDescriptions(pokemonName).descriptions[i].flavor_text.replace("\n", " ")
                        break
                    }
                }
            }
            else{
                description = "This pokemon has no description known"
            }

            pokemonDescription.postValue(description)

            isLoading.postValue(false)
        }
    }

    // Función encargadada de obtener un pokemon aleatorio y postearlo en el live data
    fun randomPokemon(){
        randomScope = viewModelScope.launch {
            isLoading.postValue(true)
            val result = getRandomPokemon()
            pokemonName = result.name
            pokemonModel.postValue(result)
            isLoading.postValue(false)
        }
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