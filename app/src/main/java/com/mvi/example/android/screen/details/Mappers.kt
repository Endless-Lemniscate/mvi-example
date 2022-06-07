package com.mvi.example.android.screen.details

import com.mvi.example.android.screen.details.store.Intent
import com.mvi.example.android.screen.details.store.Label
import com.mvi.example.android.screen.details.store.State
import com.mvi.example.android.screen.details.view.DetailsView

internal object Mappers {

    internal val stateToModel: State.() -> DetailsView.Model = {
        DetailsView.Model(
            material = material,
            buttonState = buttonState
        )
    }

    internal val labelToLabel: Label.() -> DetailsView.Label = {
        when (this) {
            is Label.EmptyTitle -> DetailsView.Label.EmptyTitle
            is Label.EmptyText -> DetailsView.Label.EmptyText
            is Label.Exit -> DetailsView.Label.Exit(this.message)
            is Label.None -> DetailsView.Label.None
        }
    }

    internal val eventToIntent: DetailsView.Event.() -> Intent = {
        when (this) {
            is DetailsView.Event.UpdateTitle -> Intent.UpdateTitle(title)
            is DetailsView.Event.UpdateText -> Intent.UpdateText(text)
            DetailsView.Event.OnButtonClicked -> Intent.OnButtonClicked
        }
    }
}