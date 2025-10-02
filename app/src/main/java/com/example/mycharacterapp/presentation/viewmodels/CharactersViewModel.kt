package com.example.mycharacterapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycharacterapp.domain.models.CharacterModel
import com.example.mycharacterapp.domain.usecases.CharactersUseCase
import com.example.mycharacterapp.presentation.states.CharactersStates
import com.example.mycharacterapp.presentation.states.LocalCharactersState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

private const val TIMEOUT_MILLIS = 5_000L

class CharactersViewModel(
    private val charactersUseCase: CharactersUseCase,
) : ViewModel() {

    private val _charactersState =
        MutableStateFlow<CharactersStates>(CharactersStates.Default)

    val charactersState: StateFlow<CharactersStates>
        get() = _charactersState.asStateFlow()

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