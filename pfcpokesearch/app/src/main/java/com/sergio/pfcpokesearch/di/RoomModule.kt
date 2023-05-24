package com.sergio.pfcpokesearch.di

import android.content.Context
import androidx.room.Room
import com.sergio.pfcpokesearch.data.database.entities.PokemonDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Clase dedicada a proveer clases como inyección de dependencias
// que provienen de librerias o ya existentes en el lenguaje
// ya que al no ser propias deben de indicarse el proveedor
@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    const val DB_NAME = "pokemon_database"
    // La anotación Singleton indica que será un Singleton,
    // La anotación provides indica que provee esa dependencia
    @Singleton
    @Provides
    // Haciendo uso de la librería Room crea la base de datos para luego proveerla
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, PokemonDatabase::class.java,
            DB_NAME).build()

    // Crea el proveedor para el DAO de la tabla de pokemons
    @Singleton
    @Provides
    fun providePokemonDao(db:PokemonDatabase) = db.getPokemonDao()

    // Crea el proveedor para el DAO de la tabla de favoritos
    @Singleton
    @Provides
    fun provideFavoritesDao(db:PokemonDatabase) = db.getFavoritesDao()

    // Crea el proveedor para el DAO de la tabla de descripciones
    @Singleton
    @Provides
    fun provideDescriptionsDao(db:PokemonDatabase) = db.getDescriptionsDao()

}