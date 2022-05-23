package com.mvi.example.android.screen.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.jetpack.subscribeAsState
import com.mvi.example.android.domain.models.Material
import com.mvi.example.android.screen.list.component.ListComponent
import com.mvi.example.android.ui.components.NeomorphButton
import com.mvi.example.android.ui.primary

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ListUi(component: ListComponent, openItem: (Long) -> Unit) {
    val model by component.model.subscribeAsState()
    val listState = rememberLazyListState()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {
            List(model.items, component, Modifier.padding(it), listState, openItem)
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { component.onAddItemClicked() }) {
                Icon(Icons.Filled.Add, "Add new material")
            }
        }
    )
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun List(
    items: List<Material>,
    component: ListComponent,
    modifier: Modifier,
    state: LazyListState,
    openItem: (Long) -> Unit
) {
    LazyColumn(
        state = state,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding(),
            bottom = 16.dp
        )
    ) {
        items(items = items) {
            MaterialListItem(material = it, component, Modifier.animateItemPlacement(), openItem)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun MaterialListItem(
    material: Material,
    component: ListComponent,
    modifier: Modifier,
    openItem: (Long) -> Unit
) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = primary,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        onClick = {
            material.id?.let {
                openItem(it)
            }
        }
    ) {
        Row {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = material.title, style = typography.h6)
                Text(text = material.text, style = typography.caption)
                NeomorphButton(
                    text = "Удалить",
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        material.id?.let {
                            component.onItemDeleteClicked(it)
                        }
                    }
                )
            }
        }
    }
}

