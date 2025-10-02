package com.example.mycharacterapp.presentation.di

import com.example.mycharacterapp.presentation.viewmodels.CharactersViewModel
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        CharactersViewModel(
            charactersUseCase = get(),
            ioDispatcher = Dispatchers.IO
        )
    }
}