package com.mvi.example.android.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.dsl.module

internal val navigationModule = module {
    val cicerone: Cicerone<Router> = Cicerone.create(Router())
    single { cicerone.router }
    single { cicerone.getNavigatorHolder() }
}
