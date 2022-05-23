package com.mvi.example.android.screen.list.component

import com.arkivanov.decompose.value.Value
import com.mvi.example.android.domain.models.Material

interface ListComponent {

    val model: Value<Model>

    fun onItemDeleteClicked(id: Long)

    fun onAddItemClicked()

    data class Model(
        val items: List<Material>
    )
}