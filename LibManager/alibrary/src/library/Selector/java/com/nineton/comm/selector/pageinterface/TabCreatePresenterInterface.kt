package com.nineton.comm.selector.pageinterface

import android.graphics.Color
import androidx.fragment.app.FragmentManager

/**
 * @author HUAN
 * @date
 */
interface TabCreatePresenterInterface {
    /**
     *@param margin tab 之间的margin  单位时dp 不用转换
     *@param yOffset 下标的偏移量
     *@param isNeedIndicator 是否需要下标
     */
    fun createTab(model: TabCreateModelInterface
                  ,view: TabCreateViewInterface
                  , margin :Int = 0
                  , normalColor: Int = Color.parseColor("#8C8B98")
                  , selectedColor: Int = Color.parseColor("#948FF6")
                  , textSize: Float = 0f
                  , yOffset: Int = 0
                  ,isNeedIndicator:Boolean = true){
        val titles = model.createTitle()
        val navigatorAdapter = TabCreator.createNavigatorAdapter(
            titles,
            view,
            normalColor,
            selectedColor,
            textSize,
            yOffset,
            isNeedIndicator
        )
        view.onNavigatorAdapter(navigatorAdapter,margin)
    }

    fun initViewPager(fragmentManager: FragmentManager
                      , model: TabCreateModelInterface, view: TabCreateViewInterface
    ){
        val adapter = model.initViewPager(fragmentManager)
        view.onCreateFragmentAdapter(adapter)
    }

    fun initStateViewPager(fragmentManager: FragmentManager
                           , model: TabCreateModelInterface, view: TabCreateViewInterface
    ){
        val adapter = model.initViewStatePager(fragmentManager)
        view.onCreateFragmentAdapter(adapter)
    }

    /**
     * tab 本地决定时 依次调用
     * tab 从服务器拉取时  用crateTab()后
     * 再用 {TabCreateViewInterface.onTabCreate()}
     * 回调里 initViewPager(fragmentManager: FragmentManager)
     */
    fun createTab()
    fun initViewPager(fragmentManager: FragmentManager)
}