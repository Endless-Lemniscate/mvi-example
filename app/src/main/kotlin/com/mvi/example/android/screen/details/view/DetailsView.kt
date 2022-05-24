package com.mvi.example.android.screen.details.view

import com.arkivanov.mvikotlin.core.view.MviView
import com.mvi.example.android.domain.models.Material
import com.mvi.example.android.screen.details.store.ButtonState

interface DetailsView : MviView<DetailsView.Model, DetailsView.Event> {

    data class Model(
        val material: Material,
        val buttonState: ButtonState
    )

    sealed class Event {
        data class UpdateTitle(val title: String) : Event()
        data class UpdateText(val text: String) : Event()
        object OnButtonClicked : Event()
    }

    sealed class Label {
        data class Exit(val message: String) : Label()
        object EmptyTitle : Label()
        object EmptyText : Label()
        object None : Label()
    }
}