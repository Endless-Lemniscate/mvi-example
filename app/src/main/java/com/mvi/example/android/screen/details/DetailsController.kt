package com.mvi.example.android.screen.details

import com.arkivanov.mvikotlin.core.binder.Binder
import com.arkivanov.mvikotlin.extensions.coroutines.bind
import com.arkivanov.mvikotlin.extensions.coroutines.events
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.states
import com.github.terrakok.cicerone.Router
import com.mvi.example.android.screen.details.Mappers.eventToIntent
import com.mvi.example.android.screen.details.Mappers.labelToLabel
import com.mvi.example.android.screen.details.Mappers.stateToModel
import com.mvi.example.android.screen.details.store.MaterialDetailsStoreProvider
import com.mvi.example.android.screen.details.view.DetailsView
import kotlinx.coroutines.flow.map

class DetailsController(
    materialId: Long,
    private val router: Router,
    private val showToast: (String) -> Unit
) {
    private val store = MaterialDetailsStoreProvider(materialId).provide()
    private var binder: Binder? = null

    fun onViewCreated(view: DetailsView) {
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

    private fun consumeEvent(event: DetailsView.Label) {
        when (event) {
            is DetailsView.Label.EmptyTitle -> {
                showToast("Заполните заголовок")
            }
            DetailsView.Label.EmptyText -> {
                showToast("Заполните описание")
            }
            is DetailsView.Label.Exit -> {
                router.exit()
            }
            DetailsView.Label.None -> Unit
        }
    }
}