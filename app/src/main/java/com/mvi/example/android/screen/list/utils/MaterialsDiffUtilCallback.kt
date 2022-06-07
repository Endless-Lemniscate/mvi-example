package com.mvi.example.android.screen.list.utils

import androidx.recyclerview.widget.DiffUtil
import com.mvi.example.android.domain.models.Material

class MaterialsDiffUtilCallback : DiffUtil.ItemCallback<Material>() {
    override fun areItemsTheSame(oldItem: Material, newItem: Material): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Material, newItem: Material): Boolean {
        return oldItem == newItem
    }
}