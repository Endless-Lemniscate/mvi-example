package com.mvi.example.android.domain.usecases

import com.mvi.example.android.domain.models.Material
import com.mvi.example.android.domain.repositories.MaterialRepository
import kotlinx.coroutines.flow.Flow

class GetAllMaterialsUseCase(private val materialRepository: MaterialRepository) {

    fun invoke(): Flow<List<Material>> {
        return materialRepository.getAllMaterials()
    }
}