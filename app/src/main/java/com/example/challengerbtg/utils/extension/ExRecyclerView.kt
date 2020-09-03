package com.example.challengerbtg.utils.extension

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun <T> RecyclerView.initTwoGridLayout(adapter: T) {
    val linearLayoutManager = GridLayoutManager(this.context, 2)
    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
    this.layoutManager = linearLayoutManager
    this.setHasFixedSize(true)
    this.adapter = adapter as RecyclerView.Adapter<*>
}

fun <T> RecyclerView.initGridLayout(adapter: T) {
    val linearLayoutManager = GridLayoutManager(this.context, 1)
    linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
    this.layoutManager = linearLayoutManager
    this.setHasFixedSize(true)
    this.adapter = adapter as RecyclerView.Adapter<*>
}