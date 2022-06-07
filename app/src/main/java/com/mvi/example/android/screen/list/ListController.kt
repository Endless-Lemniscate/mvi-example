package com.mvi.example.android.screen.list

import com.arkivanov.mvikotlin.core.binder.Binder
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.events
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.github.terrakok.cicerone.Router
import com.mvi.example.android.Screens
import com.mvi.example.android.screen.list.Mappers.eventToIntent
import com.mvi.example.android.screen.list.Mappers.labelToLabel
import com.mvi.example.android.screen.list.Mappers.stateToModel
import com.mvi.example.android.screen.list.store.ListStoreProvider
import com.mvi.example.android.screen.list.view.ListView
import kotlinx.coroutines.flow.map

class ListController(private val router: Router) {
    private val store = ListStoreProvider().provide()
    private var binder: Binder? = null

    fun onViewCreated(view: ListView) {
        binder = bind {
            store.states.map(stateToModel) bindTo view
            store.labels.map(labelToLabel) bindTo ::consumeEvent
            view.events.map(eventToIntent) bindTo store
        }
    }

    fun onStart() {
        binder?.start()
    }

    fun onStop() {
        binder?.stop()
    }

    fun onViewDestroyed() {
        binder = null
    }

    fun onDestroy() {
        store.dispose()
    }

    private fun consumeEvent(event: ListView.Label) {
        when (event) {
            is ListView.Label.NavigateToDetails -> {
                router.navigateTo(Screens.details(event.materialId))
            }
            ListView.Label.None -> Unit
        }
    }
}