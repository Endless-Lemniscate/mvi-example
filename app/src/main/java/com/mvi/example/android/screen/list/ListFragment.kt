package com.mvi.example.android.screen.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import com.mvi.example.android.databinding.FragmentListBinding
import com.mvi.example.android.screen.list.view.ListViewImpl
import org.koin.android.ext.android.inject

class ListFragment : Fragment() {
    private val router: Router by inject()
    private val controller: ListController = ListController(router)

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller.onViewCreated(ListViewImpl(binding))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        controller.onViewDestroyed()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        controller.onStart()
    }

    override fun onStop() {
        super.onStop()
        controller.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        controller.onDestroy()
    }
}