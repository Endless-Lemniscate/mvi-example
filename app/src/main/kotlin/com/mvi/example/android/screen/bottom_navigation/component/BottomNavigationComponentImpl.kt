package com.mvi.example.android.screen.bottom_navigation.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.activeChild
import com.arkivanov.decompose.router.bringToFront
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.parcelable.Parcelable
import com.mvi.example.android.screen.list.flow.ListFlowComponent
import com.mvi.example.android.screen.list.flow.ListFlowComponentImpl
import com.mvi.example.android.screen.settings.component.SettingsComponent
import com.mvi.example.android.screen.settings.component.SettingsComponentImpl
import kotlinx.parcelize.Parcelize

class BottomNavigationComponentImpl(
    componentContext: ComponentContext
) : BottomNavigationComponent, ComponentContext by componentContext {

    private val listFlow: ListFlowComponent by lazy { ListFlowComponentImpl(componentContext) }
    private val settings: SettingsComponent by lazy { SettingsComponentImpl(componentContext) }

    private val router = router<Configuration, BottomNavigationComponent.Child>(
        initialConfiguration = Configuration.ListFlow,
        key = "BottomNavigation"
    ) { configuration, _ ->
        when (configuration) {
            is Configuration.ListFlow -> BottomNavigationComponent.Child.ListFlow(
                listFlow
            )
            is Configuration.Settings -> BottomNavigationComponent.Child.Settings(
                settings
            )
        }
    }

    override val routerState: Value<RouterState<*, BottomNavigationComponent.Child>> = router.state

    override val activeChildIndex: Value<Int> = routerState.map {
        it.activeChild.instance.index
    }

    override fun onSelectTab(index: Int) {
        if (index == activeChildIndex.value) {
            router.activeChild.instance.dropFlow()
        }
        router.bringToFront(
            when (index) {
                0 -> Configuration.ListFlow
                1 -> Configuration.Settings
                else -> throw IllegalArgumentException("index $index is not defined in app")
            }
        )
    }

    sealed class Configuration : Parcelable {
        @Parcelize
        object ListFlow : Configuration()

        @Parcelize
        object Settings : Configuration()
    }
}