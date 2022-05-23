package com.mvi.example.android.screen.list.flow

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetpack.Children
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.childAnimation
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.fade
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.plus
import com.arkivanov.decompose.extensions.compose.jetpack.animation.child.scale
import com.mvi.example.android.screen.details.MaterialsDetailsUi
import com.mvi.example.android.screen.list.ListUi

@ExperimentalDecomposeApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun FlowUi(component: ListFlowComponent) {
    Children(
        modifier = Modifier.fillMaxSize(),
        routerState = component.routerState,
        animation = childAnimation(scale() + fade())
    ) { created ->
        when (val child = created.instance) {
            is ListFlowComponent.Child.List -> ListUi(
                component = child.component,
                openItem = { id ->
                    component.openDetails(id)
                }
            )
            is ListFlowComponent.Child.Details -> MaterialsDetailsUi(child.component)
        }
    }
}