package com.pierre.motiontest.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.pierre.motiontest.databinding.FragmentBaseBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A class that contains what is common to our Fragments so we don't need to implement it in each.
 * This BaseFragment can show messages, errors.
 *
 * Additionally, it could handle Rx disposables, events, logging...
 *
 */
@AndroidEntryPoint
abstract class BaseFragment: Fragment() {

    private lateinit var baseBinding: FragmentBaseBinding

    // Child view binding
    abstract fun initBinding(inflater: LayoutInflater): ViewBinding

    // BaseFragment binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val childView = initBinding(inflater).root

        baseBinding = FragmentBaseBinding.inflate(inflater, container, false)
        baseBinding.baseContainer.addView(childView)

        return baseBinding.root
    }

    /**
     * Shows or hides a loader at the bottom of the screen
     */
    protected fun displayLoading(display: Boolean) {
        baseBinding.baseLoader.visibility =
            if (display) View.VISIBLE else View.GONE
    }

}
