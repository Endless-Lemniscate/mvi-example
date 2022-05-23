package com.mvi.example.android.screen.list.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.mvi.example.android.screen.list.component.store.ListStore
import com.mvi.example.android.screen.list.component.store.ListStoreProvider
import com.mvi.example.android.utils.asValue

class ListComponentImpl(
    componentContext: ComponentContext
) : ListComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        ListStoreProvider().provide()
    }

    override val model: Value<ListComponent.Model> = store.asValue().map {
        ListComponent.Model(it.items)
    }

    override fun onAddItemClicked() {
        store.accept(ListStore.Intent.AddItem)
    }

    override fun onItemDeleteClicked(id: Long) {
        store.accept(ListStore.Intent.DeleteItem(id = id))
    }
}