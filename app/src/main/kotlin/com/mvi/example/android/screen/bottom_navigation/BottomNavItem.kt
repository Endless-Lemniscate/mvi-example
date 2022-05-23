package com.mvi.example.android.screen.bottom_navigation

import com.mvi.example.android.R

sealed class BottomNavItem(
    val index: Int,
    val labelId: Int,
    val icon: Int,
) {
    object List : BottomNavItem(
        0,
        R.string.menu_news,
        R.drawable.ic_list_menu
    )

    object Settings : BottomNavItem(
        1,
        R.string.menu_settings,
        R.drawable.ic_settings_menu
    )
}