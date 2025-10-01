package com.example.mycharacterapp.data.network.responses

import com.squareup.moshi.Json

data class CharactersResponse(
    @param:Json(name = "results")
    val results: List<CharactersResultsResponse>? = emptyList()
)

data class CharactersResultsResponse(
    @param:Json(name = "id")
    val id: Int,
    @param:Json(name = "name")
    val name: String? = String(),
    @param:Json(name = "status")
    val status: String? = String(),
    @param:Json(name = "species")
    val species: String? = String(),
    @param:Json(name = "gender")
    val gender: String? = String(),
    @param:Json(name = "image")
    val image: String? = String()
)


