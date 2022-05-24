package com.mvi.example.android.screen.list.utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mvi.example.android.databinding.MaterialListItemBinding
import com.mvi.example.android.domain.models.Material

class ListMaterialsAdapter(
    private val itemsClicked: (Long) -> Unit,
    private val deleteItemClicked: (Long) -> Unit
) : ListAdapter<Material, ListMaterialsAdapter.ViewHolder>(
    MaterialsDiffUtilCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MaterialListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int = currentList.size

    inner class ViewHolder(private val binding: MaterialListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(material: Material) {
            binding.title.text = material.title
            binding.text.text = material.text
            binding.buttonDelete.setOnClickListener {
                deleteItemClicked(material.id!!)
            }
            binding.cardView.setOnClickListener {
                itemsClicked(material.id!!)
            }
        }
    }
}