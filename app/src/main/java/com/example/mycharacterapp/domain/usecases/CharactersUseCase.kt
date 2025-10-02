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

    suspend fun getCharacters() = withContext(ioDispatcher) {
        charactersRepository.getCharacters().onEach { charactersList ->
            charactersRepository.insertCharacters(charactersList.map { it.toDomain() })
        }
    }

    fun getAllCharactersDB() = charactersRepository.getAllCharactersDB()

    suspend fun createCharacter(character: CharacterModel) =
        withContext(ioDispatcher) {
            charactersRepository.createCharacter(character)
        }

    suspend fun updateCharacter(character: CharacterModel) =
        withContext(ioDispatcher) {
            charactersRepository.updateCharacter(character)
        }

    suspend fun deleteCharacter(character: CharacterModel) =
        withContext(ioDispatcher) {
            charactersRepository.deleteCharacter(character)
        }
}