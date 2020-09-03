package com.example.challengebtg.utils

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("fadeInRight")
fun fadeInRight(view: View, position: Int) {
    val animation = ViewAnimation()
    animation.fadeInRight(view, duration = 600)
}

@BindingAdapter("fadeIn")
fun fadeIn(view: View, position: Int) {
    val animation = ViewAnimation()
    animation.fadeIn(view, duration = 600)
}