package com.nineton.comm.selector.pageinterface

import android.graphics.drawable.ColorDrawable
import android.widget.LinearLayout
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.dresses.library.AppLifecyclesImpl
import com.nineton.comm.selector.TabBean
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.UIUtil
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter


/**
 * @author HUAN
 * @date
 */
interface TabCreateViewInterface : ViewPager.OnPageChangeListener {

    /**
     * 有些tab 需要请求服务器延时操作  等待 tab 创建好了才 布局fragment
     */
    fun onTabCreate(tabs:MutableList<TabBean>) {}

    fun getPager(): ViewPager?
    fun getMagicIndicator(): MagicIndicator?

    fun onPagerIndex(index: Int) {
        getPager()?.currentItem = index
    }

    fun isAdjust(): Boolean

    fun onNavigatorAdapter(navigatorAdapter: CommonNavigatorAdapter,margin:Int = 0) {
        if (getMagicIndicator() == null) return
        val commonNavigator = MarginNavigator(getMagicIndicator()?.context!!,margin)
        commonNavigator.adapter = navigatorAdapter
        commonNavigator.isAdjustMode = isAdjust()
        getMagicIndicator()?.navigator = commonNavigator
        val titleContainer =
            commonNavigator.titleContainer // must after setNavigator

        titleContainer.showDividers = LinearLayout.SHOW_DIVIDER_MIDDLE
        titleContainer.dividerDrawable = object : ColorDrawable() {
            override fun getIntrinsicWidth(): Int {
                return UIUtil.dip2px(AppLifecyclesImpl.appContext, 10.0)
            }
        }
    }

    fun onCreateFragmentAdapter(adapter: PagerAdapter) {
        getPager()?.adapter = adapter
        getPager()?.addOnPageChangeListener(this)
    }

    override fun onPageScrollStateChanged(state: Int) {
        getMagicIndicator()?.onPageScrollStateChanged(state)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        getMagicIndicator()?.onPageScrolled(position, positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {
        getMagicIndicator()?.onPageSelected(position)
    }

}