package com.sergio.pfcpokesearch.data.database.entities

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.sergio.pfcpokesearch.data.database.entities.dao.DescriptionsDao
import com.sergio.pfcpokesearch.data.database.entities.dao.FavoritesDao
import com.sergio.pfcpokesearch.data.database.entities.dao.PokemonDao

// Clase encargada de crear la base de datos indicando las tablas
@Database(entities = [PokemonEntity::class, FavoritesEntity::class, DescriptionEntity::class], version = 1)
@TypeConverters(Converters::class) // Indica los TypeConverters a usar para la base de datos (más info en la clase Converters)
abstract class PokemonDatabase: RoomDatabase(){
    // Funciones para pasar los DAO de cada respectiva tabla (Interfaces donde van las SQL Query)
    abstract fun getPokemonDao(): PokemonDao

    abstract fun getFavoritesDao(): FavoritesDao

    abstract fun getDescriptionsDao(): DescriptionsDao
}