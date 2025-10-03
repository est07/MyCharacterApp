package com.example.mycharacterapp.presentation.navigation

import com.example.mycharacterapp.domain.models.CharacterModel
import kotlinx.serialization.Serializable

@Serializable
object CharactersScreen

@Serializable
data class EditCharacterScreen(val character: CharacterModel)