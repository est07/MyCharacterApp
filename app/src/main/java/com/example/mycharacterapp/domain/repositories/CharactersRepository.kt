package com.example.mycharacterapp.domain.repositories

import com.example.mycharacterapp.data.database.entities.CharacterEntity
import com.example.mycharacterapp.domain.models.CharacterModel
import kotlinx.coroutines.flow.Flow

interface CharactersRepository {

    suspend fun getCharacters(): Flow<List<CharacterModel>>

    suspend fun insertCharacters(characters: List<CharacterEntity>)

    fun getAllCharactersDB(): Flow<List<CharacterModel>>

    suspend fun createCharacter(character: CharacterModel)

    suspend fun updateCharacter(character: CharacterModel)

    suspend fun deleteCharacter(character: CharacterModel)
}