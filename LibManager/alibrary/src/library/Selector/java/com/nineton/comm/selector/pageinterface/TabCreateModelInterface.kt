package com.nineton.comm.selector.pageinterface

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dresses.library.utils.logDebug
import com.nineton.comm.selector.TabBean

/**
 * @author HUAN
 * @date
 */
interface TabCreateModelInterface {
    fun createTitle(): MutableList<TabBean>
    fun createFragments(): MutableList<Fragment>?//少量fragment
    fun getStateFragment(position: Int): Fragment?//较多fragment
    /**
     * 不需要回收的FragmentAdapter 已经实现
     */
    fun initViewPager(fragmentManager: FragmentManager): FragmentPagerAdapter {
        return object :
            FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
            override fun getItem(position: Int): Fragment {
                return createFragments()!![position]
            }

            override fun getCount(): Int {
                return createFragments()!!.size
            }
        }
    }

    /**
     * 较多tab 需要回收的FragmentAdapter 需要自己重写实现
     */
    fun initViewStatePager(fragmentManager: FragmentManager): FragmentStatePagerAdapter {
        return object : FragmentStatePagerAdapter(fragmentManager) {
            override fun getItem(position: Int): Fragment {
                return getStateFragment(position)!!
            }

            override fun getCount(): Int {
                return createTitle().size
            }
        }
    }
}