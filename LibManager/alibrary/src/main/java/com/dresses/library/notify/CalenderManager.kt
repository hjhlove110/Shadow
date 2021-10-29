package com.dresses.library.notify

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.graphics.Color
import android.net.Uri
import android.provider.CalendarContract
import android.text.TextUtils
import com.jess.arms.utils.LogUtils
import java.util.*


/**
 * @author HUAN
 * @date
 */
object CalenderManager {
    private const val CALENDAR_URL = "content://com.android.calendar/calendars";
    private const val  CALENDAR_EVENT_URL = "content://com.android.calendar/events";
    private const val  CALENDAR_REMINDER_URL = "content://com.android.calendar/reminders";

    private const val CALENDARS_NAME = "ciyuan"
    private const val CALENDARS_ACCOUNT_NAME = "ciyuan@nineton.com"
    private const val CALENDARS_ACCOUNT_TYPE = "com.android.ciyuan"
    private const val CALENDARS_DISPLAY_NAME = "jingling"

    /**
     * 检查是否已经添加了日历账户，如果没有添加先添加一个日历账户再次进行查询
     * 获取账户成功返回账户id，否则返回-1
     */
    @Deprecated("")
    private fun checkAndAddCalendarAccount(context: Context): Int {
        val oldId: Int = checkCalendarAccount(context)
        return if (oldId >= 0) {
            oldId
        } else {
            val addId: Long = addCalendarAccount(context)
            if (addId >= 0) {
                checkCalendarAccount(context)
            } else {
                -1
            }
        }
    }

    @Deprecated("")
    private fun checkCalendarAccount(context: Context): Int {
        val userCursor: Cursor? = context.contentResolver.query(Uri.parse(CALENDAR_URL),
                null, null, null, null)
        return userCursor.use { userCursor ->
            if (userCursor == null) { // 查询返回空值
                return -1
            }
            val count: Int = userCursor.count
            if (count > 0) { // 存在现有账户，取第一个账户的id返回
                userCursor.moveToFirst()
                userCursor.getInt(userCursor.getColumnIndex(CalendarContract.Calendars._ID))
            } else {
                -1
            }
        }
    }

