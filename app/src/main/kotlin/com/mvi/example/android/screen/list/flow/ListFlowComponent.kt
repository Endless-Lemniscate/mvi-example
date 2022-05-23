package com.mvi.example.android.screen.list.flow

import com.arkivanov.decompose.router.RouterState
import com.arkivanov.decompose.value.Value
import com.mvi.example.android.screen.bottom_navigation.BottomNavigationFlow
import com.mvi.example.android.screen.details.component.MaterialDetailsComponent
import com.mvi.example.android.screen.list.component.ListComponent

interface ListFlowComponent : BottomNavigationFlow {
    val routerState: Value<RouterState<*, Child>>

    sealed class Child {
        data class List(val component: ListComponent) : Child()
        data class Details(val component: MaterialDetailsComponent) : Child()
    }

    fun openDetails(id: Long?)

    override fun dropFlow()
}