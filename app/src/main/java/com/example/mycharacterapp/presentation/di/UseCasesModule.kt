package com.example.mycharacterapp.presentation.di

import com.example.mycharacterapp.domain.usecases.CharactersUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module

val useCasesModule = module {
    factory {
        CharactersUseCase(
            charactersRepository = get(),
            ioDispatcher = Dispatchers.IO
        )
    }
}