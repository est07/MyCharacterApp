package com.example.mycharacterapp.presentation.states

import com.example.mycharacterapp.domain.models.CharacterModel

sealed interface LocalCharactersState {
    data object Loading : LocalCharactersState

    data class Success(
        val localCharacters: List<CharacterModel> = emptyList(),
    ) : LocalCharactersState
}