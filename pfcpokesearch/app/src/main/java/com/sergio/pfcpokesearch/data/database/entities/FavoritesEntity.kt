package com.sergio.pfcpokesearch.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Creación de la tabla usando la librería Room mediante la anotación entity
@Entity(tableName = "favorites")
data class FavoritesEntity (
    // Datos de la tabla
    @PrimaryKey
    @ColumnInfo(name = "pokemonName") val name: String
)