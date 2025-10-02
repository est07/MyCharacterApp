package com.example.mycharacterapp.domain.models
import android.os.Parcelable
import com.example.mycharacterapp.data.database.entities.CharacterEntity
import com.example.mycharacterapp.data.network.responses.CharactersResultsResponse
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class CharacterModel(
    val id: Int,
    var name: String? = String(),
    var status: String? = String(),
    var species: String? = String(),
    var gender: String? = String(),
    var image: String? = String(),
    var created: String? = String()
) : Parcelable

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
