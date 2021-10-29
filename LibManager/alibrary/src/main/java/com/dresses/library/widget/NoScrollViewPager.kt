package com.dresses.library.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.viewpager.widget.ViewPager


class NoScrollViewPager(context: Context,attributes: AttributeSet):ViewPager(context,attributes) {

    private var noScroll = true

    override fun onTouchEvent(arg0: MotionEvent?): Boolean {
        return if (noScroll) false else super.onTouchEvent(arg0)
    }

    override fun onInterceptTouchEvent(arg0: MotionEvent?): Boolean {
        return if (noScroll) false else super.onInterceptTouchEvent(arg0)
    }
}