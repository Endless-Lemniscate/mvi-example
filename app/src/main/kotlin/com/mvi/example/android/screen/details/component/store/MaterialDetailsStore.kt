package com.mvi.example.android.screen.details.component.store

import com.arkivanov.mvikotlin.core.store.Store
import com.mvi.example.android.domain.models.Material

internal interface MaterialDetailsStore : Store<Intent, State, Label>

sealed interface Intent {
    data class UpdateTitle(val title: String) : Intent
    data class UpdateText(val text: String) : Intent
    object SaveMaterial : Intent
}

data class State(
    val material: Material
) {
    companion object {
        fun initialState(): State {
            return State(
                material = Material(
                    id = null,
                    title = "",
                    text = ""
                )
            )
        }
    }
}

sealed interface Label {
    data class MaterialSaved(val message: String) : Label
    object None : Label
}