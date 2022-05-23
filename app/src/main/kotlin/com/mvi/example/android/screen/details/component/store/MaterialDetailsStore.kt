package com.mvi.example.android.screen.details.component.store

import com.arkivanov.mvikotlin.core.store.Store
import com.mvi.example.android.domain.models.Material

internal interface MaterialDetailsStore : Store<Intent, State, Label>

sealed interface Intent {
    data class UpdateTitle(val title: String) : Intent
    data class UpdateText(val text: String) : Intent
    object OnButtonClicked : Intent
}

data class State(
    val material: Material,
    val buttonState: ButtonState
) {
    companion object {
        fun initialState(): State {
            return State(
                material = Material(
                    id = null,
                    title = "",
                    text = ""
                ),
                ButtonState.CLOSE
            )
        }
    }
}

sealed interface Label {
    data class Exit(val message: String) : Label
    object EmptyTitle : Label
    object EmptyText : Label
    object None : Label
}