    @Deprecated("")
    private fun addCalendarAccount(context: Context): Long {
        val timeZone: TimeZone = TimeZone.getDefault()
        val value = ContentValues()
        value.put(CalendarContract.Calendars.NAME, CALENDARS_NAME)
        value.put(CalendarContract.Calendars.ACCOUNT_NAME, CALENDARS_ACCOUNT_NAME)
        value.put(CalendarContract.Calendars.ACCOUNT_TYPE, CALENDARS_ACCOUNT_TYPE)
        value.put(CalendarContract.Calendars.CALENDAR_DISPLAY_NAME, CALENDARS_DISPLAY_NAME)
        value.put(CalendarContract.Calendars.VISIBLE, 1)
        value.put(CalendarContract.Calendars.CALENDAR_COLOR, Color.BLUE)
        value.put(CalendarContract.Calendars.CALENDAR_ACCESS_LEVEL,
                CalendarContract.Calendars.CAL_ACCESS_OWNER)
        value.put(CalendarContract.Calendars.SYNC_EVENTS, 1)
        value.put(CalendarContract.Calendars.CALENDAR_TIME_ZONE, timeZone.id)
        value.put(CalendarContract.Calendars.OWNER_ACCOUNT, CALENDARS_ACCOUNT_NAME)
        value.put(CalendarContract.Calendars.CAN_ORGANIZER_RESPOND, 0)
        var calendarUri = Uri.parse(CALENDAR_URL)
        calendarUri = calendarUri.buildUpon()
                .appendQueryParameter(CalendarContract.CALLER_IS_SYNCADAPTER, "true")
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_NAME,
                        CALENDARS_ACCOUNT_NAME
                )
                .appendQueryParameter(CalendarContract.Calendars.ACCOUNT_TYPE,
                        CALENDARS_ACCOUNT_TYPE
                )
                .build()
        val result = context.contentResolver.insert(calendarUri, value)
        return if (result == null) -1 else ContentUris.parseId(result)
    }


    fun insertCalendarEvent(context: Context?, title: String?, description: String?,
                            beginTimeMillis: Long, endTimeMillis: Long): Boolean {
        var beginTimeMillis = beginTimeMillis
        var endTimeMillis = endTimeMillis
        if (context == null || TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            return false
        }
        val calId = checkAndAddCalendarAccount(context) // 获取日历账户的id
        if (calId < 0) { // 获取账户id失败直接返回，添加日历事件失败
            return false
        }
        // 如果起始时间为零，使用当前时间
        if (beginTimeMillis == 0L) {
            val beginCalendar = Calendar.getInstance()
            beginTimeMillis = beginCalendar.timeInMillis
        }
        // 如果结束时间为零，使用起始时间+30分钟
        if (endTimeMillis == 0L) {
            endTimeMillis = beginTimeMillis + 60 * 60 * 1000
        }
        LogUtils.debugInfo("CalenderManager" , "开始时间：${beginTimeMillis}\n结束时间：${endTimeMillis}" +
                "\n当前时间：${System.currentTimeMillis()}")
        try {
            /** 插入日程  */
            val eventValues = ContentValues()
            eventValues.put(CalendarContract.Events.DTSTART, beginTimeMillis)
            eventValues.put(CalendarContract.Events.DTEND, endTimeMillis)
//            eventValues.put(CalendarContract.Events.DURATION, "P24H")//RFC2445 编码  间隔24小时
            eventValues.put(CalendarContract.Events.TITLE, title)
            eventValues.put(CalendarContract.Events.DESCRIPTION, description)
            eventValues.put(CalendarContract.Events.CALENDAR_ID, calId)
            eventValues.put(CalendarContract.Events.EVENT_LOCATION, "我的次元")
            eventValues.put(CalendarContract.Events.RRULE, "FREQ=DAILY;INTERVAL=1")//每1天发生一次，直到永远
//            eventValues.put(CalendarContract.Events.RDATE, "小精灵")
            val tz = TimeZone.getDefault() // 获取默认时区
            eventValues.put(CalendarContract.Events.EVENT_TIMEZONE, tz.id)
            val eUri = context.contentResolver.insert(Uri.parse(CALENDAR_EVENT_URL), eventValues)
            val eventId = ContentUris.parseId(eUri!!)
            if (eventId == 0L) { // 插入失败
                LogUtils.debugInfo("CalenderManager" , "插入失败1")

                return false
            }
            /** 插入提醒 - 依赖插入日程成功  */
            val reminderValues = ContentValues()
            // uri.getLastPathSegment();
            reminderValues.put(CalendarContract.Reminders.EVENT_ID, eventId)
            reminderValues.put(CalendarContract.Reminders.MINUTES, 0) // 提前10分钟提醒
            reminderValues.put(CalendarContract.Reminders.METHOD,
                    CalendarContract.Reminders.METHOD_ALERT)
            val rUri = context.contentResolver.insert(Uri.parse(CALENDAR_REMINDER_URL),
                    reminderValues)
            if (rUri == null || ContentUris.parseId(rUri) == 0L) {
                LogUtils.debugInfo("CalenderManager" , "插入失败2")
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
        LogUtils.debugInfo("CalenderManager" , "插入成功")
        return true
    }


    @Deprecated("")
    fun deleteCalendarEvent(context: Context?, title: String) {
        if (context == null) {
            return
        }
        val eventCursor = context.contentResolver.query(Uri.parse(CALENDAR_EVENT_URL),
                null, null, null, null)
        eventCursor.use { eventCursor ->
            if (eventCursor == null) { // 查询返回空值
                LogUtils.debugInfo("CalenderManager" , "五时间")
                return
            }
            if (eventCursor.count > 0) { // 遍历所有事件，找到title跟需要查询的title一样的项
                eventCursor.moveToFirst()
                while (!eventCursor.isAfterLast) {
                    val eventTitle = eventCursor.getString(eventCursor.getColumnIndex("title"))
                    if (!TextUtils.isEmpty(title) && title == eventTitle) {
                        val id = eventCursor.getInt(eventCursor
                                .getColumnIndex(CalendarContract.Calendars._ID)) // 取得id
                        val deleteUri = ContentUris.withAppendedId(Uri.parse(CALENDAR_EVENT_URL), id.toLong())
                        val rows = context.contentResolver.delete(deleteUri, null, null)
                        if (rows == -1) { // 事件删除失败
                            LogUtils.debugInfo("CalenderManager" , "删除失败")
                            return
                        }
                        LogUtils.debugInfo("CalenderManager" , "删除成功")
                    }
                    eventCursor.moveToNext()
                }
            }
        }
    }

}