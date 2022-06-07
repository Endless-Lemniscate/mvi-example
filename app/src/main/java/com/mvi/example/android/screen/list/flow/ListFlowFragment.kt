package com.mvi.example.android.screen.list.flow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mvi.example.android.IOnBackPressed
import com.mvi.example.android.R
import com.mvi.example.android.Screens
import com.mvi.example.android.databinding.FragmentFlowBinding
import org.koin.android.ext.android.inject

class ListFlowFragment : Fragment(), IOnBackPressed {

    private val navigatorHolder: NavigatorHolder by inject()
    private val router: Router by inject()

    private val navigator by lazy {
        object : AppNavigator(requireActivity(), R.id.containerList, childFragmentManager) {
            override fun activityBack() {
                router.navigateTo(Screens.list())
            }
        }
    }

    private var _binding: FragmentFlowBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFlowBinding.inflate(inflater)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        router.newRootScreen(Screens.list())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        router.exit()
    }

}