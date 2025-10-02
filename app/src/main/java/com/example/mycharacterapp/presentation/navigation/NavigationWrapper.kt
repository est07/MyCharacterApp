package com.example.mycharacterapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.mycharacterapp.presentation.compose.CharactersRoute

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = CharactersScreen) {
        composable<CharactersScreen> {
            CharactersRoute(
                navigateToEdit = {  }
            )
        }
    }
}