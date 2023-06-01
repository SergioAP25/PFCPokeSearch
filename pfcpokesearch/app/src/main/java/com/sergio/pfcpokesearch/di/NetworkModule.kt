package com.sergio.pfcpokesearch.di

import com.sergio.pfcpokesearch.data.network.PokemonApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

// Clase dedicada a proveer clases como inyección de dependencias
// que provienen de librerias o ya existentes en el lenguaje
// ya que al no ser propias deben de indicarse el proveedor
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    // La anotación Singleton indica que será un Singleton,
    // La anotación provides indica que provee esa dependencia
    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        // Haciendo uso de la librería retrofit crea el punto de llamada a la api
        // y con la librería Gson le asigna un converter para transformar los JSON en
        // datos usables
        return Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    // Crea el proveedor para el ApiClient
    @Singleton
    @Provides
    fun providePokemonApiClient(retrofit: Retrofit): PokemonApiClient {
        return retrofit.create(PokemonApiClient::class.java)
    }
}