package com.dresses.library.interceptor

import android.os.Build
import com.dresses.library.utils.get

object HttpConfig {
    private var mHeaderMap: Map<String, String>? = null
    private var mParamMap = mapOf(
        "device" to "android",
        "app_ver" to get("app_ver", "1.0.5"),
        "os_ver" to "${Build.VERSION.RELEASE}",
        "resolution" to "1920*1080",
        "deviceNo" to "a58ff406260d637x",
        "country" to "US",
        "lang" to "en",
        "installTime" to get("installTime", "1629185215"),
        "resolution_e" to "1920*1080",
        "simcard" to "1",
        "brand" to "${Build.MANUFACTURER.toUpperCase()}",
        "model" to "${Build.MODEL}",
        "idfa" to get("idfa", "987a8069-9749-4fad-ae5e-c2a864a1de85"),
        "app_id" to get("app_id", get("app_id", "143"))
    )
    private var mBaseUrl: String = ""
    private var isLog: Boolean = true

    fun init(
        baseUrl: String,
        headers: Map<String, String>? = null,
        params: Map<String, String>? = null,
        isLog: Boolean = false
    ) {
        setBaseUrl(baseUrl)
        headers?.apply { setHeader(this) }
        params?.apply { setParam(this) }
        setLogEnable(isLog)
    }

    fun setBaseUrl(url: String) {
        mBaseUrl = url
    }

    fun getBaseUrl(): String {
        return checkNotNull(mBaseUrl)
    }

    fun setHeader(map: Map<String, String>) {
        mHeaderMap = map
    }

    fun setParam(map: Map<String, String>) {
        mParamMap = map
    }

    fun getHeader(): Map<String, String>? {
        return mHeaderMap
    }

    fun getParam(): Map<String, String>? {
        return mParamMap
    }

    fun setLogEnable(isLog: Boolean) {
        this.isLog = isLog
    }

}