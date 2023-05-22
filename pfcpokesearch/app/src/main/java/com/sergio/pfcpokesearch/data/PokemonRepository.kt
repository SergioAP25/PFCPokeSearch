package com.sergio.pfcpokesearch.data

import com.sergio.pfcpokesearch.data.database.entities.DescriptionEntity
import com.sergio.pfcpokesearch.data.database.entities.PokemonEntity
import com.sergio.pfcpokesearch.data.database.entities.dao.DescriptionsDao
import com.sergio.pfcpokesearch.data.database.entities.dao.FavoritesDao
import com.sergio.pfcpokesearch.data.database.entities.dao.PokemonDao
import com.sergio.pfcpokesearch.data.model.*
import com.sergio.pfcpokesearch.data.network.PokemonService
import com.sergio.pfcpokesearch.domain.model.FilteredPokemon
import com.sergio.pfcpokesearch.domain.model.Pokemon
import com.sergio.pfcpokesearch.domain.model.PokemonDescription
import com.sergio.pfcpokesearch.domain.model.toDomain
import javax.inject.Inject

class PokemonRepository @Inject constructor(
    // Inyección de dependencias necesarias
    private val api: PokemonService,
    private val pokemonDao: PokemonDao,
    private val favoritesDao: FavoritesDao,
    private val descriptionsDao: DescriptionsDao
) {
    // Variables para generar un pokemon por defecto que será necesaria en caso de que la respuesta sea null al recuperar un pokémon
    // de la base de datos (se da en la primera ejecución de la App ya que consume directamente de la base de datos y al iniciarla no existe)
    // ningún pokemon en ella abriendose en home que hace uso de escoger un pokemon aleatorio
    private val name: String = "Bulbasaur"
    private val species: Species = Species("https://pokeapi.co/api/v2/pokemon-species/1/")
    private val sprites: Sprites =  Sprites("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/1.png")
    private val stats: List<Stats> = listOf(Stats(45), Stats(49), Stats(49), Stats(65), Stats(65), Stats(45))
    private val types: List<Types> = listOf(Types(1, Type("grass")), Types(2, Type("poison")))
    private val height: Int = 7
    private val weight: Int = 69
    private val defaultPokemon: PokemonEntity = PokemonEntity(1, name, species, sprites, stats, types, height, weight)
    private val descriptionList: List<Description> = listOf(Description(
        "For some time after its birth, it grows by gaining nourishment from the seed on its back.",
        Language("en")))
    private val defaultDescriptions: DescriptionEntity = DescriptionEntity(1, descriptionList)

    // Llamada que recupera todos los pokemons de la api y convertir los datos al modelo de la capa de dominio
    suspend fun getAllPokemonsFromApi(): Pokemon?{
        val response: PokemonModel? = api.getPokemons()
        return response?.toDomain()
    }
    // Llamada que recupera un pokemon por nombre u id de la api y convertir los datos al modelo de la capa de dominio
    suspend fun getAllPokemonsByNameFromApi(name: String): FilteredPokemon?{
        val response: FilteredPokemonModel = api.getPokemonsByName(name) ?: return null
        return response?.toDomain()
    }
    // Llamada que recupera la descripción de un pokemon de la api y convertir los datos al modelo de la capa de dominio
    suspend fun getPokemonDescriptionByNameFromApi(name: String): PokemonDescription? {
        val response: DescriptionPokemonModel? = api.getPokemonsDescriptionByName(name)?: return null
        return response?.toDomain()
    }
    // Llamada que recupera todos los pokemons que contengan determinada cadena de texto de la base de datos y convertir los datos al modelo de la capa de dominio
    suspend fun getPokemonsFromDatabaseByName(name: String): List<FilteredPokemon>{
        val response = pokemonDao.getPokemonByName(name)
        return response.map { it.toDomain() }
    }
    // Llamada que recupera un pokemon de determinado nombre de la base de datos y convertir los datos al modelo de la capa de dominio
    suspend fun getDetailPokemonFromDatabase(name: String): FilteredPokemon {
        var response = pokemonDao.getDetailPokemon(name)
        // Si la respuesta es null, que es posible que se dé el caso a pesar de que android dice lo contrario devuelve el pokemon by default
        if (response==null){
            response = defaultPokemon
        }
        return response.toDomain()
    }

    // Las siguientes llamadas recuperan pokemons de la base de datos en todas las posibles combinaciones de filtros que la app posee
    suspend fun getPokemonsFromDatabaseByNameAZ(name: String): List<FilteredPokemon>{
        val response = pokemonDao.getPokemonByNameAZ(name)
        return response.map { it.toDomain() }
    }

    suspend fun getPokemonsFromDatabaseByNameZA(name: String): List<FilteredPokemon>{
        val response = pokemonDao.getPokemonByNameZA(name)
        return response.map { it.toDomain() }
    }

    suspend fun getPokemonsFromDatabaseByNameFilteredByType(name: String, type1: String): List<FilteredPokemon>{
        val response = pokemonDao.getPokemonByNameFilteredByType(name, type1)
        return response.map { it.toDomain() }
    }

    suspend fun getPokemonsFromDatabaseByNameFilteredByTypeAZ(name: String, type1: String): List<FilteredPokemon>{
        val response = pokemonDao.getPokemonByNameFilteredByTypeAZ(name, type1)
        return response.map { it.toDomain() }
    }

    suspend fun getPokemonsFromDatabaseByNameFilteredByTypeZA(name: String, type1: String): List<FilteredPokemon>{
        val response = pokemonDao.getPokemonByNameFilteredByTypeZA(name, type1)
        return response.map { it.toDomain() }
    }

    suspend fun getPokemonsFromDatabaseByNameFilteredByMultiType(name: String, type1: String, type2: String): List<FilteredPokemon>{
        val response = pokemonDao.getPokemonByNameFilteredByMultiType(name, type1, type2)
        return response.map { it.toDomain() }
    }
    suspend fun getPokemonsFromDatabaseByNameFilteredByMultiTypeAZ(name: String, type1: String, type2: String): List<FilteredPokemon>{
        val response = pokemonDao.getPokemonByNameFilteredByMultiTypeAZ(name, type1, type2)
        return response.map { it.toDomain() }
    }

    suspend fun getPokemonsFromDatabaseByNameFilteredByMultiTypeZA(name: String, type1: String, type2: String): List<FilteredPokemon>{
        val response = pokemonDao.getPokemonByNameFilteredByMultiTypeZA(name, type1, type2)
        return response.map { it.toDomain() }
    }
}