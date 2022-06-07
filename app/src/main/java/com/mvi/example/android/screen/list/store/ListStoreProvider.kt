package com.mvi.example.android.screen.list.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.mvi.example.android.domain.models.Material
import com.mvi.example.android.domain.usecases.AddMaterialUseCase
import com.mvi.example.android.domain.usecases.DeleteMaterialByIdUseCase
import com.mvi.example.android.domain.usecases.GetAllMaterialsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ListStoreProvider : KoinComponent {

    private val storeFactory: StoreFactory by inject()
    private val getAllMaterialsUseCase: GetAllMaterialsUseCase by inject()
    private val deleteMaterialByIdUseCase: DeleteMaterialByIdUseCase by inject()
    private val addMaterialUseCase: AddMaterialUseCase by inject()

    fun provide(): ListStore =
        object : ListStore,
            Store<ListStore.Intent, ListStore.State, ListStore.Label> by storeFactory.create(
                name = "ListStore",
                initialState = ListStore.State(),
                bootstrapper = SimpleBootstrapper(Unit),
                executorFactory = ::ExecutorImpl,
                reducer = ReducerImpl
            ) {}

    private sealed class Msg {
        data class ItemsLoaded(val items: List<Material>) : Msg()
        data class ItemDeleted(val id: Long) : Msg()
    }

    private inner class ExecutorImpl :
        CoroutineExecutor<ListStore.Intent, Unit, ListStore.State, Msg, ListStore.Label>() {

        override fun executeAction(action: Unit, getState: () -> ListStore.State) {
            scope.launch(Dispatchers.Main) {
                getAllMaterialsUseCase.invoke()
                    .map(Msg::ItemsLoaded)
                    .collect {
                        dispatch(it)
                    }
            }
        }

        override fun executeIntent(intent: ListStore.Intent, getState: () -> ListStore.State) {
            when (intent) {
                is ListStore.Intent.ItemClicked -> {
                    publish(ListStore.Label.NavigateToDetails(intent.id))
                }
                is ListStore.Intent.AddItem -> {
                    addItem()
                }
                is ListStore.Intent.DeleteItem -> {
                    deleteItem(id = intent.id)
                }
            }
        }

        private fun deleteItem(id: Long) {
            dispatch(Msg.ItemDeleted(id = id))
            scope.launch(Dispatchers.IO) {
                deleteMaterialByIdUseCase.invoke(id)
            }
        }

        private fun addItem() {
            scope.launch(Dispatchers.IO) {
                val material = Material(
                    null,
                    "Новый материал",
                    "Пустое описание"
                )
                addMaterialUseCase.invoke(material)
            }
        }
    }

    private object ReducerImpl : Reducer<ListStore.State, Msg> {
        override fun ListStore.State.reduce(msg: Msg): ListStore.State =
            when (msg) {
                is Msg.ItemsLoaded -> copy(items = msg.items.sorted())
                is Msg.ItemDeleted -> copy(items = items.filterNot { it.id == msg.id })
            }

        private fun Iterable<Material>.sorted(): List<Material> = sortedByDescending(Material::id)
    }
}