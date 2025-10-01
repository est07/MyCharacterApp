package com.example.mycharacterapp.presentation.di

import androidx.room.Room
import com.example.mycharacterapp.data.database.CharactersDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val CHARACTERS_DATABASE_NAME = "characters_database"

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidContext(),
            CharactersDatabase::class.java,
            CHARACTERS_DATABASE_NAME
        ).build()
    }
    single { get<CharactersDatabase>().getCharactersDao() }
}