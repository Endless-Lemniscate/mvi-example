package com.mvi.example.android.domain.usecases

import com.mvi.example.android.domain.repositories.MaterialRepository

class DeleteMaterialByIdUseCase(private val materialRepository: MaterialRepository) {

    suspend fun invoke(id: Long) {
        materialRepository.deleteMaterialById(id)
    }
}