package com.mvi.example.android.screen.list.component.store

import com.arkivanov.mvikotlin.core.store.Store
import com.mvi.example.android.domain.models.Material

interface ListStore : Store<ListStore.Intent, ListStore.State, Nothing> {

    sealed class Intent {
        data class DeleteItem(val id: Long) : Intent()
        object AddItem : Intent()
    }

    data class State(
        val items: List<Material> = emptyList()
    )
}