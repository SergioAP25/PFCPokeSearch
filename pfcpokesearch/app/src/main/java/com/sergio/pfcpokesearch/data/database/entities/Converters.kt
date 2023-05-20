package com.sergio.pfcpokesearch.data.database.entities

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sergio.pfcpokesearch.data.model.*

// RoomDB por detrás es SQLite con lo cual no puede almacenar tipos complejos de datos solo primitivos.
// Para almacenar un tipo complejo de dato hay que indicarle como transformarlo a un primitivo y como destransformarlo
// eso es lo que hace esta clase, indicarle como almacenarlo y como interpretarlo al sacar el dato de la base de datos
class Converters {

    // Por ejemplo queremos almacenar el tipo de dato de clase Species que tiene simplemente un campo String url
    // pues le indicamos que lo almacene como String

    @TypeConverter // Con la anotación indicamos que se trata de un Typeconverter
    fun fromSpecies(species: Species): String {
        return species.url
    }

    // A la hora de sacarlo que genere un Species con ese String reconvirtiendolo
    @TypeConverter
    fun toSpecies(url: String): Species{
        return Species(url)
    }

    @TypeConverter
    fun fromSprites(sprites: Sprites): String {
        return sprites.front_default
    }

    @TypeConverter
    fun toSprites(front_default: String): Sprites{
        return Sprites(front_default)
    }

    // Stats ya es más complicado ya que se trata de una lista de un tipo de datos complejos
    // Para guardarlo y sacarlo de la base de datos hago un poco de "trampa" por motivos de tiempo
    // aunque soy plenamente consciente que la solución óptima para un proyecto que requiera de escalabilidad
    // y de ser más eficiente sería hacer varias tablas conectadas con claves foráneas
    // pero para solventar esto en este pequeño proyecto decidí hacer lo siguiente:
    @TypeConverter
    fun fromStatsList(value: List<Stats>): String {
        val gson = Gson() // Instancia un objeto Gson de la librería gson
        val type = object : TypeToken<List<Stats>>() {}.type // Convierte la lista en un String en formato JSON
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toStatsList(value: String): List<Stats> {
        val gson = Gson() // Instancia un objeto Gson de la librería gson
        val type = object : TypeToken<List<Stats>>() {}.type // Convierte el String en formato JSON en el objeto deseado
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromTypesList(value: List<Types>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Types>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toTypesList(value: String): List<Types> {
        val gson = Gson()
        val type = object : TypeToken<List<Types>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromDescriptionList(value: List<Description>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Description>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toDescriptionList(value: String): List<Description> {
        val gson = Gson()
        val type = object : TypeToken<List<Description>>() {}.type
        return gson.fromJson(value, type)
    }
}