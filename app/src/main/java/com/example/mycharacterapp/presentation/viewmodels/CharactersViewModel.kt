package com.example.mycharacterapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mycharacterapp.domain.usecases.CharactersUseCase
import com.example.mycharacterapp.presentation.states.CharactersStates
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class CharactersViewModel(
    private val charactersUseCase: CharactersUseCase,
) : ViewModel() {

    private val _characterListState
        get() = MutableStateFlow<CharactersStates>(CharactersStates.Default)

    val characterListState: StateFlow<CharactersStates>
        get() = _characterListState.asStateFlow()

    fun getCharacters() {
        viewModelScope.launch {
            charactersUseCase.getCharactersList()
                .onStart {
                    _characterListState.value = CharactersStates.Loading }
                .catch {
                    _characterListState.value = CharactersStates.Error
                }
                .collect {
                    _characterListState.value = CharactersStates.Success
                }
        }
    }
}