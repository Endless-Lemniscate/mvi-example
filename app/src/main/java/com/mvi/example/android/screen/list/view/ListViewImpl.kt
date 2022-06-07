package com.mvi.example.android.screen.list.view

import androidx.recyclerview.widget.LinearLayoutManager
import com.arkivanov.mvikotlin.core.view.BaseMviView
import com.mvi.example.android.databinding.FragmentListBinding
import com.mvi.example.android.screen.list.utils.ListMaterialsAdapter

class ListViewImpl(binding: FragmentListBinding) : BaseMviView<ListView.Model, ListView.Event>(),
    ListView {

    private val recyclerView = binding.recyclerMaterials
    private var adapterR: ListMaterialsAdapter

    init {
        binding.addMaterialButton.setOnClickListener {
            dispatch(ListView.Event.AddItem)
        }
        adapterR = ListMaterialsAdapter(
            itemsClicked =  { dispatch(ListView.Event.ItemClicked(it)) },
            deleteItemClicked = { dispatch(ListView.Event.DeleteItem(it)) }
        )
        recyclerView.adapter = adapterR
        recyclerView.layoutManager = LinearLayoutManager(binding.root.context)
    }

    override fun render(model: ListView.Model) {
        super.render(model)
        adapterR.submitList(model.items)
    }
}