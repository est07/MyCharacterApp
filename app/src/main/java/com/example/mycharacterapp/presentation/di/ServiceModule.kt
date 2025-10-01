package com.example.mycharacterapp.presentation.di

import com.example.mycharacterapp.data.network.apis.CharactersApi
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit

val serviceModule = module {
    single(named(RETROFIT_API)) { ManagerNetwork().createWebService() }
    single { get<Retrofit>(named(RETROFIT_API)).create(CharactersApi::class.java) }
}