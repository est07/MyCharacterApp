package com.example.mycharacterapp.presentation.di

import com.example.mycharacterapp.data.repositories.CitiesRepositoryImpl
import com.example.mycharacterapp.domain.repositories.CharactersRepository
import org.koin.dsl.module

val repositoriesModule = module {
    factory<CharactersRepository> { CitiesRepositoryImpl(api = get()) }
}