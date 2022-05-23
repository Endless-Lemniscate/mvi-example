package com.mvi.example.android.data.models

import com.mvi.example.android.domain.models.Material
import entities.MaterialEntity

fun MaterialEntity.toModel(): Material {
    return Material(
        id = this.id,
        title = this.title,
        text = this.text
    )
}