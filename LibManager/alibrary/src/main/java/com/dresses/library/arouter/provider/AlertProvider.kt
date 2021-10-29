package com.dresses.library.arouter.provider

import android.content.Context
import com.alibaba.android.arouter.facade.template.IProvider

/**
 * @author: zzs
 * @date: 2020/10/28
 */
public interface AlertProvider : IProvider {
    fun needShowAlarmTips(context: Context): Boolean
    fun goToBgStartSet(context: Context)
    fun clearAlert()

    fun addSignAlert()
    fun deleteSignAlert()

    fun isTimeRepeat(hour: Int, minutes: Int,id:Int, repeat: List<Int>): Boolean
}
