package com.mvi.example.android.data.repositories

import com.mvi.example.android.data.db.MaterialsDataSource
import com.mvi.example.android.data.models.toModel
import com.mvi.example.android.domain.models.Material
import com.mvi.example.android.domain.repositories.MaterialRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class MaterialsRepositoryImpl(private val dataSource: MaterialsDataSource) : MaterialRepository {

    override fun getAllMaterials(): Flow<List<Material>> {
        return dataSource.getAllMaterials().map { list ->
            list.map {
                it.toModel()
            }
        }
    }

    override suspend fun getMaterialById(id: Long): Material? {
        return dataSource.getMaterialsById(id)?.toModel()
    }

    override suspend fun addMaterial(material: Material) {
        dataSource.insertMaterial(material.title, material.text, material.id)
    }

    override suspend fun deleteMaterialById(id: Long) {
        dataSource.deleteMaterialById(id)
    }
}