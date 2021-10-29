package com.dresses.library.widget

import android.content.Context
import android.content.res.AssetManager
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import com.dresses.library.R
import com.dresses.library.widget.TypeFaceControlTextView.Companion.FONT_BOLD_PATH
import com.dresses.library.widget.TypeFaceControlTextView.Companion.FONT_NORMAL_PATH


class TypeFaceControlRadioButton(context: Context, attributeSet: AttributeSet) :
    androidx.appcompat.widget.AppCompatRadioButton(
        context,
        attributeSet
    ) {



    init {
        val ta: TypedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.TypeFaceControlTextView)
        val isBold = ta.getBoolean(R.styleable.TypeFaceControlTextView_is_bold, false)
        ta.recycle()
        includeFontPadding = false
    }
}