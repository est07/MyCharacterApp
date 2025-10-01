package com.example.mycharacterapp.data.network.apis

import com.example.mycharacterapp.data.network.responses.CharactersResponse
import retrofit2.http.GET

interface CharactersApi {

    @GET("character")
    suspend fun getCharacters(): CharactersResponse
}