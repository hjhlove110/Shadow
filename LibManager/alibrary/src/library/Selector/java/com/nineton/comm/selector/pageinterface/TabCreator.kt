package com.nineton.comm.selector.pageinterface

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import com.dresses.library.R
import com.dresses.library.utils.dp2px
import com.dresses.library.widget.TypeFaceControlTextView
import com.nineton.comm.selector.BigSmallBoldTranstionPagerTitleView
import com.nineton.comm.selector.TabBean
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator

/**
 * @author HUAN
 * @date
 */
object TabCreator {


    fun createNavigatorAdapter(titles: MutableList<TabBean>
                               , view: TabCreateViewInterface
                               , normalColor: Int = Color.parseColor("#8C8B98")
                               , selectedColor: Int = Color.parseColor("#948FF6")
                               , textSize: Float = 0f
                               , yOffset: Int = 0,isNeedIndicator:Boolean = true
    ): CommonNavigatorAdapter {

        val adapter = object : CommonNavigatorAdapter() {
            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val titleView = BigSmallBoldTranstionPagerTitleView(context)
                titleView.text = titles[index].getTabTitle()
                titleView.normalColor = normalColor
                titleView.selectedColor = selectedColor

                if (textSize != 0f) {
                    titleView.setTextSize(textSize.toInt())
                    titleView.setBigTextSize(textSize.toInt() )
                }else{
                    titleView.setTextSize(13)
                    titleView.setBigTextSize(13)
                }
                titleView.setOnClickListener {
                    view.onPagerIndex(index)
                }
                return titleView
            }

            override fun getCount(): Int {
                return titles.size
            }

            override fun getIndicator(context: Context?): IPagerIndicator? {
                if (!isNeedIndicator) return null
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY
                indicator.lineHeight = context?.resources?.getDimension(R.dimen.qb_px_2)!!
                indicator.lineWidth = context?.resources?.getDimension(R.dimen.qb_px_8)!!
                indicator.roundRadius = context?.resources?.getDimension(R.dimen.qb_px_2)!!
                if (yOffset!= 0){
                    indicator.yOffset = dp2px(yOffset)
                }
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(2.0f)
                indicator.setColors(selectedColor)
                return indicator
            }
        }
        return adapter
    }
}