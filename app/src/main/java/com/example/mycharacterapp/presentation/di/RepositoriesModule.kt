package com.example.mycharacterapp.presentation.di

import com.example.mycharacterapp.data.repositories.CharactersRepositoryImpl
import com.example.mycharacterapp.domain.repositories.CharactersRepository
import org.koin.dsl.module

val repositoriesModule = module {
    factory<CharactersRepository> { CharactersRepositoryImpl(api = get(), charactersDao = get()) }
}