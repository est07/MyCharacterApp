package com.example.mycharacterapp.presentation.states

sealed class CharactersStates {
    data object Success : CharactersStates()
    data object Default : CharactersStates()// Assuming cities are represented as a list of strings
    data object Loading : CharactersStates()
    data object Error : CharactersStates()
}