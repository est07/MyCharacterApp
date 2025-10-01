package com.example.mycharacterapp.presentation.states

import com.example.mycharacterapp.data.network.responses.CharactersResultsResponse

sealed class CharactersStates {
    data class Success(val characters: List<CharactersResultsResponse>) : CharactersStates()
    data object Default : CharactersStates()// Assuming cities are represented as a list of strings
    data object Loading : CharactersStates()
    data object Error : CharactersStates()
}