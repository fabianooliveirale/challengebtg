package com.example.challengebtg.utils

import android.R.attr.animationDuration
import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Handler
import android.view.View
import android.view.animation.PathInterpolator
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import com.example.challengebtg.utils.extension.toDP

class ViewAnimation {
    private var baseDurationAnime: Long = 990
    private val easeOut = PathInterpolator(.33f, 1f, .7f, 1f)
    private var handler: Handler? = null
    var inAnimation = false

    init {
        handler = Handler()
    }

    fun slideInRight(view: View, delay: Long = 0, duration: Long = baseDurationAnime) {
        view.isVisible = false
        handler?.postDelayed({
            YoYo.with(Techniques.SlideInRight)
                .duration(duration)
                .interpolate(easeOut)
                .onStart {
                    inAnimation = true
                    view.isGone = false
                }.onEnd {
                    inAnimation = false
                }.playOn(view)
        }, delay)
    }

    fun slideInLeft(view: View, delay: Long = 0, duration: Long = baseDurationAnime) {
        handler?.postDelayed({
            YoYo.with(Techniques.SlideInLeft)
                .duration(duration)
                .interpolate(easeOut)
                .onStart {
                    inAnimation = true
                    view.isGone = false
                }.onEnd {
                    inAnimation = false
                }.playOn(view)
        }, delay)
    }

    fun fadeInLeft(
        view: View,
        delay: Long = 0,
        duration: Long = baseDurationAnime
    ) {
        handler?.postDelayed({
            YoYo.with(Techniques.FadeInLeft)
                .duration(duration)
                .interpolate(easeOut)
                .onStart {
                    inAnimation = true
                    view.isGone = false
                }.onEnd {
                    inAnimation = false
                }.playOn(view)
        }, delay)
    }

    fun fadeInRight(
        view: View,
        delay: Long = 0,
        duration: Long = baseDurationAnime
    ) {
        handler?.postDelayed({
            YoYo.with(Techniques.FadeInRight)
                .duration(duration)
                .interpolate(easeOut)
                .onStart {
                    inAnimation = true
                    view.isGone = false
                }.onEnd {
                    inAnimation = false
                }.playOn(view)
        }, delay)
    }

    fun slideInUp(
        view: View,
        delay: Long = 0,
        duration: Long = baseDurationAnime
    ) {
        view.visibility = View.INVISIBLE
        handler?.postDelayed({
            YoYo.with(Techniques.SlideInUp)
                .duration(duration)
                .onStart { view.visibility = View.VISIBLE }
                .interpolate(easeOut)
                .onStart {
                    inAnimation = true
                }
                .onEnd {
                    inAnimation = false
                    view.isGone = true
                }.playOn(view)
        }, delay)
    }

    fun slideInDown(
        view: View,
        delay: Long = 0,
        duration: Long = baseDurationAnime
    ) {
        view.visibility = View.INVISIBLE
        handler?.postDelayed({
            YoYo.with(Techniques.SlideInDown)
                .duration(duration)
                .onStart { view.visibility = View.VISIBLE }
                .interpolate(easeOut)
                .onStart {
                    inAnimation = true
                    view.isGone = false
                }.onEnd {
                    inAnimation = false
                }.playOn(view)
        }, delay)
    }

    fun dropOut(
        view: View,
        delay: Long = 0,
        duration: Long = baseDurationAnime
    ) {
        view.isVisible = false
        handler?.postDelayed({
            YoYo.with(Techniques.DropOut)
                .duration(duration)
                .interpolate(easeOut)
                .onStart {
                    inAnimation = true
                    view.isGone = false
                }.onEnd {
                    inAnimation = false
                }.playOn(view)
        }, delay)
    }

    fun fadeOutUp(
        view: View,
        delay: Long = 0,
        duration: Long = baseDurationAnime
    ) {
        handler?.postDelayed({
            YoYo.with(Techniques.FadeOutUp)
                .duration(duration)
                .interpolate(easeOut)
                .onStart {
                    inAnimation = true
                }
                .onEnd {
                    inAnimation = false
                    view.isGone = true
                }.playOn(view)
        }, delay)
    }

