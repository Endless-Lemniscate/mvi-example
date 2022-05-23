package com.mvi.example.android.di

import com.mvi.example.android.data.db.MaterialDataSourceImpl
import com.mvi.example.android.data.db.MaterialsDataSource
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import database.MaterialsDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single<SqlDriver> {
        AndroidSqliteDriver(MaterialsDatabase.Schema, androidContext(), "app.db")
    }
    single { MaterialsDatabase.invoke(get()) }
    single<MaterialsDataSource> {
        MaterialDataSourceImpl(
            get()
        )
    }
}