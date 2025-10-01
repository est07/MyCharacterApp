package com.example.mycharacterapp.domain.usecases

import com.example.mycharacterapp.data.database.entities.toDomain
import com.example.mycharacterapp.domain.models.CharacterModel
import com.example.mycharacterapp.domain.repositories.CharactersRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext

class CharactersUseCase(
    private val charactersRepository: CharactersRepository,
    private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun getCharactersList() = withContext(ioDispatcher) {
        charactersRepository.getCharacters().onEach { charactersList ->
            charactersRepository.insertCharacters(charactersList.map { it.toDomain() })
        }
    }

    suspend fun getAllCharactersDB() =
        withContext(ioDispatcher) {
            charactersRepository.getAllCharactersDB()
        }

    suspend fun createCharacters(character: CharacterModel) =
        withContext(ioDispatcher) {
            charactersRepository.createCharacter(character)
        }

    suspend fun updateCharacters(character: CharacterModel) =
        withContext(ioDispatcher) {
            charactersRepository.updateCharacter(character)
        }

    suspend fun deleteCharacters(character: CharacterModel) =
        withContext(ioDispatcher) {
            charactersRepository.deleteCharacter(character)
        }
}