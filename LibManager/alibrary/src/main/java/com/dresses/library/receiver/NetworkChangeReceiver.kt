package com.dresses.library.receiver

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.WIFI_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.getSystemService
import com.dresses.library.AppLifecyclesImpl
import com.dresses.library.arouter.EventTags
import com.dresses.library.utils.PermissionUtil
import com.jess.arms.integration.EventBusManager
import com.jess.arms.utils.LogUtils


class NetworkChangeReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val connectionManager =
            getSystemService(AppLifecyclesImpl.appContext,ConnectivityManager::class.java) as ConnectivityManager?
        val networkInfo = connectionManager?.activeNetworkInfo


            if (networkInfo != null && networkInfo.isAvailable) {
                when (networkInfo.type) {
                    ConnectivityManager.TYPE_MOBILE -> {
                        EventBusManager.getInstance().post(1,EventTags.EVENT_TAG_NETWORK_STATE_CHANGE)
                    }
                    ConnectivityManager.TYPE_WIFI -> {
                        EventBusManager.getInstance().post(1,EventTags.EVENT_TAG_NETWORK_STATE_CHANGE)
                        LogUtils.debugInfo("NetworkChangeReceiver","wifi连接")
                        val wifiManager =
                            AppLifecyclesImpl.appContext.getSystemService(WIFI_SERVICE) as WifiManager?
                        val wifiInfo = wifiManager?.connectionInfo
                        if (ActivityCompat.checkSelfPermission(
                                AppLifecyclesImpl.appContext,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            wifiManager?.configuredNetworks?.let {
                                for (t in it){
                                    LogUtils.debugInfo("NetworkChangeReceiver",t.SSID)
                                }
                            }
                            return
                        }

                        wifiInfo?.let {
                            LogUtils.debugInfo("NetworkChangeReceiver", it.ssid)
                            EventBusManager.getInstance().post(it.ssid,EventTags.EVENT_TAG_NETWORK_STATE_WIFI)
                        }
                    }
                    else -> {
                    }
                }
            } else {
                EventBusManager.getInstance().post(0,EventTags.EVENT_TAG_NETWORK_STATE_CHANGE)
            }

    }


    /**
     * 获取SSID
     * @param activity 上下文
     * @return  WIFI 的SSID
     */
    fun getWIFISSID(): String? {
        val ssid = "unknown id"
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O_MR1) { //针对Android9.0 10.0 11.0的
            val connManager = (AppLifecyclesImpl.appContext
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            val networkInfo: NetworkInfo = connManager.activeNetworkInfo!!
            if (networkInfo.isConnected()) {
                if (networkInfo.getExtraInfo() != null) {
                    return networkInfo.getExtraInfo().replace("\"", "")
                }
            }
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O || Build.VERSION.SDK_INT == Build.VERSION_CODES.P) {
            val mWifiManager = (AppLifecyclesImpl.appContext
                .getSystemService(WIFI_SERVICE) as WifiManager)
            val info: WifiInfo = mWifiManager.connectionInfo
            return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
                info.getSSID()
            } else {
                info.getSSID().replace("\"", "")
            }
        }
        return ssid
    }
}