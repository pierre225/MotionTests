package com.pierre.ui.utils

import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.pierre.ui.R

object AnimationUtils {

    fun blink(view: View): Animation =
        AnimationUtils.loadAnimation(view.context, R.anim.blink_anim).apply {
            view.animation = this
            setAnimationListener(object : Animation.AnimationListener {
                override fun onAnimationStart(animation: Animation?) { view.visibility = View.VISIBLE }
                override fun onAnimationEnd(animation: Animation?) { view.visibility = View.GONE }
                override fun onAnimationRepeat(animation: Animation?) {}
            })
        }

}