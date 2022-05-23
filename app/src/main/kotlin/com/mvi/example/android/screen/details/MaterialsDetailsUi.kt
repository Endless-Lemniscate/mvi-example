package com.mvi.example.android.screen.details

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.mvi.example.android.screen.details.component.MaterialDetailsComponent
import com.mvi.example.android.screen.details.component.store.Label
import kotlinx.coroutines.flow.Flow

@Composable
internal fun MaterialsDetailsUi(
    component: MaterialDetailsComponent
) {
    val model by component.model.subscribeAsState()
    ProcessLabels(component.labels)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                PaddingValues(
                    top = WindowInsets.statusBars
                        .asPaddingValues()
                        .calculateTopPadding()
                )
            )
    ) {
        Column(
            Modifier
                .fillMaxSize()
                .padding(top = 50.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = model.material.title,
                onValueChange = { component.updateTitle(it) },
                modifier = Modifier.fillMaxWidth()
            )
            TextField(
                value = model.material.text,
                onValueChange = { component.updateText(it) },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = { component.saveMaterial() }) {
                Text(text = "Сохранить")
            }
        }
    }
}

@Composable
private fun ProcessLabels(labelsFlow: Flow<Label>) {
    val context = LocalContext.current
    val labels by labelsFlow.collectAsState(initial = Label.None)

    LaunchedEffect(labels) {
        when (val label = labels) {
            is Label.MaterialSaved -> {
                Toast.makeText(context, label.message, Toast.LENGTH_SHORT).show()
            }
            is Label.None -> Unit
        }
    }
}
