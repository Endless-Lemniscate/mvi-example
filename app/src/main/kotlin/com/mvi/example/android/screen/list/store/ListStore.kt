package com.mvi.example.android.screen.list.store

import com.arkivanov.mvikotlin.core.store.Store
import com.mvi.example.android.domain.models.Material

interface ListStore : Store<ListStore.Intent, ListStore.State, ListStore.Label> {

    sealed class Intent {
        data class ItemClicked(val id: Long) : Intent()
        data class DeleteItem(val id: Long) : Intent()
        object AddItem : Intent()
    }

    data class State(
        val items: List<Material> = emptyList()
    )

    sealed interface Label {
        data class NavigateToDetails(val materialId: Long) : Label
        object None : Label
    }
}