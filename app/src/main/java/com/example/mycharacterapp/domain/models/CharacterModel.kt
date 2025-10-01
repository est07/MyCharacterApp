package com.example.mycharacterapp.domain.models
import com.example.mycharacterapp.data.database.entities.CharacterEntity
import com.example.mycharacterapp.data.network.responses.CharactersResultsResponse

data class CharacterModel(
    val id: Int,
    var name: String? = String(),
    var status: String? = String(),
    var species: String? = String(),
    var gender: String? = String(),
    var image: String? = String(),
    var created: String? = String()
)

fun CharactersResultsResponse.toDomain() = CharacterModel(
    id = id,
    name = name,
    status = status,
    gender = gender,
    image = image,
    created = String()
)

fun CharacterEntity.toDomain() = CharacterModel(
    id = id,
    name = name,
    status = status,
    gender = gender,
    image = image,
    created = created
)
