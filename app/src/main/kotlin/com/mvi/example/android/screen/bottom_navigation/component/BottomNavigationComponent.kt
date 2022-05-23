package com.mvi.example.android.screen.bottom_navigation.component

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.mvi.example.android.screen.bottom_navigation.BottomNavigationFlow
import com.mvi.example.android.screen.list.flow.ListFlowComponent
import com.mvi.example.android.screen.settings.component.SettingsComponent

interface BottomNavigationComponent {
    val activeChildIndex: Value<Int>

    val routerState: Value<RouterState<*, Child>>

    sealed class Child(val index: Int) : BottomNavigationFlow {
        data class ListFlow(val component: ListFlowComponent) : Child(index = 0) {
            override fun dropFlow() {
                component.dropFlow()
            }
        }

        data class Settings(val component: SettingsComponent) : Child(index = 1) {
            override fun dropFlow() = Unit
        }
    }

    fun onSelectTab(index: Int)
}