package com.pierre.ui.square

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import androidx.core.net.toUri
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDeepLinkRequest
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.pierre.ui.base.BaseFragment
import com.pierre.ui.databinding.FragmentSquareBinding
import com.pierre.ui.report.ReportFragment
import com.pierre.ui.utils.AnimationUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SquareFragment : BaseFragment() {

    private val squareViewModel: SquareViewModel by viewModels()

    private lateinit var binding: FragmentSquareBinding

    // The square can be moved in the whole screen (even on top of buttons)
    private val MIN_X by lazy { binding.root.x }
    private val MIN_Y by lazy { binding.root.y }
    private val MAX_X by lazy { binding.root.width.toFloat() - binding.square.width }
    private val MAX_Y by lazy { binding.root.height.toFloat() - binding.square.height }

    private var dX = 0f
    private var dY = 0f

    override fun initBinding(inflater: LayoutInflater): ViewBinding {
        binding = FragmentSquareBinding.inflate(inflater)
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

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    squareViewModel.lastSavedPosition.collectLatest {
                        animateSquare(it.squareX, it.squareY)
                    }
                }
            }
        }

        squareViewModel.getLastPosition()
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

    private fun onActionDown(event: MotionEvent): Boolean {
        dX = binding.square.x - event.rawX
        dY = binding.square.y - event.rawY

        squareViewModel.startCapture()
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

        squareViewModel.addPosition(binding.square.x, binding.square.y, event.rawX, event.rawY)
        return true
    }

    private fun exceededBounds() {
       if (!squareViewModel.hasExceededBounds()) {
            binding.exceededBoundOutline.startAnimation(AnimationUtils.blink(binding.exceededBoundOutline))
            squareViewModel.onExceededBounds()
        }
    }

    private fun onActionUp(): Boolean {
        squareViewModel.stopCapture()
        return true
    }

    override fun onPause() {
        squareViewModel.stopCapture()
        super.onPause()
    }

    override fun onDestroy() {
        squareViewModel.stopCapture()
        super.onDestroy()
    }

    private fun centerSquare() {
        val centerX = (binding.root.width / 2f) - binding.square.width / 2f
        val centerY = (binding.root.height / 2f) - binding.square.height / 2f
        animateSquare(centerX, centerY)
    }

    private fun animateSquare(x: Float, y: Float) {
        binding.square
            .animate()
            .x(x)
            .y(y)
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