package com.mvi.example.android.screen.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.Router
import com.mvi.example.android.databinding.FragmentDetailsBinding
import com.mvi.example.android.screen.details.view.DetailsViewImpl
import org.koin.android.ext.android.inject

class DetailsFragment : Fragment() {
    private val router: Router by inject()
    private lateinit var controller: DetailsController
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        controller = DetailsController(
            requireArguments().getLong("MATERIAL_ID"),
            router
        ) {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        controller.onViewCreated(DetailsViewImpl(binding))
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