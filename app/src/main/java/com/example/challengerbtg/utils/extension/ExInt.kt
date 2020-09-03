package com.example.challengerbtg.utils.extension

import android.util.TypedValue
import com.example.challengerbtg.MyApplication

fun Int.toDP(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        MyApplication.context.resources.displayMetrics
    )
}