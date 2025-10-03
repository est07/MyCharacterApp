package com.example.mycharacterapp.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.mycharacterapp.domain.models.CharacterModel

@Entity(tableName = "characters_table")
data class CharacterEntity(
    @PrimaryKey
    @ColumnInfo(name = "id") val id: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "status") val status: String,
    @ColumnInfo(name = "species") val species: String,
    @ColumnInfo(name = "gender") val gender: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "created") val created: String
)

fun CharacterModel.toDomain() = CharacterEntity(
    id = id?: 0,
    name = name?: String(),
    status = status?: String(),
    species = species?: String(),
    gender = gender?: String(),
    image = image?: String(),
    created = created?: String()
)
