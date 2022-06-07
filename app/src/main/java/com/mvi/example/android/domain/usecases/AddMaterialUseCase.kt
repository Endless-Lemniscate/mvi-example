package com.mvi.example.android.domain.usecases

import com.mvi.example.android.domain.models.Material
import com.mvi.example.android.domain.repositories.MaterialRepository

class AddMaterialUseCase(private val materialRepository: MaterialRepository) {

    suspend fun invoke(material: Material) {
        materialRepository.addMaterial(material)
    }
}