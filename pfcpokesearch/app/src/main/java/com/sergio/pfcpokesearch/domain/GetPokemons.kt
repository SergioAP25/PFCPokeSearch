package com.sergio.pfcpokesearch.domain

import com.sergio.pfcpokesearch.data.PokemonRepository
import com.sergio.pfcpokesearch.data.database.entities.toDatabase
import com.sergio.pfcpokesearch.data.model.PokemonResults
import com.sergio.pfcpokesearch.domain.model.FilteredPokemon
import com.sergio.pfcpokesearch.domain.model.PokemonDescription
import kotlinx.coroutines.delay
import java.util.*
import javax.inject.Inject

// Caso de uso que se dedica a gestionar a obtención de pokemons de la Api e
// insertarlos en la BD

class GetPokemons @Inject constructor(
    private val repository: PokemonRepository
){
    private var apiList: List<PokemonResults> = emptyList()
    suspend operator fun invoke(){
        // Todos los "do while" con esta estructura sirven para que en caso de que la conexión
        // se caiga no haya respuesta de la api cada 3 segundos haga un reintento para seguir
        // descargando la info
        do {
            // Recoge todos los pokemons y los almacena en apilist
            apiList = repository.getAllPokemonsFromApi()?.results!!
            if (apiList.isEmpty()){
                delay(3000)
            }
        }while (apiList.isEmpty())

        // Comprueba si el último pokemon de la apilist descargada existe en la base de datos
        // para saber si es necesario actualizarla o no, lo comprueba con la letra en mayúscula
        // debido a que así los inserto
        if(!repository.exists(capitalizeName(apiList.last().name))){
            insertPokemonsIntoDatabase()
        }
    }

    // Función encargada de gestionar la inserción de pokemons en la BD
    private suspend fun insertPokemonsIntoDatabase(){
        var pokemon : FilteredPokemon?
        var pokemonDescription: PokemonDescription?
        var startingIndex = repository.countPokemons() // startingIndex posicionado al último pokemon de la bd para contar desde ahí

        // Comprueba si el número de pokemons es distinto al número de descripciones en la tabla de descripciones
        // esto es debido a que si se interrumpe la inserción a mitad podría añadirse un pokemon pero no su descripción
        // y hay que gestionar ese caso
        if(repository.countPokemons()!=repository.countDescriptions()){
            do {
                // Recoge la información de un pokemon de la Api, el substring es debido a que a partir de ese punto
                // de la cadena se encuentra el ID que necesita el método para pasarle al endpoint
                pokemon = repository.getAllPokemonsByNameFromApi(apiList[startingIndex].url.substring(34))
                if (pokemon == null){
                    delay(3000)
                }
            }while (pokemon == null)

            do {
                // Recoge la info del pokemon anterior de la Api, el substring es por lo mismo que el anterior
                pokemonDescription = repository.getPokemonDescriptionByNameFromApi(pokemon.species.url.substring(42))
                if (pokemonDescription == null){
                    delay(3000)
                }
            } while (pokemonDescription == null)

            // Inserta la descripción faltante en la BD
            repository.insertPokemonDescription(startingIndex, pokemonDescription.descriptions)
        }

        // La función continua con un bucle for que va desde el último pokemon de la BD hasta el tamaño de la lista de
        // la Api para así mejorar la eficiencia del insertado y no recorrer todos
        for(i in startingIndex until apiList.size){
            do {
                // Recoge la info del pokemon de la api
                pokemon = repository.getAllPokemonsByNameFromApi(apiList[i].url.substring(34))
                if (pokemon == null){
                    delay(3000)
                }
            } while (pokemon == null)

            do {
                // Recoge la info de la descripción del pokemon de la api
                pokemonDescription = repository.getPokemonDescriptionByNameFromApi(pokemon.species.url.substring(42))
                if (pokemonDescription == null){
                    delay(3000)
                }
            }while (pokemonDescription == null)

            // Hace mayúscula la primera letra del nombre del pokemon
            pokemon.name = capitalizeName(pokemon.name)

            // Inserta el pokemon
            repository.insertPokemon(pokemon.toDatabase())

            // Inserta su descripción
            repository.insertPokemonDescription(repository.countPokemons(), pokemonDescription.descriptions)

        }
    }

    // Función que sirve para hacer mayúscula la primera letra de un String
    private fun capitalizeName(name: String): String{
        return name.replaceFirstChar {
            if (it.isLowerCase()){
                it.titlecase(Locale.getDefault())
            }
            else {
                it.toString()
            }
        }
    }
}