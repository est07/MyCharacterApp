package com.example.mycharacterapp.data.repositories

import com.example.mycharacterapp.data.database.dao.CharactersDao
import com.example.mycharacterapp.data.database.entities.CharacterEntity
import com.example.mycharacterapp.data.database.entities.toDomain
import com.example.mycharacterapp.data.network.apis.CharactersApi
import com.example.mycharacterapp.data.network.responses.CharactersResponse
import com.example.mycharacterapp.domain.models.CharacterModel
import com.example.mycharacterapp.domain.models.toDomain
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class CharactersRepositoryImplTest {

    private val api = mockk<CharactersApi>()
    private val charactersDao = mockk<CharactersDao>()

    private lateinit var charactersRepositoryImpl: CharactersRepositoryImpl

    @Before
    fun setUp() {
        charactersRepositoryImpl = CharactersRepositoryImpl(
            api = api,
            charactersDao = charactersDao
        )
    }

    @Test
    fun getCharactersTest() = runBlocking {
        val citiesResponse =  mockk<CharactersResponse>(relaxed = true)

        coEvery { api.getCharacters() } returns citiesResponse

        charactersRepositoryImpl.getCharacters().collect { characters ->
            assert(characters == citiesResponse.results?.map { it.toDomain() })
        }
        coVerify { api.getCharacters() }

        confirmVerified(api)
    }

    @Test
    fun insertCharactersTest()  = runBlocking {
        val characters = listOf(
            CharacterModel(
                id = 1,
                name = "name",
                status = "status",
                species = "species",
                gender = "gender",
                image = "image",
                created = "created"
            ),
            CharacterModel(
                id = 2,
                name = "name2",
                status = "status2",
                species = "species2",
                gender = "gender2",
                image = "image2",
                created = "created2"
            )
        )

        coEvery {
            charactersDao.insertAll(
                characters.map { it.toDomain() }
            )
        } returns Unit

        coEvery {
            charactersDao.getAllCharacters()
        } returns flow { characters.map { it.toDomain() } }

        charactersRepositoryImpl.insertCharacters(characters.map { it.toDomain() })

        charactersRepositoryImpl.getAllCharactersDB().collect {
            assertEquals(characters , it)

            coVerify {
                charactersDao.insertAll(
                    characters.map { characters -> characters.toDomain() }
                )
                charactersDao.getAllCharacters()
            }
            confirmVerified(charactersDao)
        }
    }

    @Test
    fun getAllCharactersDBTest() = runBlocking {
        val characters =  mockk<List<CharacterEntity>>(relaxed = true)

        coEvery {
            charactersDao.getAllCharacters()
        } returns flow {
            characters
        }

        charactersRepositoryImpl.getAllCharactersDB().collect { charactersDB ->
            assertEquals(charactersDB , characters.map { it.toDomain() })

            coVerify { charactersDao.getAllCharacters() }

            confirmVerified(charactersDao)
        }
    }

    @Test
    fun createCharacterTest() = runBlocking {

        val character = CharacterModel(
            id = 1,
            name = "name",
            status = "status",
            species = "species",
            gender = "gender",
            image = "image",
            created = "created"
        )

        coEvery {
            charactersDao.insert(character.toDomain())
        } returns Unit

        charactersRepositoryImpl.createCharacter(character)

        coVerify { charactersDao.insert(character.toDomain()) }

        confirmVerified(charactersDao)
    }

    @Test
    fun updateCharacterTest() = runBlocking {
        val character = CharacterModel(
            id = 1,
            name = "name",
            status = "status",
            species = "species",
            gender = "gender",
            image = "image",
            created = "created"
        )

        coEvery {
            charactersDao.updateCharacter(character.toDomain())
        } returns Unit

        val updateCharacterDB = charactersRepositoryImpl.updateCharacter(character)

        assertEquals(Unit , updateCharacterDB)

        coVerify { charactersDao.updateCharacter(character.toDomain()) }

        confirmVerified(charactersDao)
    }

    @Test
    fun deleteCharacterTest() = runBlocking {
        val character = CharacterModel(
            id = 1,
            name = "name",
            status = "status",
            species = "species",
            gender = "gender",
            image = "image",
            created = "created"
        )

        coEvery {
            charactersDao.delete(character.toDomain())
        } returns Unit

        val deleteCharacterDB = charactersRepositoryImpl.deleteCharacter(character)

        assertEquals(Unit , deleteCharacterDB)

        coVerify { charactersDao.delete(character.toDomain()) }

        confirmVerified(charactersDao)
    }
}