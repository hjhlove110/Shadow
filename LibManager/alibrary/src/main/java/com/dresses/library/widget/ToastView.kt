package com.dresses.library.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import com.dresses.library.R
import kotlinx.android.synthetic.main.layout_toast.view.*

class ToastView: FrameLayout {
    constructor(context: Context) : super(context)
    constructor(context: Context,attributeSet: AttributeSet) : super(context,attributeSet)
    constructor(context: Context,attributeSet: AttributeSet, defStyleAttr: Int) : super(context,attributeSet,defStyleAttr)

    init {
        addView(View.inflate(context, R.layout.layout_toast, null))
    }


    fun setText(text: CharSequence) {
        toastText!!.text = text
    }

    fun setTextSize(size: Int) {
        toastText!!.textSize = size.toFloat()
    }

    fun setTextColor(color: Int) {
        toastText!!.setTextColor(color)
    }
}