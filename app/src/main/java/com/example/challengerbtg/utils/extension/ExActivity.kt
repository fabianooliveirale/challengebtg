package com.example.challengerbtg.utils.extension

import android.app.Activity
import android.widget.Toast

fun Activity.toast(value: String) {
    Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
}