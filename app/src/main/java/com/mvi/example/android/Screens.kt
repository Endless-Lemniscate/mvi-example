package com.mvi.example.android

import androidx.core.os.bundleOf
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.mvi.example.android.screen.details.DetailsFragment
import com.mvi.example.android.screen.list.ListFragment

object Screens {
    fun list() = FragmentScreen { ListFragment() }

    fun details(id: Long) = FragmentScreen {
        DetailsFragment().apply {
            arguments = bundleOf("MATERIAL_ID" to id)
        }
    }
}