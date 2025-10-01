package com.example.mycharacterapp.domain.repositories

import com.example.mycharacterapp.data.network.responses.CharactersResultsResponse
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    suspend fun getCharactersList(): Flow<List<CharactersResultsResponse>>

    suspend fun insertCharactersList(characters: List<CharactersResultsResponse>)

    suspend fun getAllCharactersDB(): List<CharactersResultsResponse>

    suspend fun createCharacters(character: CharactersResultsResponse)

    suspend fun updateCharacters(character: CharactersResultsResponse)

    suspend fun deleteCharacters(character: CharactersResultsResponse)
}