package com.mvi.example.android.screen.root.component

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.mvi.example.android.screen.bottom_navigation.component.BottomNavigationComponent
import com.mvi.example.android.screen.settings.component.SettingsComponent

interface AppRootComponent {
    val routerState: Value<RouterState<*, Child>>

    fun openSettings()

    sealed class Child {
        data class Main(val component: BottomNavigationComponent) : Child()
        data class Settings(val component: SettingsComponent) : Child()
    }
}