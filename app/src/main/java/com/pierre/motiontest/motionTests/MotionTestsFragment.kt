package com.pierre.motiontest.motionTests

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewbinding.ViewBinding
import com.pierre.motiontest.R
import com.pierre.motiontest.base.BaseFragment
import com.pierre.motiontest.databinding.FragmentMotionTestsBinding
import com.pierre.ui.motion.SquareFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * A menu screen to choose what test to launch
 */
@AndroidEntryPoint
class MotionTestsFragment : BaseFragment() {

    private lateinit var binding: FragmentMotionTestsBinding

    override fun initBinding(inflater: LayoutInflater): ViewBinding {
        binding = FragmentMotionTestsBinding.inflate(inflater)
        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            movingSquare.setOnClickListener { onClickSquareTest() }
            test2.setOnClickListener { Toast.makeText(context, "Not implemented", Toast.LENGTH_LONG).show() }
            test3.setOnClickListener { Toast.makeText(context, "Not implemented", Toast.LENGTH_LONG).show() }
        }
    }

    /**
     * Launch the square motion test configuring the square size to be 70 px
     */
    private fun onClickSquareTest() {
        val bundle = bundleOf(SquareFragment.BUNDLE_SQUARE_SIZE_KEY to SQUARE_SIZE_CONFIGURATION)
        findNavController().navigate(R.id.motionTestsToSquare, bundle)
    }

    companion object {
        private const val SQUARE_SIZE_CONFIGURATION = 70
    }
}