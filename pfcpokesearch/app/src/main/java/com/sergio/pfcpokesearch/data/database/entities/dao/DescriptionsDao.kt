package com.sergio.pfcpokesearch.data.database.entities.dao

import androidx.room.Dao
import androidx.room.Query
import com.sergio.pfcpokesearch.data.model.Description

@Dao
interface DescriptionsDao {
    // Inserta una descripción en la tabla
    @Query("INSERT INTO descriptions (descriptions_id, descriptions) VALUES(:id, :description)")
    suspend fun insertPokemonDescriptions(id: Int, description: List<Description>)

    // Devuelve el total de descripciones en la tabla
    @Query("SELECT COUNT(*) FROM descriptions")
    suspend fun countDescriptions(): Int

}