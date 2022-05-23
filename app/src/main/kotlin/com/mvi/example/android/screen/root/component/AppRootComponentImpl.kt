package com.mvi.example.android.screen.root.component

import android.os.Parcelable
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.router.push
import com.arkivanov.decompose.router.router
import com.arkivanov.decompose.value.Value
import com.mvi.example.android.screen.bottom_navigation.component.BottomNavigationComponent
import com.mvi.example.android.screen.bottom_navigation.component.BottomNavigationComponentImpl
import com.mvi.example.android.screen.settings.component.SettingsComponent
import com.mvi.example.android.screen.settings.component.SettingsComponentImpl
import kotlinx.parcelize.Parcelize

class AppRootComponentImpl(componentContext: ComponentContext) : AppRootComponent,
    ComponentContext by componentContext {

    private val main: (ComponentContext) -> BottomNavigationComponent = {
        BottomNavigationComponentImpl(it)
    }

    private val settings: (ComponentContext) -> SettingsComponent = {
        SettingsComponentImpl(it)
    }

    private val router = router<Configuration, AppRootComponent.Child>(
        initialConfiguration = Configuration.Main,
        handleBackButton = true,
        key = "Root",
        childFactory = { configuration, componentContext ->
            when (configuration) {
                is Configuration.Main -> AppRootComponent.Child.Main(
                    main(componentContext)
                )
                is Configuration.Settings -> AppRootComponent.Child.Settings(
                    settings(componentContext)
                )
            }
        }
    )

    override val routerState: Value<RouterState<*, AppRootComponent.Child>> = router.state

    override fun openSettings() {
        router.push(Configuration.Settings)
    }

    private sealed class Configuration : Parcelable {
        @Parcelize
        object Main : Configuration()

        @Parcelize
        object Settings : Configuration()
    }
}