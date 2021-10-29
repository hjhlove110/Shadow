package com.dresses.library.utils.gravity

import android.app.Activity
import android.app.ActivityManager
import android.app.usage.UsageStatsManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Build
import com.dresses.library.AppLifecyclesImpl
import com.dresses.library.utils.get
import com.dresses.library.utils.putEntity
import java.io.Serializable


object AppInfoManager {

    private val SAVED_APP_PACKAGE_NAME = "saved_app_package_name"
    private val appInfos = mutableListOf<AppInfo>()

    private val packageManager by lazy {
        AppLifecyclesImpl.appContext.packageManager
    }
    private val packages: MutableList<PackageInfo> by lazy {
        packageManager.getInstalledPackages(0)
    }


    init {
        for (packageInfo in packages) {
//            if (ApplicationInfo.FLAG_SYSTEM and packageInfo.applicationInfo.flags == 0) {//非系统应用
//            }
            var tmpInfo = AppInfo()
            if (packageInfo != null) {
                if (packageInfo.packageName.isEmpty()) {
                    continue
                }
                packageManager.getLaunchIntentForPackage(packageInfo.packageName) ?: continue
                tmpInfo.appName = packageInfo.applicationInfo.loadLabel(packageManager).toString()
                tmpInfo.packageName = if (packageInfo.packageName != null) packageInfo.packageName else ""
                tmpInfo.versionName = if (packageInfo.versionName != null) packageInfo.versionName else ""
                tmpInfo.versionCode = packageInfo.versionCode
                tmpInfo.appIcon = packageInfo.applicationInfo.loadIcon(packageManager)
                appInfos.add(tmpInfo)

            }
        }
        appInfos.add(0, AppInfo( "", ""))
    }

    fun init() {
        //nothing
    }

    fun getApps(): MutableList<AppInfo> {
        return appInfos
    }

    fun startApp(context: Context) {
        val packageName: String = getAppInfoToStart()
        if (packageName.isEmpty()) {
            return
        }
//        goSystemHome(context)
        (context as Activity).window.decorView.postDelayed({

        },500)

        val launchIntent = packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            launchIntent.addCategory(Intent.CATEGORY_LAUNCHER)
            launchIntent.action = Intent.ACTION_MAIN
            launchIntent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
            context.startActivity(launchIntent)
            context.finish()
        }
    }

    fun goSystemHome(context: Context) {
        val intent = Intent()
        intent.action = Intent.ACTION_MAIN// "android.intent.action.MAIN"
        intent.addCategory(Intent.CATEGORY_HOME) //"android.intent.category.HOME"
        context.startActivity(intent)
    }

    fun saveAppInfoToStart(packageName: String) {
      putEntity(SAVED_APP_PACKAGE_NAME, packageName)
    }

    fun getAppInfoToStart(): String {
        return get(SAVED_APP_PACKAGE_NAME, "").replace("\"", "")
    }


    /**
     * 等价于@link{startApp(context: Context)}
     */
    fun openApp(context: Context) {
        val packageName: String = getAppInfoToStart()
        if (packageName.isEmpty()) {
            return
        }
        goSystemHome(context)
        val pi = packageManager.getPackageInfo(packageName, 0)

        val resolveIntent = Intent(Intent.ACTION_MAIN, null)
        resolveIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        resolveIntent.setPackage(pi.packageName)

        val apps = packageManager.queryIntentActivities(resolveIntent, 0)

        val ri = apps.iterator().next()
        if (ri != null) {
            val packageName = ri!!.activityInfo.packageName
            val className = ri!!.activityInfo.name

            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)

            val cn = ComponentName(packageName, className)

            intent.component = cn
            context.startActivity(intent)
        }
    }

    private fun getTopApp(): String? {
        //android5.0以上只能使用该方式
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val m = AppLifecyclesImpl.appContext.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            if (m != null) {
                val now = System.currentTimeMillis()
                //获取一小时之内的应用数据
                //<p> The caller must have {@link android.Manifest.permission#PACKAGE_USAGE_STATS} </p>
                val stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 60 * 1000 * 60, now)
                var topActivity = ""
                //取得最近运行的一个app，即当前运行的app
                if (stats != null && !stats.isEmpty()) {
                    var j = 0
                    for (i in stats.indices) {
                        if (stats[i].lastTimeUsed > stats[j].lastTimeUsed) {
                            j = i
                        }
                    }
                    topActivity = stats[j].packageName
                }
                return topActivity
            }
        } else {
            //android5.0以下可以获取所有运行程序的包名
            val mActivityManager =AppLifecyclesImpl.appContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
            val rti = mActivityManager!!.getRunningTasks(1)
            return rti[0].topActivity?.packageName
        }
        return null
    }


    private  fun getHomes():MutableList<String> {//获取所有桌面应用
        val names = mutableListOf<String>()
        val intent =  Intent(Intent.ACTION_MAIN)
        intent.addCategory(Intent.CATEGORY_HOME)
        val resolveInfo = packageManager.queryIntentActivities(intent,
        PackageManager.MATCH_DEFAULT_ONLY)
        for ( ri in resolveInfo) {
            names.add(ri.activityInfo.packageName)
        }
        return names
    }


    /**
     * 判断当前界面是否是桌面
     */
     fun isHome() :Boolean {
        val packageName = getTopApp()
        return getHomes().contains(packageName)
    }

}

data class AppInfo(
    var appName: String = ""
    , var packageName: String = ""
    , var versionName: String = ""
    , var versionCode: Int = 0
    , var appIcon: Drawable? = null
    , var isCheck: Boolean = false
) : Serializable

