package com.sergio.pfcpokesearch.data.database.entities.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sergio.pfcpokesearch.data.database.entities.DescriptionEntity
import com.sergio.pfcpokesearch.data.database.entities.PokemonEntity

// Interfaz encargada de gestionar las querys a la base de datos
@Dao
interface PokemonDao {
    // Devuelve todos los pokemons cuyo nombre coincida con el String recibido
    @Query("SELECT * FROM pokemon WHERE name LIKE '%'||:name||'%'")
    suspend fun getPokemonByName(name: String):List<PokemonEntity>

    // Devuelve un único pokemon cuyo nombre coincida con el String recibido
    @Query("SELECT * FROM pokemon WHERE name = :name")
    suspend fun getDetailPokemon(name: String):PokemonEntity

    // Las siguientes querys tratan sobre recuperar los pokemons de la tabla de pokemons o favoritos en función de distintos filtros
    @Query("SELECT * FROM pokemon p, favorites f WHERE p.name = f.pokemonName AND name LIKE '%'||:name||'%'")
    suspend fun getFavoritePokemonByName(name: String):List<PokemonEntity>

    @Query("SELECT * FROM pokemon p, favorites f WHERE p.name = f.pokemonName AND name LIKE '%'||:name||'%' ORDER BY p.name ASC")
    suspend fun getFavoritePokemonByNameAZ(name: String):List<PokemonEntity>

    @Query("SELECT * FROM pokemon p, favorites f WHERE p.name = f.pokemonName AND name LIKE '%'||:name||'%' ORDER BY p.name DESC")
    suspend fun getFavoritePokemonByNameZA(name: String):List<PokemonEntity>

    // Devuelve true o false dependiendo de si existe determinado pokemon en la BD
    @Query("SELECT (SELECT COUNT(*) FROM pokemon WHERE name = :name) == 1")
    suspend fun exists(name: String): Boolean

    // Las siguientes querys tratan sobre recuperar los pokemons de la tabla de pokemons o favoritos en función de distintos filtros
    @Query("SELECT * FROM pokemon WHERE name LIKE '%'||:name||'%' ORDER BY name ASC")
    suspend fun getPokemonByNameAZ(name: String):List<PokemonEntity>

    @Query("SELECT * FROM pokemon WHERE name LIKE '%'||:name||'%' ORDER BY name DESC")
    suspend fun getPokemonByNameZA(name: String):List<PokemonEntity>

    @Query("SELECT * FROM pokemon WHERE name LIKE '%'||:name||'%' AND types LIKE '%'||:type1||'%'")
    suspend fun getPokemonByNameFilteredByType(name: String, type1: String):List<PokemonEntity>

    @Query("SELECT * FROM pokemon WHERE name LIKE '%'||:name||'%' AND types LIKE '%'||:type1||'%' ORDER BY name ASC")
    suspend fun getPokemonByNameFilteredByTypeAZ(name: String, type1: String):List<PokemonEntity>

    @Query("SELECT * FROM pokemon WHERE name LIKE '%'||:name||'%' AND types LIKE '%'||:type1||'%' ORDER BY name DESC")
    suspend fun getPokemonByNameFilteredByTypeZA(name: String, type1: String):List<PokemonEntity>

    @Query("SELECT * FROM pokemon WHERE name LIKE '%'||:name||'%' AND types LIKE '%'||:type1||'%' AND types LIKE '%'||:type2||'%'")
    suspend fun getPokemonByNameFilteredByMultiType(name: String, type1: String, type2: String):List<PokemonEntity>

    @Query("SELECT * FROM pokemon WHERE name LIKE '%'||:name||'%' AND types LIKE '%'||:type1||'%' AND types LIKE '%'||:type2||'%' ORDER BY name ASC")
    suspend fun getPokemonByNameFilteredByMultiTypeAZ(name: String, type1: String, type2: String):List<PokemonEntity>

    @Query("SELECT * FROM pokemon WHERE name LIKE '%'||:name||'%' AND types LIKE '%'||:type1||'%' AND types LIKE '%'||:type2||'%' ORDER BY name DESC")
    suspend fun getPokemonByNameFilteredByMultiTypeZA(name: String, type1: String, type2: String):List<PokemonEntity>

    @Query("SELECT * FROM pokemon p, favorites f WHERE p.name = f.pokemonName AND p.name LIKE '%'||:name||'%' AND p.types LIKE '%'||:type1||'%'")
    suspend fun getFavoritePokemonByNameFilteredByType(name: String, type1: String):List<PokemonEntity>

    @Query("SELECT * FROM pokemon p, favorites f WHERE p.name = f.pokemonName AND p.name LIKE '%'||:name||'%' AND p.types LIKE '%'||:type1||'%' ORDER BY p.name ASC")
    suspend fun getFavoritePokemonByNameFilteredByTypeAZ(name: String, type1: String):List<PokemonEntity>

    @Query("SELECT * FROM pokemon p, favorites f WHERE p.name = f.pokemonName AND p.name LIKE '%'||:name||'%' AND p.types LIKE '%'||:type1||'%' ORDER BY p.name DESC")
    suspend fun getFavoritePokemonByNameFilteredByTypeZA(name: String, type1: String):List<PokemonEntity>

    @Query("SELECT * FROM pokemon p, favorites f WHERE p.name = f.pokemonName AND p.name LIKE '%'||:name||'%' AND p.types LIKE '%'||:type1||'%' AND p.types LIKE '%'||:type2||'%'")
    suspend fun getFavoritePokemonByNameFilteredByMultiType(name: String, type1: String, type2: String):List<PokemonEntity>

    @Query("SELECT * FROM pokemon p, favorites f WHERE p.name = f.pokemonName AND p.name LIKE '%'||:name||'%' AND p.types LIKE '%'||:type1||'%' AND p.types LIKE '%'||:type2||'%' ORDER BY p.name ASC")
    suspend fun getFavoritePokemonByNameFilteredByMultiTypeAZ(name: String, type1: String, type2: String):List<PokemonEntity>

    @Query("SELECT * FROM pokemon p, favorites f WHERE p.name = f.pokemonName AND p.name LIKE '%'||:name||'%' AND p.types LIKE '%'||:type1||'%' AND p.types LIKE '%'||:type2||'%' ORDER BY p.name DESC")
    suspend fun getFavoritePokemonByNameFilteredByMultiTypeZA(name: String, type1: String, type2: String):List<PokemonEntity>

    @Query("SELECT * FROM pokemon p, descriptions d WHERE p.id = d.descriptions_id AND name = :name")
    suspend fun getPokemonDescriptions(name: String): DescriptionEntity

    // Recupera un pokemon aleatorio de la BD
    @Query("SELECT * FROM pokemon ORDER BY RANDOM () LIMIT 1.")
    suspend fun getRandomPokemon(): PokemonEntity

    // Devuelve el número total de pokemons en la tabla
    @Query("SELECT COUNT(*) FROM pokemon")
    suspend fun countPokemons(): Int

    // Inserta pokemons en la tabla y si existe conflicto los reemplaza
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(pokemon : PokemonEntity)

    // Borra la tabla de pokemons
    @Query("DELETE FROM pokemon")
    suspend fun deleteAllPokemons()

    // Devuelve true o false dependiendo de si está vacía la tabla o no
    @Query("SELECT (SELECT COUNT(*) FROM pokemon) == 0")
    fun isEmpty(): Boolean

}