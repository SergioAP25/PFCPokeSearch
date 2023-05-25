package com.sergio.pfcpokesearch.domain

import com.sergio.pfcpokesearch.data.PokemonRepository
import javax.inject.Inject

// Caso de uso que llama al repositorio para añadir un favorito
// Los casos van todos en clases a parte para no llamar directamente al repositorio
// y separar más la lógica de la aplicación además de que pueden contener lógica adicional
class AddFavorite @Inject constructor(
    private val repository: PokemonRepository
) {
    // El operador "invoke" se llama cuando se hace referencia a una instancia de la clase
    // con los paréntesis como si de una función se tratase
    // por ejemplo si tenemos la variable ---> var instance: AddFavorite , invoke se
    // llamaría al usar instance()
    suspend operator fun invoke(name: String) {
        repository.addFavorite(name)
    }
}