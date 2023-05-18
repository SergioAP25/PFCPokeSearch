package com.sergio.pfcpokesearch.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.sergio.pfcpokesearch.data.model.Description

// Variable que almacena la descripción que tendrá por defecto
private const val DEFAULT_DESCRIPTION = "[{\"flavor_text\":\"A strange seed was\\nplanted on its\\nback at birth.\\fThe plant sprouts\\nand grows with\\nthis POKéMON.\"}]"

// Creación de la tabla usando la librería Room mediante la anotación entity
@Entity(tableName = "descriptions",
    foreignKeys = [ForeignKey(entity = PokemonEntity::class, parentColumns = ["id"], // Indica que descriptions_id es una clave foránea
        childColumns = ["descriptions_id"])])
data class DescriptionEntity (
    // Datos de la tabla
    @PrimaryKey
    @ColumnInfo(name = "descriptions_id") val id: Int,
    @ColumnInfo(name = "descriptions", defaultValue = DEFAULT_DESCRIPTION) val descriptions: List<Description>
)