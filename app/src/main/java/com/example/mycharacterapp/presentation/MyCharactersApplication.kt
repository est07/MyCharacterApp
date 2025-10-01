package com.example.mycharacterapp.presentation

import android.app.Application
import com.example.mycharacterapp.presentation.di.repositoriesModule
import com.example.mycharacterapp.presentation.di.serviceModule
import com.example.mycharacterapp.presentation.di.useCasesModule
import com.example.mycharacterapp.presentation.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin

class MyCharactersApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyCharactersApplication)
            loadKoinModules(
                listOf(
                    serviceModule,
                    repositoriesModule,
                    useCasesModule,
                    viewModelModule
                )
            )
        }
    }
}