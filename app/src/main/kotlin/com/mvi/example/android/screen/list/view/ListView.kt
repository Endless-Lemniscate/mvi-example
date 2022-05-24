package com.mvi.example.android.screen.list.view

import com.arkivanov.mvikotlin.core.view.MviView
import com.mvi.example.android.domain.models.Material

interface ListView : MviView<ListView.Model, ListView.Event> {

    data class Model(
        val items: List<Material>
    )

    sealed class Event {
        data class ItemClicked(val id: Long) : Event()
        data class DeleteItem(val id: Long) : Event()
        object AddItem : Event()
    }

    sealed interface Label {
        data class NavigateToDetails(val materialId: Long) : Label
        object None : Label
    }
}