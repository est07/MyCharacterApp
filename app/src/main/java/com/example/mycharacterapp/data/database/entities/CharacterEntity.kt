package com.example.mycharacterapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mycharacterapp.domain.models.CharacterModel

@Entity(tableName = "characters_table")
data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String? = String(),
    @ColumnInfo(name = "status") val status: String? = String(),
    @ColumnInfo(name = "species") val species: String? = String(),
    @ColumnInfo(name = "gender") val gender: String? = String(),
    @ColumnInfo(name = "image") val image: String? = String(),
    @ColumnInfo(name = "created") val created: String? = String()
)

fun CharacterModel.toDomain() = CharacterEntity(
    id = id,
    name = name,
    status = status,
    gender = gender,
    image = image,
    created = created
)
