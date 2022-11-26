package com.pierre.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import com.pierre.ui.base.BaseFragment
import com.pierre.ui.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint

/**
 * A splash screen displayed to the user while we load the data in the background
 */
@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    private lateinit var binding: FragmentSplashBinding

    override fun initBinding(inflater: LayoutInflater): ViewBinding {
        binding = FragmentSplashBinding.inflate(inflater)
        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}