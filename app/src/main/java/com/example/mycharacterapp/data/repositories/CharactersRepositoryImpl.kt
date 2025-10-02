package com.example.mycharacterapp.data.repositories

import com.example.mycharacterapp.data.database.dao.CharactersDao
import com.example.mycharacterapp.data.database.entities.CharacterEntity
import com.example.mycharacterapp.data.database.entities.toDomain
import com.example.mycharacterapp.data.network.apis.CharactersApi
import com.example.mycharacterapp.domain.models.CharacterModel
import com.example.mycharacterapp.domain.models.toDomain
import com.example.mycharacterapp.domain.repositories.CharactersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class CharactersRepositoryImpl(
    private val api: CharactersApi,
    private val charactersDao: CharactersDao
): CharactersRepository {

    override suspend fun getCharacters(): Flow<List<CharacterModel>> =
        flow {
            emit(api.getCharacters().results?.map { it.toDomain() } ?: emptyList())
        }

    override suspend fun insertCharacters(characters: List<CharacterEntity>) {
        charactersDao.insertAll(characters)
    }

    override fun getAllCharactersDB(): Flow<List<CharacterModel>> =
        charactersDao.getAllCharacters()
            .map { charactersDao -> charactersDao.map { it.toDomain() } }

    override suspend fun createCharacter(character: CharacterModel) {
        charactersDao.insert(character.toDomain())
    }

    override suspend fun updateCharacter(character: CharacterModel) {
        charactersDao.updateCharacter(character.toDomain())
    }

    override suspend fun deleteCharacter(character: CharacterModel) {
        charactersDao.delete(character.toDomain())
    }
}