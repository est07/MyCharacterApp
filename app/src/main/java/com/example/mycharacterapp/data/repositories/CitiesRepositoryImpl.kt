package com.example.mycharacterapp.data.repositories

import com.example.mycharacterapp.data.network.apis.CharactersApi
import com.example.mycharacterapp.data.network.responses.CharactersResultsResponse
import com.example.mycharacterapp.domain.repositories.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CitiesRepositoryImpl(private val api: CharactersApi): CharactersRepository {
    override suspend fun getCharactersList(): Flow<List<CharactersResultsResponse>> =
        flow {
            emit(api.getCharacters().results ?: emptyList())
        }

    override suspend fun insertCharactersList(characters: List<CharactersResultsResponse>) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllCharactersDB(): List<CharactersResultsResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun createCharacters(character: CharactersResultsResponse) {
        TODO("Not yet implemented")
    }

    override suspend fun updateCharacters(character: CharactersResultsResponse) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteCharacters(character: CharactersResultsResponse) {
        TODO("Not yet implemented")
    }
}