package com.example.challengerbtg.utils.extension


import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.challengerbtg.R
import com.example.challengerbtg.ui.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

fun Fragment.hideToolbar() {
    val toolbar = (activity as MainActivity).supportActionBar
    toolbar?.hide()
    toolbar?.setDisplayHomeAsUpEnabled(false)
}

fun Fragment.showToolbar() {
    val toolbar = (activity as MainActivity).supportActionBar
    toolbar?.show()
    toolbar?.setDisplayHomeAsUpEnabled(false)
}

fun Fragment.setToolbar(title: String, backEnable: Boolean = true) {
    val toolbar = (activity as MainActivity).toolbar
    toolbar?.title = title
    toolbar.navigationIcon = activity?.getDrawable(R.drawable.ic_arrow_back)
    toolbar.setNavigationOnClickListener {
        activity?.onBackPressed()
    }
}

fun Fragment.setSearchToolbar(title: String, onMenu: Toolbar.OnMenuItemClickListener) {
    val toolbar = (activity as MainActivity).toolbar
    toolbar?.title = title
    toolbar.inflateMenu(R.menu.toolbar_menu)
    toolbar.navigationIcon = activity?.getDrawable(R.drawable.ic_arrow_back)
    toolbar.setOnMenuItemClickListener(onMenu)
    toolbar.setNavigationOnClickListener {
        activity?.onBackPressed()
    }
}