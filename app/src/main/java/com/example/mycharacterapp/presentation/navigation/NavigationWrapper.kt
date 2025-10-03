package com.example.mycharacterapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.example.mycharacterapp.domain.models.CharacterModel
import com.example.mycharacterapp.presentation.compose.CharactersRoute
import com.example.mycharacterapp.presentation.compose.EditCharacterRoute
import com.example.mycharacterapp.presentation.navigation.type.createNavType
import kotlin.reflect.typeOf

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = CharactersScreen) {
        composable<CharactersScreen> {
            CharactersRoute(
                navigateToEdit = {
                    navController.navigate(EditCharacterScreen(it))
                }
            )
        }

        composable<EditCharacterScreen>(
            typeMap = mapOf(typeOf<CharacterModel>() to createNavType<CharacterModel>())
        ) { navBackStackEntry ->
            val mapData: EditCharacterScreen = navBackStackEntry.toRoute()
            EditCharacterRoute(
                characterModel = mapData.character,
                navigateBack = { navController.popBackStack() }
            )
        }
    }
}