    fun fadeInDown(
        view: View,
        delay: Long = 0,
        duration: Long = baseDurationAnime
    ) {
        handler?.postDelayed({
            YoYo.with(Techniques.FadeInDown)
                .duration(duration)
                .interpolate(easeOut)
                .onStart {
                    inAnimation = true
                    view.isGone = false
                }.onEnd {
                    inAnimation = false
                }.playOn(view)
        }, delay)
    }

    fun fadeInUp(
        view: View,
        delay: Long = 0,
        duration: Long = baseDurationAnime
    ) {
        handler?.postDelayed({
            YoYo.with(Techniques.FadeInUp)
                .duration(duration)
                .interpolate(easeOut)
                .onStart {
                    inAnimation = true
                    view.isGone = false
                }.onEnd {
                    inAnimation = false
                }.playOn(view)
        }, delay)
    }

    fun increaseViewSize(
        view: View,
        value: Int,
        delay: Long = 0,
        duration: Long = baseDurationAnime,
        closure: () -> (Unit) = {}
    ) {

        val dpHeight = value.toDP()

        inAnimation = true
        val valueAnimator =
            ValueAnimator.ofInt(view.measuredHeight, view.measuredHeight + dpHeight.toInt())
        handler?.postDelayed({
            valueAnimator.duration = duration
            valueAnimator.addUpdateListener {
                val animatedValue = valueAnimator.animatedValue as Int
                val layoutParams = view.layoutParams
                layoutParams.height = animatedValue
                view.layoutParams = layoutParams
            }

            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    inAnimation = false
                    closure()
                }
            })

            valueAnimator.start()
        }, delay)
    }

    fun decreaseViewSize(
        view: View,
        value: Int,
        delay: Long = 0,
        duration: Long = baseDurationAnime,
        closure: () -> (Unit) = {}
    ) {

        val dpHeight = value.toDP()

        inAnimation = true
        val valueAnimator =
            ValueAnimator.ofInt(view.measuredHeight, view.measuredHeight - dpHeight.toInt())
        handler?.postDelayed({
            valueAnimator.duration = duration
            valueAnimator.addUpdateListener {
                val animatedValue = valueAnimator.animatedValue as Int
                val layoutParams = view.layoutParams
                layoutParams.height = animatedValue
                view.layoutParams = layoutParams
            }

            valueAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    inAnimation = false
                    closure()
                }
            })

            valueAnimator.start()
        }, delay)
    }


    fun fadeIn(
        view: View,
        delay: Long = 0,
        duration: Long = baseDurationAnime
    ) {
        handler?.postDelayed({
            YoYo.with(Techniques.FadeIn)
                .duration(duration)
                .interpolate(easeOut)
                .onStart {
                    inAnimation = true
                    view.isGone = false
                }.onEnd {
                    inAnimation = false
                }.playOn(view)
        }, delay)
    }

    fun fadeOut(
        view: View,
        delay: Long = 0,
        duration: Long = baseDurationAnime
    ) {
        handler?.postDelayed({
            YoYo.with(Techniques.FadeOut)
                .duration(duration)
                .interpolate(easeOut)
                .onStart {
                    inAnimation = true
                }
                .onEnd {
                    inAnimation = false
                    view.isGone = true
                }.playOn(view)
        }, delay)
    }

    private fun viewGoneAnimator(view: View) {
        view.animate()
            .alpha(0f)
            .setDuration(animationDuration.toLong())
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.isGone = true
                }
            })
    }

    fun viewVisibleAnimator(view: View, delay: Long = 0) {
        handler?.postDelayed({
            view.animate()
                .alpha(1f)
                .setDuration(animationDuration.toLong())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        view.isVisible = true
                    }
                })
        }, delay)
    }
}