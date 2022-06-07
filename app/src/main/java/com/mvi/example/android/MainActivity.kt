package com.mvi.example.android

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.mvi.example.android.databinding.MainActivityLayoutBinding
import com.mvi.example.android.screen.list.flow.ListFlowFragment
import com.mvi.example.android.screen.settings.SettingsFragment

class MainActivity : FragmentActivity() {
    private lateinit var binding: MainActivityLayoutBinding

    private val currentFragment: IOnBackPressed?
        get() = supportFragmentManager.findFragmentById(R.id.container) as? IOnBackPressed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityLayoutBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        supportFragmentManager.beginTransaction()
            .add(R.id.container, ListFlowFragment(), "LIST")
            .commit()
        binding.bottomNaviagtion.setOnItemSelectedListener {
            val fm = supportFragmentManager
            val transaction = fm.beginTransaction()
            fm.fragments.forEach { fragment ->
                transaction.hide(fragment)
            }
            when (it.itemId) {
                R.id.main -> {
                    val fragment = fm.findFragmentByTag("LIST")?.let { fragment ->
                        transaction.show(fragment)
                    }
                    if (fragment == null) {
                        transaction.add(R.id.container, ListFlowFragment(), "LIST")
                    }
                }
                R.id.settings -> {
                    val fragment = fm.findFragmentByTag("SETTINGS")?.let { fragment ->
                        transaction.show(fragment)
                    }
                    if (fragment == null) {
                        transaction.add(R.id.container, SettingsFragment(), "SETTINGS")
                    }
                }
            }
            transaction.commit()
            true
        }
    }

    override fun onBackPressed() = currentFragment?.onBackPressed() ?: super.onBackPressed()
}