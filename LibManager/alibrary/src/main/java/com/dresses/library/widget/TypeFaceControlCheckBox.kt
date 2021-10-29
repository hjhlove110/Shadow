package com.dresses.library.widget

import android.content.Context
import android.content.res.AssetManager
import android.content.res.TypedArray
import android.graphics.Typeface
import android.util.AttributeSet
import com.dresses.library.R


class TypeFaceControlCheckBox(context: Context, attributeSet: AttributeSet) :
    androidx.appcompat.widget.AppCompatCheckBox(
        context,
        attributeSet
    ) {

    companion object {
        const val FONT_BOLD_PATH = "font/SourceHanSerifCN-Bold.otf.subset.ttf"
        const val FONT_NORMAL_PATH = "font/SourceHanSerifCN-Regular.otf.subset.ttf"
    }

    init {
        val ta: TypedArray =
            context.obtainStyledAttributes(attributeSet, R.styleable.TypeFaceControlTextView)
        val isBold = ta.getBoolean(R.styleable.TypeFaceControlTextView_is_bold, false)
        ta.recycle()
        includeFontPadding = false
    }
}