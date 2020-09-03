package com.example.challengebtg.utils.extension

import android.util.TypedValue
import com.example.challengebtg.MyApplication

fun Int.toDP(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        MyApplication.context.resources.displayMetrics
    )
}