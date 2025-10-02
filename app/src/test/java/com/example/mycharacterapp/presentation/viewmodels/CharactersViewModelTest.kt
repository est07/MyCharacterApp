package com.example.mycharacterapp.presentation.viewmodels

import com.example.mycharacterapp.MainDispatcherRule
import com.example.mycharacterapp.domain.models.CharacterModel
import com.example.mycharacterapp.domain.usecases.CharactersUseCase
import com.example.mycharacterapp.presentation.states.CharactersStates
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CharactersViewModelTest {

    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    private val charactersUseCase = mockk<CharactersUseCase>()

    private val ioDispatcher = Dispatchers.IO

    private lateinit var viewModel: CharactersViewModel

    @Before
    fun setUp() {
        viewModel = CharactersViewModel(charactersUseCase, ioDispatcher)
    }

    @Test
    fun getCharactersStateTest() = runBlocking {
        assertEquals(
            viewModel.charactersState.value,
            CharactersStates.Default
        )
    }

    @Test
    fun getLocalCharacterStateTest() = runBlocking {
        assertEquals(
            viewModel.localCharacterState.value,
            emptyList<CharacterModel>()
        )
    }

    @Test
    fun getCharacters_success_response_test() = runTest {
        val characters =  mockk<List<CharacterModel>>(relaxed = true)

        coEvery {
            charactersUseCase.getCharacters()
        } returns flowOf(characters)

        viewModel.getCharacters()

        assertEquals(viewModel.charactersState.value, CharactersStates.Success)

        coVerify {
            charactersUseCase.getCharacters()
        }
        confirmVerified(charactersUseCase)
    }

    @Test
    fun getCharacters_empty_response_test() = runTest {

        val characters = emptyList<CharacterModel>()

        coEvery {
            charactersUseCase.getCharacters()
        } returns flowOf(characters)

        viewModel.getCharacters()

        assertEquals(viewModel.charactersState.value, CharactersStates.Error)

        coVerify {
            charactersUseCase.getCharacters()
        }
        confirmVerified(charactersUseCase)
    }

    @Test
    fun getCharacters_error_response_test() = runTest {

        val throwable = mockk<Throwable>()
        val flow = flow<List<CharacterModel>> { throw throwable }

        coEvery {
            charactersUseCase.getCharacters()
        } returns flow

        viewModel.getCharacters()

        assertEquals(viewModel.charactersState.value, CharactersStates.Error)

        coVerify {
            charactersUseCase.getCharacters()
        }
        confirmVerified(charactersUseCase)
    }

    @Test
    fun getLocalCharacters_success_response_test() = runTest {
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
            charactersUseCase.getAllCharactersDB()
        } returns flowOf(characters)

        viewModel.getLocalCharacters()

        assertEquals(viewModel.localCharacterState.value, characters)
    }

    @Test
    fun createCharacterTest() = runTest {
        val character = mockk<CharacterModel>(relaxed = true)

        coEvery {
            charactersUseCase.createCharacter(character)
        } returns Unit

        viewModel.createCharacter(character)

        coVerify {
            charactersUseCase.createCharacter(character)
        }
        confirmVerified(charactersUseCase)
    }

    @Test
    fun updateCharacterTest() = runTest {
        val character = mockk<CharacterModel>(relaxed = true)

        coEvery {
            charactersUseCase.updateCharacter(character)
        } returns Unit

        viewModel.updateCharacter(character)

        coVerify {
            charactersUseCase.updateCharacter(character)
        }
        confirmVerified(charactersUseCase)
    }

    @Test
    fun deleteCharacterTest() = runTest {

        val character = mockk<CharacterModel>(relaxed = true)

        coEvery {
            charactersUseCase.deleteCharacter(character)
        } returns Unit

        viewModel.deleteCharacter(character)

        coVerify {
            charactersUseCase.deleteCharacter(character)
        }
        confirmVerified(charactersUseCase)
    }
}