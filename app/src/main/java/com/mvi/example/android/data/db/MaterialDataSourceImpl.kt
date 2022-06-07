package com.mvi.example.android.data.db

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import database.MaterialsDatabase
import entities.MaterialEntity

class MaterialDataSourceImpl(
    db: MaterialsDatabase
) : MaterialsDataSource {

    private val queries = db.materialEntityQueries

    override fun getAllMaterials(): Flow<List<MaterialEntity>> {
        return queries.getAllMaterials().asFlow().mapToList()
    }

    override suspend fun getMaterialsById(id: Long): MaterialEntity? {
        return withContext(Dispatchers.IO) {
            queries.getMaterialById(id).executeAsOneOrNull()
        }
    }

    override suspend fun deleteMaterialById(id: Long) {
        withContext(Dispatchers.IO) {
            queries.deleteMaterialById(id)
        }
    }

    override suspend fun insertMaterial(title: String, text: String, id: Long?) {
        withContext(Dispatchers.IO) {
            queries.insertMaterial(id, title, text)
        }
    }
}