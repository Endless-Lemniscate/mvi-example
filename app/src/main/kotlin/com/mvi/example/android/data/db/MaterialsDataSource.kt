package com.mvi.example.android.data.db

import entities.MaterialEntity
import kotlinx.coroutines.flow.Flow

interface MaterialsDataSource {
    fun getAllMaterials(): Flow<List<MaterialEntity>>

    suspend fun getMaterialsById(id: Long): MaterialEntity?

    suspend fun deleteMaterialById(id: Long)

    suspend fun insertMaterial(title: String, text: String, id: Long? = null)
}