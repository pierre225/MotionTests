package com.pierre.ui.square

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.pierre.ui.R
import com.pierre.ui.base.BaseFragment
import com.pierre.ui.databinding.FragmentMotionBinding
import com.pierre.ui.report.ReportFragment
import com.pierre.ui.utils.AnimationUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SquareFragment : BaseFragment() {

    private val motionViewModel: SquareViewModel by viewModels()

    private lateinit var binding: FragmentMotionBinding

    // The square can be moved in the whole screen (even on top of buttons)
    private val MIN_X by lazy { binding.root.x }
    private val MIN_Y by lazy { binding.root.y }
    private val MAX_X by lazy { binding.root.width.toFloat() - binding.square.width }
    private val MAX_Y by lazy { binding.root.height.toFloat() - binding.square.height }

    private var dX = 0f
    private var dY = 0f

    override fun initBinding(inflater: LayoutInflater): ViewBinding {
        binding = FragmentMotionBinding.inflate(inflater)
        return binding
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configureSquareTouch()
        configureSquareSize()

        binding.apply {
            centerSquareButton.setOnClickListener { centerSquare() }
            displayDataButton.setOnClickListener { displayData() }
        }
    }

    /**
     * Configure the square size if there is a value in the bundle
     */
    private fun configureSquareSize() {
        arguments?.getInt(BUNDLE_SQUARE_SIZE_KEY)?.also { size ->
            if (size > 0) { // The bundle returns à if no mapping exist
                binding.square.updateLayoutParams {
                    width = size
                    height = size
                }
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun configureSquareTouch() {
        binding.square.setOnTouchListener { view, event ->
            when (event.actionMasked) {
                MotionEvent.ACTION_DOWN -> onActionDown(event)
                MotionEvent.ACTION_MOVE -> onActionMove(event)
                MotionEvent.ACTION_UP -> onActionUp()
                else -> false
            }
        }
    }

    // todo comment
    private fun onActionDown(event: MotionEvent): Boolean {
        dX = binding.square.x - event.rawX
        dY = binding.square.y - event.rawY

        motionViewModel.startCapture()
        return true
    }

    /**
     * Moves the squares to the new position if it is within the screen
     */
    private fun onActionMove(event: MotionEvent): Boolean {
        val newX = event.rawX + dX
        val newY = event.rawY + dY
        if (newX in MIN_X..MAX_X) binding.square.x = newX else exceededBounds()
        if (newY in MIN_Y..MAX_Y) binding.square.y = newY else exceededBounds()

        motionViewModel.addPosition(newX, newY, event.rawX, event.rawY)
        return true
    }

    private fun exceededBounds() {
       if (!motionViewModel.hasExceededBounds()) {
            binding.exceededBoundOutline.startAnimation(AnimationUtils.blink(binding.exceededBoundOutline))
            motionViewModel.onExceededBounds()
        }
    }

    private fun onActionUp(): Boolean {
        motionViewModel.stopCapture()
        return true
    }

    override fun onPause() {
        motionViewModel.stopCapture()
        super.onPause()
    }

    override fun onDestroy() {
        motionViewModel.stopCapture()
        super.onDestroy()
    }

    private fun centerSquare() {
        val centerX = (binding.root.width / 2f) - binding.square.width / 2f
        val centerY = (binding.root.height / 2f) - binding.square.height / 2f

        binding.square
            .animate()
            .x(centerX)
            .y(centerY)
            .y(centerY)
            .setDuration(CENTER_SQUARE_ANIMATION_DURATION)
            .start()
    }

    private fun displayData() {
        val request = NavDeepLinkRequest.Builder
            .fromUri(ReportFragment.DEEPLINK_URI.toUri())
            .build()
        findNavController().navigate(request)
    }

    companion object {
        const val BUNDLE_SQUARE_SIZE_KEY = "BUNDLE_SQUARE_SIZE"
        private const val CENTER_SQUARE_ANIMATION_DURATION = 300L
    }
}