package com.sergio.pfcpokesearch.data.database.entities.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface FavoritesDao {

    // Inserta un pokemon en la tabla
    @Query("INSERT INTO favorites (pokemonName) VALUES(:name)")
    suspend fun addFavorite(name: String)

    // Elimina un pokemon de la tabla
    @Query("DELETE FROM favorites WHERE pokemonName = :name")
    suspend fun removeFavorite(name: String)

    // Devuelve true o false dependiendo de si existe determinado pokemon en la BD
    @Query("SELECT (SELECT COUNT(*) FROM favorites WHERE pokemonName = :name) == 1")
    suspend fun isFavorite(name: String): Boolean


}