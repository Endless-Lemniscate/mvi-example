package com.mvi.example.android.di

import com.mvi.example.android.data.repositories.MaterialsRepositoryImpl
import com.mvi.example.android.domain.repositories.MaterialRepository
import org.koin.dsl.module

internal val repositoryModule = module {
    single<MaterialRepository> { MaterialsRepositoryImpl(get()) }
}
