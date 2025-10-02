package com.example.mycharacterapp.domain.usecases

import com.example.mycharacterapp.data.database.entities.toDomain
import com.example.mycharacterapp.domain.models.CharacterModel
import com.example.mycharacterapp.domain.repositories.CharactersRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CharactersUseCaseTest {

    private val charactersRepository = mockk<CharactersRepository>()
    private val ioDispatcher = Dispatchers.IO

    private lateinit var charactersUseCase: CharactersUseCase

    @Before
    fun setUp() {
        charactersUseCase = CharactersUseCase(
            charactersRepository = charactersRepository,
            ioDispatcher = ioDispatcher
        )
    }

    @Test
    fun getCharactersTest() = runBlocking {
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
            charactersRepository.getCharacters()
        } returns flowOf(characters)

        coEvery {
            charactersRepository.insertCharacters(characters.map { it.toDomain() })
        } returns Unit

        val result = charactersUseCase.getCharacters()

        launch {
            result.onEach {
                charactersRepository.insertCharacters(characters.map { it.toDomain() })
            }.collect{}
        }

        coVerify {
            charactersRepository.getCharacters()
            charactersRepository.insertCharacters(characters.map { it.toDomain() })
        }
    }

    @Test
    fun getAllCharactersDBTest() = runBlocking {
        val characters =  mockk<List<CharacterModel>>(relaxed = true)

        coEvery {
            charactersRepository.getAllCharactersDB()
        } returns flowOf(characters)

        charactersUseCase.getAllCharactersDB().collect {
            assert(characters == it)
        }

        coVerify {
            charactersRepository.getAllCharactersDB()
        }
        confirmVerified(charactersRepository)
    }

    @Test
    fun createCharactersTest() = runBlocking {
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
            charactersRepository.createCharacter(character)
        } returns Unit

        charactersUseCase.createCharacter(character)

        coVerify {
            charactersRepository.createCharacter(character)
        }
        confirmVerified(charactersRepository)
    }

    @Test
    fun updateCharactersTest() = runBlocking {
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
            charactersRepository.updateCharacter(character)
        } returns Unit

        charactersUseCase.updateCharacter(character)

        coVerify {
            charactersRepository.updateCharacter(character)
        }
        confirmVerified(charactersRepository)
    }

    @Test
    fun deleteCharactersTest() = runBlocking {
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
            charactersRepository.deleteCharacter(character)
        } returns Unit

        charactersUseCase.deleteCharacter(character)

        coVerify {
            charactersRepository.deleteCharacter(character)
        }
        confirmVerified(charactersRepository)
    }
}