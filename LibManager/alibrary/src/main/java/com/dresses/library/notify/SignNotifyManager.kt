package com.dresses.library.notify

import android.content.Context
import com.dresses.library.utils.get
import com.dresses.library.utils.put
import java.util.*


/**
 * @author HUAN
 * @date
 * 用 AlarmManager 发送闹钟任务
 */
object SignNotifyManager {

    private const val NOTIFY_TIME_HOUR = "notify_time_hour"
    private const val NOTIFY_TIME_MIN = "notify_time_min"
    private const val NOTIFY_IS_SET = "notify_is_set"

    fun getIsSetNotify(): Boolean {
        return get(NOTIFY_IS_SET, false)
    }

    private fun setIsSetNotify(isNotify: Boolean) {
        put(NOTIFY_IS_SET, isNotify)
    }


    const val notifyTitle = "今天签到了吗"
    const val notifyContent = "快去签到领礼品吧"
    fun addNotify(context: Context,hour:Int,min:Int) {
        if (getIsSetNotify()){
            deleteNotify(context)
        }
        val calendar = Calendar.getInstance(Locale.getDefault())
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, min)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        CalenderManager.insertCalendarEvent(
            context, notifyTitle
            , notifyContent
            , calendar.timeInMillis
            , calendar.timeInMillis + 10 * 60 * 1000
        )
        setIsSetNotify(true)
    }

    fun deleteNotify(context: Context){
        CalenderManager.deleteCalendarEvent(context, notifyTitle)
        setIsSetNotify(false)
    }

}