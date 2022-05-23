package com.mvi.example.android.screen.details.component

import com.arkivanov.decompose.value.Value
import com.mvi.example.android.domain.models.Material
import com.mvi.example.android.screen.details.component.store.Label
import kotlinx.coroutines.flow.Flow

interface MaterialDetailsComponent {
    val model: Value<Model>
    val labels: Flow<Label>

    fun updateTitle(title: String)

    fun updateText(text: String)

    fun saveMaterial()
}

data class Model(
    val material: Material
)