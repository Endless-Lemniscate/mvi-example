package com.mvi.example.android.domain.repositories

import com.mvi.example.android.domain.models.Material
import kotlinx.coroutines.flow.Flow

interface MaterialRepository {
    fun getAllMaterials(): Flow<List<Material>>
    suspend fun getMaterialById(id: Long): Material?
    suspend fun addMaterial(material: Material)
    suspend fun deleteMaterialById(id: Long)
}