package com.mvi.example.android.screen.details.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.mvi.example.android.domain.models.Material
import com.mvi.example.android.domain.usecases.AddMaterialUseCase
import com.mvi.example.android.domain.usecases.GetMaterialByIdUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MaterialDetailsStoreProvider(private val materialId: Long? = null) : KoinComponent {

    private val storeFactory: StoreFactory by inject()
    private val getMaterialByIdUseCase: GetMaterialByIdUseCase by inject()
    private val addMaterialUseCase: AddMaterialUseCase by inject()

    internal fun provide(): MaterialDetailsStore =
        object : MaterialDetailsStore,
            Store<Intent, State, Label> by storeFactory.create(
                name = "MaterialDetailsStore",
                initialState = State.initialState(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Msg {
        data class MaterialLoaded(val item: Material) : Msg()
        data class TitleChanged(val title: String) : Msg()
        data class TextChanged(val text: String) : Msg()
        object DataSaved : Msg()
    }

    private inner class ExecutorImpl : CoroutineExecutor<Intent, Unit, State, Msg, Label>() {

        override fun executeAction(action: Unit, getState: () -> State) {
            scope.launch(Dispatchers.Main) {
                materialId?.let {
                    getMaterialByIdUseCase.invoke(materialId).map {
                        it?.let {
                            dispatch(
                                Msg.MaterialLoaded(it)
                            )
                        }
                    }
                }
            }
        }

        override fun executeIntent(intent: Intent, getState: () -> State) {
            when (intent) {
                is Intent.UpdateTitle -> {
                    dispatch(Msg.TitleChanged(intent.title))
                }
                is Intent.UpdateText -> {
                    dispatch(Msg.TextChanged(intent.text))
                }
                is Intent.OnButtonClicked -> {
                    val state = getState.invoke()
                    when (state.buttonState) {
                        ButtonState.SAVE, ButtonState.CREATE -> {
                            if(state.material.title.isBlank()) {
                                publish(Label.EmptyTitle)
                                return
                            }
                            if(state.material.text.isBlank()) {
                                publish(Label.EmptyText)
                                return
                            }
                            saveMaterial(getState.invoke().material)
                            dispatch(Msg.DataSaved)
                            publish(Label.Exit("MATERIAL SAVED"))
                        }
                        ButtonState.CLOSE -> {
                            publish(Label.Exit("MATERIAL NOT SAVED"))
                        }
                    }
                }
            }
        }

        private fun saveMaterial(material: Material) {
            scope.launch(Dispatchers.IO) {
                addMaterialUseCase.invoke(material)
            }
        }
    }

    private object ReducerImpl : Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.MaterialLoaded -> copy(material = msg.item)
                is Msg.TitleChanged -> {
                    copy(
                        material = material.copy(title = msg.title),
                        buttonState = if (material.id == null) ButtonState.CREATE else ButtonState.SAVE
                    )
                }
                is Msg.TextChanged -> {
                    copy(
                        material = material.copy(text = msg.text),
                        buttonState = if (material.id == null) ButtonState.CREATE else ButtonState.SAVE
                    )
                }
                Msg.DataSaved -> {
                    copy(buttonState = ButtonState.CLOSE)
                }
            }
    }
}