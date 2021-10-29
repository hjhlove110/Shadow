package com.nineton.comm.selector

import android.content.Context
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.graphics.Typeface
import android.view.Gravity
import android.view.ViewGroup
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.dresses.library.AppLifecyclesImpl
import com.dresses.library.R
import com.dresses.library.utils.dp2px
import com.dresses.library.utils.isAllScreenDevice
import net.lucode.hackware.magicindicator.MagicIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView


abstract class BasePagerFragmentSelector(
    activity: FragmentActivity?,
    private val indicatorType: Int = 0,
    private val isAdjustMode: Boolean = true,
    private val margin: Int = 0,
    bgClick: Boolean = true
) :
    BaseSelector(activity, isBgClickable = bgClick), ViewPager.OnPageChangeListener {
    open val fragments = mutableListOf<Fragment>()
    open val tabs = mutableListOf<TabBean>()
    private var pager: ViewPager? = null
    private var mIndicator: MagicIndicator? = null
    protected val whiteColor by lazy {
        Color.parseColor("#ffffff")
    }

    override fun initViewLater() {
        super.initViewLater()
        initData()
        initPager()
    }



    private fun initData() {
        setTabs()
        setFragments()
    }

    abstract fun setTabs()

    abstract fun setFragments()

    open fun getNormalColor(): Int {
        return Color.parseColor("#6A669D")
    }

    open fun getSelectedColor(): Int {
        return Color.parseColor("#6A669D")
    }

    open fun getFillColor(): Int {
        return Color.parseColor("#FF7786")
    }

    open fun getStartColor(): Int {
        return 0
    }

    open fun getEndColor(): Int {
        return 0
    }

    open fun isDestroyItem(): Boolean {
        return true
    }

    open fun getPagerLimit(): Int {
        return 4
    }

    open fun getSelectBgColor(): Int {
        return 0
    }

    private fun initPager() {
        getPager()?.adapter = object : FragmentPagerAdapter(activity!!.supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return fragments[position]
            }

            override fun getCount(): Int {
                return fragments.size
            }

            override fun getItemId(position: Int): Long {
                return (position + this.hashCode()).toLong()
            }
        }
        getPager()?.addOnPageChangeListener(this)

        getMagicIndicator()?.let {
            val adapter =
                if (indicatorType == 0) createNavigatorAdapter() else createBgNavigatorAdapter()
            val commonNavigator = MarginNavigator(getMagicIndicator()?.context!!, margin)

            commonNavigator.isAdjustMode = isAdjustMode
            commonNavigator.adapter = adapter
            getMagicIndicator()?.navigator = commonNavigator
        }

    }


    private fun createNavigatorAdapter(): CommonNavigatorAdapter {
        return object : CommonNavigatorAdapter() {
            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val titleView = ColorTransitionPagerTitleView(context)
                setTabText(index, titleView)
                titleView.normalColor = getNormalColor()
                titleView.selectedColor = getSelectedColor()
                titleView.setSelectBgColor(getSelectBgColor())
                titleView.gravity = Gravity.CENTER
                titleView.setOnClickListener {
                    getPager()?.currentItem = index
                }
                return titleView
            }

            override fun getCount(): Int {
                return tabs.size
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                val indicator = LinePagerIndicator(context)
                indicator.mode = LinePagerIndicator.MODE_EXACTLY;
                indicator.lineHeight = context?.resources?.getDimension(R.dimen.qb_px_3)!!
                indicator.lineWidth = context?.resources?.getDimension(R.dimen.qb_px_30)!!
//                indicator.yOffset = context?.resources?.getDimension(R.dimen.qb_px_12)!!
                indicator.roundRadius = context?.resources?.getDimension(R.dimen.qb_px_2)!!
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(2.0f)
                indicator.setColors(getSelectedColor())

                return indicator
            }
        }

    }

    private fun createBgNavigatorAdapter(): CommonNavigatorAdapter {
        return object : CommonNavigatorAdapter() {
            override fun getTitleView(context: Context?, index: Int): IPagerTitleView {
                val titleView = ColorTransitionPagerTitleView(context)
                setTabText(index, titleView)
                titleView.normalColor = getNormalColor()
                titleView.typeface = Typeface.DEFAULT_BOLD
                titleView.selectedColor = getSelectedColor()
                titleView.textSize = 14f
                titleView.setOnClickListener {
                    getPager()?.currentItem = index
                }
                return titleView
            }

            override fun getCount(): Int {
                return tabs.size
            }

            override fun getIndicator(context: Context?): IPagerIndicator {
                val indicator = NormalPaddingPagerIndicator(context)
//                indicator.verticalPadding = context?.resources?.getDimension(R.dimen.qb_px_2)!!.toInt()
                indicator.horizontalPadding =
                    -context?.resources?.getDimension(R.dimen.qb_px_4)!!.toInt()
                indicator.roundRadius = context?.resources?.getDimension(R.dimen.qb_px_0)!!
                indicator.startInterpolator = AccelerateInterpolator()
                indicator.endInterpolator = DecelerateInterpolator(2.0f)
                indicator.fillColor = getFillColor()
                if (getStartColor() != 0 && getEndColor() != 0) {
                    val linearGradient = LinearGradient(
                        0f, 0f, 0f, dp2px(24)
                        , intArrayOf(
                            getStartColor()
                            , getEndColor()
                        ), null, Shader.TileMode.MIRROR
                    )
                    indicator.getmPaint().shader = linearGradient
                }
                return indicator
            }
        }

    }

    private fun setTabText(
        index: Int,
        titleView: ColorTransitionPagerTitleView
    ) {
        if (tabs[index].getTabRes() == 0) {
            titleView.text = tabs[index].getTabTitle()
        } else {
            titleView.setCompoundDrawablesWithIntrinsicBounds(tabs[index].getTabRes(), 0, 0, 0)
        }
    }


    open fun getPager(): ViewPager? {
        if (pager == null) {
            try {
                pager = (parentView.children.find {
                    it is ViewPager
                } as ViewPager).apply {
                    //设备为全面屏的时候 装扮商城变高一些  拍照的动作和背景也会跟随着一起变
                    if (isAllScreenDevice(AppLifecyclesImpl.appContext)) {
                        layoutParams.height = dp2px(252).toInt()
                    }
                }
            } catch (e: Exception) {
            }
        }
        pager?.offscreenPageLimit = getPagerLimit()
        return pager
    }

    open fun getMagicIndicator(): MagicIndicator? {
        if (mIndicator == null) {
            val childs = parentView.children
            for (child in childs) {
                if (child is MagicIndicator) {
                    mIndicator = child
                }
            }
        }

        return mIndicator
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