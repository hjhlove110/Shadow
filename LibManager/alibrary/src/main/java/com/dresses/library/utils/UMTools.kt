package com.dresses.library.utils

import android.content.Context

object UMTools {
    /**
     * 初始化友盟
     */
    fun preInit(ct: Context, pushSecret: String? = null) {



    }

    /**
     * 关闭账号统计
     */
    fun stopUser() {
    }

    fun eventLog(context: Context, eventId: String, msg: String) {
        umEventLog(context, eventId, msg)
        //其他统计工具日志发送
    }

    /**
     * 友盟日志发送
     */
    private fun umEventLog(context: Context, eventId: String, msg: String) {
        val music = mutableMapOf<String, Any>()
        music["message"] = msg
    }

    /**
     * 使用中台sdk获取设备唯一标识符
     */
    fun getUUID(): String {
        return "cc.b(AppLifecyclesImpl.appContext)"
    }
}