package com.mvi.example.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.defaultComponentContext
import com.mvi.example.android.screen.root.AppRootContent
import com.mvi.example.android.screen.root.component.AppRootComponentImpl
import com.mvi.example.android.ui.ComposeAppTheme

@ExperimentalDecomposeApi
@ExperimentalFoundationApi
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window, false)

        setContent {
            val root = AppRootComponentImpl(defaultComponentContext())

            ComposeAppTheme {
                Surface(color = MaterialTheme.colors.background) {
                    AppRootContent(root)
                }
            }
        }
    }
}