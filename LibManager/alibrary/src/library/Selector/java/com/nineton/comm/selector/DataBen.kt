package com.nineton.comm.selector

import androidx.annotation.Keep

/**
 * @author HUAN
 * @date
 */
@Keep
abstract class TabBean{
    abstract fun getTabTitle():String
    abstract fun getTabType():Int
    abstract fun getTabRes():Int
}
@Keep
data class CommTabBean(val title:String, var type:Int,val tRes:Int = 0):TabBean(){
    override fun getTabTitle():String {
        return title
    }
    override fun getTabType(): Int {
        return type
    }

    override fun getTabRes(): Int {
        return tRes
    }
}
