package com.mvi.example.android.screen.list.flow

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.navigate
import com.arkivanov.decompose.router.pop
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.mvi.example.android.screen.details.component.MaterialDetailsComponent
import com.mvi.example.android.screen.details.component.MaterialDetailsComponentImpl
import com.mvi.example.android.screen.list.component.ListComponent
import com.mvi.example.android.screen.list.component.ListComponentImpl
import kotlinx.parcelize.Parcelize

class ListFlowComponentImpl(
    componentContext: ComponentContext
) : ListFlowComponent, ComponentContext by componentContext {

    init {
        backPressedHandler.register(::onBackPressed)
    }

    private val list: (ComponentContext) -> ListComponent = {
        ListComponentImpl(it)
    }

    private val details: (ComponentContext, Long?) -> MaterialDetailsComponent = { context, id ->
        MaterialDetailsComponentImpl(context, id) {
            router.pop()
        }
    }

    private val router = router<Configuration, ListFlowComponent.Child>(
        initialConfiguration = Configuration.List,
        key = "ListFlow"
    ) { configuration, componentContext ->
        when (configuration) {
            is Configuration.List -> ListFlowComponent.Child.List(
                list(componentContext)
            )
            is Configuration.Details -> ListFlowComponent.Child.Details(
                details(componentContext, configuration.materialId)
            )
        }
    }

    override val routerState: Value<RouterState<*, ListFlowComponent.Child>> = router.state

    override fun openDetails(id: Long?) {
        router.navigate {
            navigateDetails(it, id)
        }
    }

    private fun navigateDetails(start: List<Configuration>, materialId: Long?): List<Configuration> {
        val newState = start.toMutableList()
        newState.add(Configuration.Details(materialId))
        return newState
    }

    sealed class Configuration : Parcelable {
        @Parcelize
        object List : Configuration()

        @Parcelize
        data class Details(val materialId: Long?) : Configuration()
    }

    override fun dropFlow() {
        router.navigate(::dropFlow)
    }

    private fun dropFlow(start: List<Configuration>): List<Configuration> {
        return listOf(start.toMutableList().first())
    }

    private fun onBackPressed(): Boolean {
        if (router.state.value.backStack.isEmpty()) {
            return false
        }
        router.pop()
        return true
    }
}