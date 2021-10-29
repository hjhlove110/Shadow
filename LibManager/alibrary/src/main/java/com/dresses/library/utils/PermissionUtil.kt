package com.dresses.library.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.text.TextUtils
import androidx.fragment.app.FragmentActivity
import com.dresses.library.AppLifecyclesImpl
import com.dresses.library.R
import com.jess.arms.utils.LogUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayList

object PermissionUtil {
    const val settingRequestCode = 0x1911
    private val MARK = Build.MANUFACTURER.toLowerCase(Locale.CHINA)

    val TAG = "Permission"

    fun requestPermission(
        activity: FragmentActivity,
        requestPermission: RequestPermissionListener,
        vararg permissions: String
    ) {
        if (permissions.isEmpty()) return
        val weakReference = WeakReference<FragmentActivity>(activity)

        weakReference.get()?.let { activity ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (!activity.isDestroyed)
                    requestPermission(activity, permissions, requestPermission)
            } else {
                requestPermission(activity, permissions, requestPermission)
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun requestPermission(
        activity: FragmentActivity,
        permissions: Array<out String>,
        requestPermission: RequestPermissionListener
    ) {
        val fragments = activity.supportFragmentManager.fragments

        val rxPermissions = if (fragments.isNullOrEmpty()) {
            RxPermissions(activity)
        } else {
            RxPermissions(fragments[0])
        }

//        //过滤调已经申请过的权限
        val needRequest = permissions.filter {
            !rxPermissions.isGranted(it)
        }

        if (needRequest.isEmpty()) {//全部权限都已经申请过，直接执行操作
            requestPermission.onRequestPermissionSuccess()
        } else {//没有申请过,则开始申请
            val toTypedArray = needRequest.toTypedArray()
            rxPermissions
                .requestEachCombined(*toTypedArray)
                .subscribe { itx ->
                    if (!itx.granted) {
                        if (itx.shouldShowRequestPermissionRationale) {
                            requestPermission.onRequestPermissionFailure(mutableListOf(itx.name))
                            return@subscribe
                        } else {
                            requestPermission.onRequestPermissionFailureWithAskNeverAgain(
                                mutableListOf(itx.name)
                            )
                            return@subscribe
                        }
                    }
                    requestPermission.onRequestPermissionSuccess()
                }
        }
    }

    fun isHavePermission(activity: FragmentActivity, permissions: String): Boolean {
        return RxPermissions(activity).isGranted(permissions)
    }

    /**
     * 请求摄像头权限
     */
    fun launchCamera(activity: FragmentActivity, requestPermission: RequestPermissionListener) {
        requestPermission(
            activity,
            requestPermission,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO
        )
    }

    /**
     * 请求录屏权限
     */
    fun launchRecord(activity: FragmentActivity, requestPermission: RequestPermissionListener) {
        requestPermission(
            activity,
            requestPermission,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    /**
     * 请求外部存储的权限
     */
    fun externalStorage(activity: FragmentActivity, requestPermission: RequestPermissionListener) {
        requestPermission(
            activity,
            requestPermission,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    /**
     * live2d录屏
     */
    fun externalLiveRecord(
        activity: FragmentActivity,
        requestPermission: RequestPermissionListener
    ) {
        requestPermission(
            activity,
            requestPermission,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }

    /**
     * 读取日历
     */

    fun calendarPermission(
        activity: FragmentActivity,
        requestPermission: RequestPermissionListener
    ) {
        requestPermission(
            activity,
            requestPermission,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
        )
    }

    /**
     * 录音权限
     */
    fun voiceRecord(
        activity: FragmentActivity,
        requestPermission: RequestPermissionListener
    ) {
        requestPermission(
            activity,
            requestPermission,
            Manifest.permission.RECORD_AUDIO
        )
    }

    /**
     * 请求外部存储的权限
     */
    fun wrCalender(activity: FragmentActivity, requestPermission: RequestPermissionListener) {
        requestPermission(
            activity,
            requestPermission,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR
        )
    }

    /**
     * 请求震动的权限
     */
    fun externalVibrate(activity: FragmentActivity, requestPermission: RequestPermissionListener) {
        requestPermission(
            activity,
            requestPermission,
            Manifest.permission.VIBRATE
        )
    }

    /**
     * 请求手机基本信息 必须要的信息
     */
    fun externalReadPhoneStateMust(
        activity: FragmentActivity,
        requestPermission: RequestPermissionListener
    ) {
        requestPermission(
            activity,
            requestPermission,
            Manifest.permission.READ_PHONE_STATE
        )
    }

    /**
     * 请求手机基本信息
     * 每天最多请求一次，防止过于平凡的请求。
     * 非必要权限  7天内只弹一次
     */
    fun externalReadPhoneState(
        activity: FragmentActivity,
        requestPermission: RequestPermissionListener
    ) {
        if (System.currentTimeMillis() / 1000 - get("lastRequest", 0L) >= 7 * 60 * 60 * 24) {
            put("lastRequest", System.currentTimeMillis() / 1000)
            requestPermission(
                activity,
                requestPermission,
                Manifest.permission.READ_PHONE_STATE
            )
        } else {
            requestPermission.onRequestPermissionSuccess()
        }
    }

    /**
     * 快捷方式的权限
     */
    fun externalShortcut(activity: FragmentActivity, requestPermission: RequestPermissionListener) {
        requestPermission(
            activity, requestPermission
            , Manifest.permission.INSTALL_SHORTCUT
            , Manifest.permission.UNINSTALL_SHORTCUT
        )
    }

    /**
     * 请求发送短信权限
     */
    fun sendSms(activity: FragmentActivity, requestPermission: RequestPermissionListener) {
        requestPermission(activity, requestPermission, Manifest.permission.SEND_SMS)
    }


    /**
     * 请求打电话权限
     */
    fun callPhone(activity: FragmentActivity, requestPermission: RequestPermissionListener) {
        requestPermission(activity, requestPermission, Manifest.permission.CALL_PHONE)
    }

    /**
     * 请求电源权限
     */
    fun callPowerManager(activity: FragmentActivity, requestPermission: RequestPermissionListener) {
        requestPermission(activity, requestPermission, Manifest.permission.WAKE_LOCK)
    }


    /**
     * 请求获取手机状态的权限
     */
    fun readPhonestate(activity: FragmentActivity, requestPermission: RequestPermissionListener) {
        requestPermission(activity, requestPermission, Manifest.permission.READ_PHONE_STATE)
    }

    /**
     * 请求获取位置
     */
    fun location(activity: FragmentActivity, requestPermission: RequestPermissionListener) {
        requestPermission(
            activity,
            requestPermission,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    }

    /**
     *
     */
    fun changeWifiState(activity: FragmentActivity, requestPermission: RequestPermissionListener?) {
        requestPermission(
            activity,
            requestPermission?:object :RequestPermissionListener{
                override fun onRequestPermissionSuccess() {
                    LogUtils.debugInfo("NetworkChangeReceiver", "onRequestPermissionSuccess")
                }

                override fun onRequestPermissionFailure(permissions: List<String>) {
                    LogUtils.debugInfo("NetworkChangeReceiver", "onRequestPermissionFailure")
                }

                override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
                    LogUtils.debugInfo("NetworkChangeReceiver", "onRequestPermissionFailureWithAskNeverAgain")
                }
            },
            Manifest.permission.CHANGE_WIFI_STATE
            ,Manifest.permission.UPDATE_DEVICE_STATS
            ,Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    /**
     * 跳转到miui的权限管理页面
     */
    fun gotoMiuiPermission(): Intent {
        val intent = Intent("miui.intent.action.APP_PERM_EDITOR")
        intent.putExtra("extra_pkgname", AppLifecyclesImpl.appContext.packageName)
        if (hasActivity(AppLifecyclesImpl.appContext, intent)) return intent

        intent.setClassName(
            "com.miui.securitycenter",
            "com.miui.permcenter.permissions.AppPermissionsEditorActivity"
        )
        if (hasActivity(AppLifecyclesImpl.appContext, intent)) return intent

        intent.setClassName(
            "com.miui.securitycenter",
            "com.miui.permcenter.permissions.PermissionsEditorActivity"
        )
        return if (hasActivity(
                AppLifecyclesImpl.appContext,
                intent
            )
        ) intent else gotoDefaultSetting()

    }

    /**
     * 跳转到魅族的权限管理系统
     */
    private fun gotoMeizuPermission(): Intent {
        val intent = Intent("com.meizu.safe.security.SHOW_APPSEC")
        intent.putExtra("packageName", AppLifecyclesImpl.appContext.packageName)
        intent.setClassName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity")
        return if (hasActivity(
                AppLifecyclesImpl.appContext,
                intent
            )
        ) intent else gotoDefaultSetting()
    }

    /**
     * 华为的权限管理页面
     */
    private fun gotoHuaweiPermission(): Intent {
        val intent = Intent()
        intent.setClassName(
            "com.huawei.systemmanager",
            "com.huawei.permissionmanager.ui.MainActivity"
        )
        return if (hasActivity(
                AppLifecyclesImpl.appContext,
                intent
            )
        ) intent else gotoDefaultSetting()

    }

    private fun oppoApi(): Intent {
        val intent = Intent()
        intent.putExtra("packageName", AppLifecyclesImpl.appContext.packageName)
        intent.setClassName(
            "com.color.safecenter",
            "com.color.safecenter.permission.PermissionManagerActivity"
        )
        if (hasActivity(AppLifecyclesImpl.appContext, intent)) return intent
        intent.setClassName("com.oppo.safe", "com.oppo.safe.permission.PermissionAppListActivity")
        return if (hasActivity(
                AppLifecyclesImpl.appContext,
                intent
            )
        ) intent else gotoDefaultSetting()
    }

    private fun vivoApi(): Intent {
        val intent = Intent()
        intent.putExtra("packagename", AppLifecyclesImpl.appContext.packageName)
        intent.setClassName(
            "com.vivo.permissionmanager",
            "com.vivo.permissionmanager.activity.SoftPermissionDetailActivity"
        )
        if (hasActivity(AppLifecyclesImpl.appContext, intent)) return intent
        intent.setClassName(
            "com.iqoo.secure",
            "com.iqoo.secure.safeguard.SoftPermissionDetailActivity"
        )
        return if (hasActivity(
                AppLifecyclesImpl.appContext,
                intent
            )
        ) intent else gotoDefaultSetting()
    }

    private fun gotoDefaultSetting(): Intent {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.fromParts("package", AppLifecyclesImpl.appContext.packageName, null)
        return intent
    }

    fun gotoSettingPage(activity: FragmentActivity) {
        var intent: Intent
        intent = if (MARK.contains("huawei")) {
            gotoHuaweiPermission()
        } else if (MARK.contains("xiaomi")) {
            gotoMiuiPermission()
        } else if (MARK.contains("oppo")) {
            oppoApi()
        } else if (MARK.contains("vivo")) {
            vivoApi()
        } else if (MARK.contains("meizu")) {
            gotoMeizuPermission()
        } else {
            gotoDefaultSetting()
        }
        try {
            activity.startActivityForResult(intent, settingRequestCode)
        } catch (e: Exception) {
            intent = gotoDefaultSetting()
            activity.startActivityForResult(intent, settingRequestCode)
        }

    }

    private fun hasActivity(context: Context, intent: Intent): Boolean {
        val packageManager = context.packageManager
        return packageManager.queryIntentActivities(
            intent,
            PackageManager.MATCH_DEFAULT_ONLY
        ).size > 0
    }

    interface RequestPermissionListener {
        /**
         * 权限请求成功
         */
        fun onRequestPermissionSuccess()

        /**
         * 用户拒绝了权限请求, 权限请求失败, 但还可以继续请求该权限
         *
         * @param permissions 请求失败的权限名
         */
        fun onRequestPermissionFailure(permissions: List<String>)

        /**
         * 用户拒绝了权限请求并且用户选择了以后不再询问, 权限请求失败, 这时将不能继续请求该权限, 需要提示用户进入设置页面打开该权限
         *
         * @param permissions 请求失败的权限名
         */
        fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>)

    }

    abstract class ResultListener : RequestPermissionListener {
        override fun onRequestPermissionFailure(permissions: List<String>) {
        }

        override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
        }

        override fun onRequestPermissionSuccess() {
        }
    }
}

abstract class RequestPermissionSuccessListener : PermissionUtil.RequestPermissionListener {
    constructor(activity: FragmentActivity) : this(activity, true)
    constructor(activity: FragmentActivity, showSetting: Boolean = true) {
        weakReference = WeakReference(activity)
        this.showSetting = showSetting
    }

    private var weakReference: WeakReference<FragmentActivity>? = null
    private var showSetting: Boolean = true
    override fun onRequestPermissionFailure(permissions: List<String>) {
    }

    override fun onRequestPermissionFailureWithAskNeverAgain(permissions: List<String>) {
        weakReference?.get()?.let {
            if (!showSetting) return
            if (permissions.isEmpty()) return
            val permissions = permissions[0].split(",")
            val permissionNames: List<String> = changePermissionsToText(permissions)
            val message: String = AppLifecyclesImpl.appContext.getString(
                R.string.message_permission_always_failed,
                TextUtils.join("\n", permissionNames)
            )
            AlertDialog.Builder(it)
                .setCancelable(false)
                .setTitle(R.string.title_dialog)
                .setMessage(message)
                .setPositiveButton(R.string.setting) { dialog, which ->
                    PermissionUtil.gotoSettingPage(
                        it
                    )
                }
                .setNegativeButton(R.string.common_cancel) { dialog, which -> }
                .show()
        }

    }

    private fun changePermissionsToText(permissions: List<String>): List<String> {
        val textList: ArrayList<String> = ArrayList()
        var message = ""
        for (permission in permissions) {
            when (permission) {
                Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION -> {
                    message =
                        AppLifecyclesImpl.appContext.resources.getString(R.string.permission_name_location)
                }
                Manifest.permission.READ_CALENDAR, Manifest.permission.WRITE_CALENDAR -> {
                    message =
                        AppLifecyclesImpl.appContext.resources.getString(R.string.permission_name_calendar)
                }
                Manifest.permission.CAMERA -> {
                    message =
                        AppLifecyclesImpl.appContext.resources.getString(R.string.permission_name_camera)
                }
                Manifest.permission.RECORD_AUDIO -> {
                    message =
                        AppLifecyclesImpl.appContext.getString(R.string.permission_name_microphone)
                }
                Manifest.permission.READ_PHONE_STATE -> {
                    message = AppLifecyclesImpl.appContext.getString(R.string.permission_name_phone)
                }
                Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE -> {
                    message =
                        AppLifecyclesImpl.appContext.getString(R.string.permission_name_storage)
                }
                Manifest.permission.READ_SMS, Manifest.permission.SEND_SMS -> {
                    message = AppLifecyclesImpl.appContext.getString(R.string.permission_name_sms)
                }
            }
            if (message.isNotEmpty() && !textList.contains(message)) {
                textList.add(message)
            }
        }
        return textList
    }
}