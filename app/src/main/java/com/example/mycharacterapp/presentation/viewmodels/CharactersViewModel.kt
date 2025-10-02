package com.example.mycharacterapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycharacterapp.domain.models.CharacterModel
import com.example.mycharacterapp.domain.usecases.CharactersUseCase
import com.example.mycharacterapp.presentation.states.CharactersStates
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val charactersUseCase: CharactersUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _charactersState =
        MutableStateFlow<CharactersStates>(CharactersStates.Default)

    val charactersState: StateFlow<CharactersStates>
        get() = _charactersState.asStateFlow()

    private val _localCharacterState =
        MutableStateFlow(emptyList<CharacterModel>())

    val localCharacterState = _localCharacterState.asStateFlow()

    fun getCharacters() {
        viewModelScope.launch {
            charactersUseCase.getCharacters()
                .onStart {
                    _charactersState.value = CharactersStates.Loading
                }
                .catch {
                    _charactersState.value = CharactersStates.Error
                }
                .collect {
                    if (it.isNotEmpty()) {
                        _charactersState.value = CharactersStates.Success
                    } else {
                        _charactersState.value = CharactersStates.Error
                    }
                }
        }
    }

    fun getLocalCharacters() {
        viewModelScope.launch {
            charactersUseCase.getAllCharactersDB()
                .flowOn(ioDispatcher)
                .collect { list ->
                    _localCharacterState.update { list }
                }
        }
    }

    fun createCharacter(character: CharacterModel) {
        viewModelScope.launch {
            charactersUseCase.createCharacter(character)
        }
    }

    fun updateCharacter(character: CharacterModel) {
        viewModelScope.launch {
            charactersUseCase.updateCharacter(character)
        }
    }

    fun deleteCharacter(character: CharacterModel) {
        viewModelScope.launch {
            charactersUseCase.deleteCharacter(character)
        }
    }
}