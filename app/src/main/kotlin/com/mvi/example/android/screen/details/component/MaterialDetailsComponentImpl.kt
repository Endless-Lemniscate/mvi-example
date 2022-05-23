package com.mvi.example.android.screen.details.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.mvi.example.android.screen.details.component.store.Intent
import com.mvi.example.android.screen.details.component.store.Label
import com.mvi.example.android.screen.details.component.store.MaterialDetailsStoreProvider
import com.mvi.example.android.utils.asValue
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

internal class MaterialDetailsComponentImpl(
    componentContext: ComponentContext,
    materialId: Long,
    goBack: () -> Unit
) :
    MaterialDetailsComponent,
    ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        MaterialDetailsStoreProvider(materialId).provide()
    }

    override val model: Value<Model> = store.asValue().map {
        Model(it.material)
    }

    override val labels: Flow<Label> = store.labels.transform {
        if (it is Label.MaterialSaved) {
            goBack.invoke()
        }
    }

    override fun updateTitle(title: String) {
        store.accept(Intent.UpdateTitle(title))
    }

    override fun updateText(text: String) {
        store.accept(Intent.UpdateText(text))
    }

    override fun saveMaterial() {
        store.accept(Intent.SaveMaterial)
    }
}