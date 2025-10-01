package com.example.mycharacterapp.domain.usecases

import com.example.mycharacterapp.data.network.responses.CharactersResultsResponse
import com.example.mycharacterapp.domain.repositories.CharactersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class CharactersUseCase(
    private val charactersRepository: CharactersRepository,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getCharactersList() = withContext(ioDispatcher) {
        charactersRepository.getCharactersList()
    }

    suspend fun insertCharactersList(characters: List<CharactersResultsResponse>) {}

    suspend fun getAllCharactersDB() {}

    suspend fun createCharacters(character: CharactersResultsResponse) {}

    suspend fun updateCharacters(character: CharactersResultsResponse) {}

    suspend fun deleteCharacters(character: CharactersResultsResponse){}
}