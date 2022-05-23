package com.mvi.example.android.screen.root

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.childAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.fade
import com.mvi.example.android.screen.bottom_navigation.BottomNavigationUi
import com.mvi.example.android.screen.root.component.AppRootComponent
import com.mvi.example.android.screen.settings.SettingsUi

@ExperimentalDecomposeApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun AppRootContent(roadsRootComponent: AppRootComponent) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Children(
            routerState = roadsRootComponent.routerState,
            animation = childAnimation(fade())
        ) {
            when (val child = it.instance) {
                is AppRootComponent.Child.Main -> BottomNavigationUi(
                    component = child.component
                )
                is AppRootComponent.Child.Settings -> SettingsUi()
            }
        }
    }
}