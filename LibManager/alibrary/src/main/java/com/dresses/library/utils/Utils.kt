package com.dresses.library.utils

import android.app.Activity
import android.content.Context
import android.os.Build

object Utils {
    fun isNavigationBarExist(
        activity: Activity?,
        onNavigationStateListener: OnNavigationStateListener?
    ) {
        if (activity == null) {
            return
        }
        val height = getNavigationHeight(activity)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH) {
            activity.window.decorView
                .setOnApplyWindowInsetsListener { v, windowInsets ->
                    var isShowing = false
                    var b = 0
                    if (windowInsets != null) {
                        b = windowInsets.systemWindowInsetBottom
                        isShowing = b == height
                    }
                    if (onNavigationStateListener != null && b <= height) {
                        onNavigationStateListener.onNavigationState(isShowing, b)
                    }
                    windowInsets
                }
        }
    }

    fun getNavigationHeight(activity: Context?): Int {
        if (activity == null) {
            return 0
        }
        val resources = activity.resources
        val resourceId = resources.getIdentifier(
            "navigation_bar_height",
            "dimen", "android"
        )
        var height = 0
        if (resourceId > 0) { //获取NavigationBar的高度
            height = resources.getDimensionPixelSize(resourceId)
        }
        return height
    }

    interface OnNavigationStateListener {
        fun onNavigationState(isOk: Boolean, height: Int)
    }
}