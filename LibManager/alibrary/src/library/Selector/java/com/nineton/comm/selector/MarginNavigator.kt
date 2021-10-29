package com.nineton.comm.selector

import android.content.Context
import android.content.res.AssetManager
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatTextView
import com.dresses.library.utils.dp2px
import com.dresses.library.widget.TypeFaceControlTextView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator

/**
 * @author HUAN
 * @date
 */
class MarginNavigator(context: Context,val margin:Int=0) : CommonNavigator(context) {
    override fun initTitlesAndIndicator(){
            var i = 0
            val j = mNavigatorHelper.totalCount
            while (i < j) {
                val v = mAdapter.getTitleView(context, i)
                if (v is View) {
                    val view = v as View
                    var lp: LinearLayout.LayoutParams
                    if (mAdjustMode) {
                        lp = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT)
                        lp.weight = mAdapter.getTitleWeight(context, i)
                    } else {
                        lp = LinearLayout.LayoutParams(dp2px(75).toInt(), LayoutParams.MATCH_PARENT)
                    }
                    mTitleContainer.addView(view, lp)
                }
                i++
            }
        if (mAdapter != null) {
            mIndicator = mAdapter.getIndicator(context)
            if (mIndicator is View) {
                val lp = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
                mIndicatorContainer.addView(mIndicator as View, lp)
            }
        }
    }
}