package com.mvi.example.android.screen.bottom_navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.childAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.fade
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.plus
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.scale
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.mvi.example.android.screen.bottom_navigation.component.BottomNavigationComponent
import com.mvi.example.android.screen.list.flow.FlowUi
import com.mvi.example.android.screen.settings.SettingsUi

@ExperimentalDecomposeApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun BottomNavigationUi(component: BottomNavigationComponent) {

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        content = {
            Children(
                modifier = Modifier.padding(it),
                routerState = component.routerState,
                animation = childAnimation(scale() + fade())
            ) { created ->
                when (val child = created.instance) {
                    is BottomNavigationComponent.Child.ListFlow -> FlowUi(
                        component = child.component
                    )
                    is BottomNavigationComponent.Child.Settings -> SettingsUi()
                }
            }
        },
        bottomBar = {
            BottomNavigation(component = component)
        }
    )
}

@Composable
fun BottomNavigation(component: BottomNavigationComponent) {
    val activeIndex by component.activeChildIndex.subscribeAsState()
    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(15.dp, 15.dp, 0.dp, 0.dp))
    ) {
        listOf(
            BottomNavItem.List,
            BottomNavItem.Settings
        ).forEach { item ->
            BottomNavigationItem(
                selected = item.index == activeIndex,
                onClick = { component.onSelectTab(index = item.index) },
                icon = {
                    Icon(
                        painterResource(id = item.icon),
                        contentDescription = stringResource(item.labelId)
                    )
                },
                label = {
                    Text(text = stringResource(id = item.labelId))
                }
            )
        }
    }
}