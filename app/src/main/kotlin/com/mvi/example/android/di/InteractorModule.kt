package com.mvi.example.android.di

import com.mvi.example.android.domain.usecases.AddMaterialUseCase
import com.mvi.example.android.domain.usecases.DeleteMaterialByIdUseCase
import com.mvi.example.android.domain.usecases.GetAllMaterialsUseCase
import com.mvi.example.android.domain.usecases.GetMaterialByIdUseCase
import org.koin.dsl.module

internal val interactorModule = module {
    single { GetAllMaterialsUseCase(get()) }
    single { AddMaterialUseCase(get()) }
    single { DeleteMaterialByIdUseCase(get()) }
    single { GetMaterialByIdUseCase(get()) }
}