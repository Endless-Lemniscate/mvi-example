package com.mvi.example.android.screen.list

import com.mvi.example.android.screen.list.store.ListStore
import com.mvi.example.android.screen.list.view.ListView

internal object Mappers {
    internal val stateToModel: ListStore.State.() -> ListView.Model = {
        ListView.Model(
            items = items
        )
    }

    internal val labelToLabel: ListStore.Label.() -> ListView.Label = {
        when(this) {
            is ListStore.Label.NavigateToDetails -> ListView.Label.NavigateToDetails(materialId)
            ListStore.Label.None -> ListView.Label.None
        }
    }

    internal val eventToIntent: ListView.Event.() -> ListStore.Intent = {
        when (this) {
            is ListView.Event.AddItem -> ListStore.Intent.AddItem
            is ListView.Event.DeleteItem -> ListStore.Intent.DeleteItem(id)
            is ListView.Event.ItemClicked -> ListStore.Intent.ItemClicked(id)
        }
    }
}