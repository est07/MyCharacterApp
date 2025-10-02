package com.example.mycharacterapp.presentation.states

sealed class CharactersStates {
    data object Success : CharactersStates()
    data object Default : CharactersStates()
    data object Loading : CharactersStates()
    data object Error : CharactersStates()
}