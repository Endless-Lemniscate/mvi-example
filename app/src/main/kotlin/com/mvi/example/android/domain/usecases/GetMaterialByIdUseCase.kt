package com.mvi.example.android.domain.usecases

import com.mvi.example.android.domain.models.Material
import com.mvi.example.android.domain.repositories.MaterialRepository

class GetMaterialByIdUseCase(private val materialRepository: MaterialRepository) {
    suspend fun invoke(id: Long): Result<Material?> {
        return Result.success(materialRepository.getMaterialById(id))
    }
}