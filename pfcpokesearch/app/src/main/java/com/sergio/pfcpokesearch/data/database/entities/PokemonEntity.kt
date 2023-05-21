package com.sergio.pfcpokesearch.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.sergio.pfcpokesearch.data.model.Species
import com.sergio.pfcpokesearch.data.model.Sprites
import com.sergio.pfcpokesearch.data.model.Stats
import com.sergio.pfcpokesearch.data.model.Types
import com.sergio.pfcpokesearch.domain.model.FilteredPokemon

// Variable que almacena el link a la imagen del sprite que se verá por defecto
private const val DEFAULT_SPRITE = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/shiny/132.png"

// Creación de la tabla usando la librería Room mediante la anotación entity
@Entity(tableName = "pokemon", indices = [
    Index(value = ["name"], unique = true)
])
data class PokemonEntity (
    // Datos de la tabla
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "species") val species: Species,
    @ColumnInfo(name = "sprites", defaultValue = DEFAULT_SPRITE) val sprites: Sprites,
    @ColumnInfo(name = "stats") val stats: List<Stats>,
    @ColumnInfo(name = "types") val types: List<Types>,
    @ColumnInfo(name = "height") val height: Int,
    @ColumnInfo(name = "weight") val weight: Int,
)

// Función que transforma un FilteredPokemon en un PokemonEntity para poder ser usado por la base de datos
fun FilteredPokemon.toDatabase() = PokemonEntity(name = name, species = species, sprites = sprites,
    stats = stats, types = types, height = height, weight = weight)