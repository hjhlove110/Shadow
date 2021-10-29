package com.dresses.library.utils

import android.app.Activity
import android.app.ActivityManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Point
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings
import android.text.TextUtils
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.google.gson.Gson
import com.dresses.library.AppLifecyclesImpl
import com.dresses.library.BuildConfig
import com.dresses.library.R
import com.jess.arms.mvp.IView
import com.jess.arms.utils.RxLifecycleUtils
import com.tencent.mmkv.MMKV
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import jp.wasabeef.glide.transformations.CropSquareTransformation
import jp.wasabeef.glide.transformations.RoundedCornersTransformation
import java.io.*
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type
import java.math.BigInteger
import java.net.NetworkInterface
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern
import kotlin.collections.ArrayList

/**
 * 保存在手机里面的文件名
 */
const val FILE_NAME = "share_data"
const val WALL_FILE_NAME = "wall_share_data"

fun testImportSharedPreferences() {
    // 迁移旧数据
    val oldMan = AppLifecyclesImpl.appContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    if (oldMan.all.isNotEmpty()) {
        val kv = MMKV.mmkvWithID(FILE_NAME)
        kv?.importFromSharedPreferences(oldMan)
        oldMan.edit().clear().commit()
    }
}

/**
 * 获取MMkv储存实例
 * @param isMultiProcess 是否是可以跨进程访问
 */
fun getMmkv(isMultiProcess: Boolean = false): MMKV? {
    return if (!isMultiProcess) MMKV.mmkvWithID(FILE_NAME) else MMKV.mmkvWithID(
        WALL_FILE_NAME,
        MMKV.MULTI_PROCESS_MODE
    )
}

/**
 * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
 *  * @param isMultiProcess 是否是可以跨进程访问

 */
fun <V> put(key: String, value: V, isMultiProcess: Boolean = false) {
    val kv = getMmkv(isMultiProcess)

    when (value) {
        is String -> {
            kv?.encode(key, value)

        }
        is Int -> {
            kv?.encode(key, value)

        }
        is Boolean -> {
            kv?.encode(key, value)

        }
        is Float -> {
            kv?.encode(key, value)

        }
        is Long -> {
            kv?.encode(key, value)

        }
        is Parcelable -> {
            kv?.encode(key, value)
        }
    }
}

/**
 * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
 *  * @param isMultiProcess 是否是可以跨进程访问

 */
fun <V> get(key: String, defValue: V, isMultiProcess: Boolean = false): V {
    val kv = getMmkv(isMultiProcess)
    return when (defValue) {
        is String -> {
            kv?.decodeString(key, defValue) as V
        }
        is Int -> {
            kv?.decodeInt(key, defValue) as V
        }
        is Boolean -> {
            kv?.decodeBool(key, defValue) as V
        }
        is Float -> {
            kv?.decodeFloat(key, defValue) as V
        }
        is Long -> {
            kv?.decodeLong(key, defValue) as V
        }
        else -> defValue
    }
}

/**
 * 保存实体
 */
fun <T> putEntity(key: String, entity: T, isMultiProcess: Boolean = false) {
    var json = Gson().toJson(entity)
    put(key, json, isMultiProcess)
}

/**
 * 获取实体数组
 */
fun <T> getEntityList(key: String, clazz: Class<T>): MutableList<T>? {
    val json = get(key, "")
    val type = ParameterizedTypeImpl(clazz)
    var result: MutableList<T>? = Gson().fromJson(json, type)
    if (result == null) {
        result = ArrayList<T>()
    }
    return result
}


/**
 * 获取实体
 */
fun <T> getEntity(key: String, clazz: Class<T>, isMultiProcess: Boolean): T? {
    val json = get(key, "", isMultiProcess)
    val result: T? = Gson().fromJson(json, clazz)
    return result
}

/**
 * 移除某个key值已经对应的值
 * @param key
 */
fun remove(key: String) {
    val sp = AppLifecyclesImpl.appContext.getSharedPreferences(
        FILE_NAME,
        Context.MODE_PRIVATE
    )
    val editor = sp?.edit()
    editor?.remove(key)
    SharedPreferencesCompat.apply(editor!!)
}

/**
 * 清除所有数据
 *
 */
fun clear() {
    val sp = AppLifecyclesImpl.appContext.getSharedPreferences(
        FILE_NAME,
        Context.MODE_PRIVATE
    )
    val editor = sp?.edit()
    editor?.clear()
    SharedPreferencesCompat.apply(editor!!)
}

/**
 * 查询某个key是否已经存在
 *
 * @param key
 * @return
 */
fun contains(key: String): Boolean {
    val sp = AppLifecyclesImpl.appContext.getSharedPreferences(
        FILE_NAME,
        Context.MODE_PRIVATE
    )
    return sp?.contains(key) == true
}

fun parseStringMate(key: String): String? {
    try {
        val ai = AppLifecyclesImpl.appContext.packageManager?.getApplicationInfo(
            AppLifecyclesImpl.appContext.packageName, PackageManager.GET_META_DATA
        )
        val bundle = ai?.metaData
        var value = bundle?.getString(key)?.replace("^", "")

        return value
    } catch (e: Exception) {
    }
    return ""
}

/**
 * 返回所有的键值对
 *
 * @param context
 * @return
 */
fun getAll(context: Context): Map<String, *> {
    val sp = context.getSharedPreferences(
        FILE_NAME,
        Context.MODE_PRIVATE
    )
    return sp.all
}

/**
 * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
 *
 * @author zhy
 */
object SharedPreferencesCompat {
    private val sApplyMethod = findApplyMethod()

    /**
     * 反射查找apply的方法
     *
     * @return
     */
    private fun findApplyMethod(): Method? {
        try {
            val clz = SharedPreferences.Editor::class.java
            return clz.getMethod("apply")
        } catch (e: NoSuchMethodException) {
        }
        return null
    }

    /**
     * 如果找到则使用apply执行，否则使用commit
     *
     * @param editor
     */
    fun apply(editor: SharedPreferences.Editor) {
        try {
            sApplyMethod?.invoke(editor) ?: return
        } catch (e: IllegalArgumentException) {
        } catch (e: IllegalAccessException) {
        } catch (e: InvocationTargetException) {
        }

        editor.commit()
    }

}

/**
 * 根据分辨率从 dp 的单位 转成为 px(像素)
 */
fun dp2px(dpValue: Int): Float {
    val scale = AppLifecyclesImpl.appContext.resources.displayMetrics.density
    return (dpValue * scale + 0.5f)
}

/**
 * 根据分辨率从 px(像素) 的单位 转成为 dp
 */
fun px2dp(pxValue: Int): Float {
    val scale = AppLifecyclesImpl.appContext.resources.displayMetrics.density
    return pxValue / scale + 0.5f
}

/**
 * 获得屏幕尺寸
 * @return
 */
fun getScreenSizeWidth(): Int {
    val point = Point()
    val wm = AppLifecyclesImpl.appContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    wm.defaultDisplay.getSize(point)
    return point.x
}

/**
 * 获得屏幕尺寸
 * @return
 */
var screenheight = 0

fun getRealScreenSizeHeight(): Int {
    if (screenheight == 0) {
        val point = Point()
        val wm =
            AppLifecyclesImpl.appContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            wm.defaultDisplay.getRealSize(point)
        } else {
            wm.defaultDisplay.getSize(point)
        }
        screenheight = point.y
    }
    return screenheight
}

/**
 * 获得屏幕尺寸
 * @return
 */
fun getScreenSizeHeight(): Int {
    val point = Point()
    val wm = AppLifecyclesImpl.appContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    wm.defaultDisplay.getSize(point)
    return point.y
}

/**
 * 获取屏幕宽度
 *
 * @param context
 * @return
 */
fun getWindowWidth(context: Activity): Int {
    val dm = DisplayMetrics()
    context.windowManager.defaultDisplay.getMetrics(dm)
    return dm.widthPixels
}

/**
 * 展示图片
 */
fun ImageView.disPlay(url: Any) {
    if (isEnable(url))
        Glide.with(this).load(url)
            .placeholder(R.mipmap.ic_empty)
            .into(this)
    else
        logDebug("不支持的图片资源")
}

/**
 * 展示图片
 */
fun ImageView.disPlayGif(url: Any) {
    if (isEnable(url))
        Glide.with(this).asGif().load(url)
            .into(this)
    else
        logDebug("不支持的图片资源")
}

/**
 * 展示图片
 */
fun ImageView.disPlaySkipMemory(url: Any) {
    if (isEnable(url))
        Glide.with(this).load(url)
            .skipMemoryCache(true)
            .into(this)
    else
        logDebug("不支持的图片资源")
}

/**
 * 展示圆角图片
 */
fun ImageView.disPlayRoundCornerWithLastHold(url: Any, radius: Int) {
    val options = if (drawable != null) {
        val pbitmap = Bitmap.createBitmap((drawable as BitmapDrawable).bitmap)
        val d = BitmapDrawable(pbitmap)
        RequestOptions.bitmapTransform(GlideRoundTransform(context, radius)).placeholder(d)
    } else {
        RequestOptions.bitmapTransform(GlideRoundTransform(context, radius))
    }

    if (isEnable(url))

        Glide.with(this).load(url)
            .apply(options)
            .into(this)
    else
        logDebug("不支持的图片资源")
}


/**
 * 展示圆角图片
 */
fun ImageView.disPlayRoundCornerCenterCrop(url: Any, radius: Int) {
    val options = RequestOptions.bitmapTransform(GlideRoundTransform(context, radius))
    if (isEnable(url))
        Glide.with(this).load(url)
            .apply(options)
            .into(this)
    else
        logDebug("不支持的图片资源")
}

/**
 * 展示图片
 */
fun ImageView.disPlayBigPicture(url: Any) {
    if (isEnable(url))
        Glide.with(this).load(url)
            .apply(
                RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.AUTOMATIC)
                    .skipMemoryCache(true)
            )
            .format(DecodeFormat.PREFER_RGB_565)
            .override(getScreenSizeWidth(), getScreenSizeHeight())
            .into(this)
    else
        logDebug("不支持的图片资源")
}

/**
 * 展示图片
 */
fun ImageView.disPlayWithoutCache(url: Any) {
    if (isEnable(url))
        Glide.with(this)
            .applyDefaultRequestOptions(
                RequestOptions()
                    .skipMemoryCache(true)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .load(url)
            .into(this)
    else
        logDebug("不支持的图片资源")
}

/**
 * 展示图片
 */
fun ImageView.disPlayLocalWithoutCache(url: Any, resHolder: Int = 0) {
    if (context == null)
        return
    if (isEnable(url))
        if (url is String) {
            Glide.with(this)
                .applyDefaultRequestOptions(
                    RequestOptions()
                        .signature(ObjectKey(File(url).lastModified()))
                )
                .load(url)
                .placeholder(resHolder)
                .into(this)
        } else {
            Glide.with(this)
                .load(url)
                .placeholder(resHolder)
                .into(this)
        }
    else
        logDebug("不支持的图片资源")
}

/**
 * 展示正方形图片
 */
fun ImageView.disPlaySquare(url: Any) {
    if (isEnable(url))
        Glide.with(this).load(url)
            .apply(RequestOptions.bitmapTransform(CropSquareTransformation()))
            .into(this)
    else
        logDebug("不支持的图片资源")
}

/**
 * 展示圆形图片
 */
fun ImageView.disPlayCircle(url: Any) {
    if (isEnable(url))
        Glide.with(this).load(url)
            .apply(RequestOptions.bitmapTransform(CircleCrop()))
            .into(this)
    else
        logDebug("不支持的图片资源")
}

/**
 * 展示圆角图片
 */
fun ImageView.disPlayRoundCorner(url: Any, radius: Int) {
    if (isEnable(url))
        Glide.with(this).load(url)
            .apply(RequestOptions.bitmapTransform(RoundedCornersTransformation(radius, 0)))
            .into(this)
    else
        logDebug("不支持的图片资源")
}

fun String.safeConvertInt(): Int {
    return try {
        this.toInt()
    } catch (e: Exception) {
        0
    }
}

private fun isEnable(url: Any) =
    url is String || url is Uri || url is Int || url is Drawable || url is Bitmap

/**
 * 日志封装
 */
fun logDebug(info: String) {
    if (BuildConfig.DEBUG) Log.e("Debug->", "$info")
}

fun showToastShort(info: String, gravity: Int = Gravity.CENTER) {
    ToastUtils.showShortToast(info)
//    Toast.makeText(AppLifecyclesImpl.appContext, "$info", Toast.LENGTH_SHORT).apply {
//        setGravity(gravity, 0, 0)
//    }.show()
}

fun showToastShort(info: Int, gravity: Int = Gravity.CENTER) {
    Toast.makeText(
        AppLifecyclesImpl.appContext,
        AppLifecyclesImpl.appContext.getString(info),
        Toast.LENGTH_SHORT
    ).apply {
        setGravity(gravity, 0, 0)
    }.show()
}

fun showToastShortCenter(info: Int) {
    val toast = Toast.makeText(AppLifecyclesImpl.appContext, info, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

fun showToastShortCenter(info: String) {
    val toast = Toast.makeText(AppLifecyclesImpl.appContext, info, Toast.LENGTH_SHORT)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

fun showToastLong(info: String) {
    Toast.makeText(AppLifecyclesImpl.appContext, "$info", Toast.LENGTH_LONG).apply {
        setGravity(Gravity.CENTER, 0, 0)
    }.show()
}

fun showToastLong(info: Int) {
    Toast.makeText(
        AppLifecyclesImpl.appContext,
        AppLifecyclesImpl.appContext.getString(info),
        Toast.LENGTH_LONG
    ).apply {
        setGravity(Gravity.CENTER, 0, 0)
    }.show()
}

/**
 * RxJava普通线程切换
 */
fun <T> Observable<T>.applySchedulers(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.applyIoSchedulers(): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .unsubscribeOn(Schedulers.io())
        .observeOn(Schedulers.io())
}

/**
 * RxJava普通线程切换 绑定生命周期
 */
fun <T> Observable<T>.applySchedulers(view: IView): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .compose(RxLifecycleUtils.bindUntilDestroyEvent(view))
}

/**
 * RxJava普通线程切换 绑定生命周期 并自动调用loading方法
 */
fun <T> Observable<T>.applySchedulersWithLoading(view: IView): Observable<T> {
    return this.subscribeOn(Schedulers.io())
        .doOnSubscribe {
            view.showLoading()//显示进度条
        }
        .subscribeOn(AndroidSchedulers.mainThread())
        .observeOn(AndroidSchedulers.mainThread())
        .doFinally {
            view.hideLoading()//隐藏进度条
        }.compose(RxLifecycleUtils.bindUntilDestroyEvent(view))
}

/**
 * 启动到应用商店app详情界面
 *
 * @param appPkg    目标App的包名
 * @param marketPkg 应用商店包名 ,如果为""则由系统弹出应用商店列表供用户选择,否则调转到目标市场的应用详情界面，某些应用商店可能会失败
 */
fun goMark(mContext: Context, appPkg: String = mContext.packageName) {
    try {
        if (TextUtils.isEmpty(appPkg)) return
        val uri = Uri.parse("market://details?id=$appPkg")
        val intent = Intent(Intent.ACTION_VIEW, uri)
//        intent.setPackage("com.android.vending")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        mContext.startActivity(intent)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

inline fun <reified A : FragmentActivity> Context.jump(bundle: Bundle?) {
    val intent = Intent(this, A::class.java)
    bundle?.let {
        intent.putExtras(it)
    }
    this.startActivity(intent)
}

inline fun <reified A : FragmentActivity> Context.jumpAF(bundle: Bundle? = null) {
    val intent = Intent(this, A::class.java)
    bundle?.let {
        intent.putExtras(it)
    }
    this.startActivity(intent)
}

inline fun <reified A : FragmentActivity> Context.jumpAFClearTask(bundle: Bundle? = null) {
    val intent = Intent(this, A::class.java)
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    bundle?.let {
        intent.putExtras(it)
    }
    this.startActivity(intent)
}

/**
 * 把序列化的文件本地化，方便进程之间数据共享
 */
fun <V : Serializable> saveConfig(value: V) {
    Observable.just(value)
        .observeOn(Schedulers.newThread())
        .subscribeOn(Schedulers.newThread())
        .subscribe {
            val file = File(AppLifecyclesImpl.appContext.filesDir.absolutePath + "/config")
            if (!file.exists())
                file.mkdirs()
            val fos = FileOutputStream(File(file, it::class.java.simpleName))
            val objFos = ObjectOutputStream(fos)
            try {
                objFos.writeObject(it)
                objFos.close()
                fos.close()
            } catch (e: Exception) {
                try {
                    objFos.close()
                    fos.close()
                } catch (e: Exception) {
                }
            }
        }
}

/**
 * 删除序列化的文件
 */
fun <T : Serializable> clearConfig(clazz: Class<T>) {
    val file =
        File(AppLifecyclesImpl.appContext.filesDir.absolutePath + "/config/" + clazz.simpleName)
    if (file.exists()) {
        file.delete()
    }
}

/**
 * 删除序列化的文件
 */
fun clearConfigWithName(key: String) {
    val file = File(AppLifecyclesImpl.appContext.filesDir.absolutePath + "/config/" + key)
    if (file.exists()) {
        file.delete()
    }
}

/**
 * 把序列化的文件本地化，方便进程之间数据共享
 */
fun <V : Serializable> saveConfigWithName(key: String, value: V) {
    val file = File(AppLifecyclesImpl.appContext.filesDir.absolutePath + "/config")
    if (!file.exists())
        file.mkdirs()
    val fos = FileOutputStream(File(file, key))
    val objFos = ObjectOutputStream(fos)
    try {
        objFos.writeObject(value)
        objFos.close()
        fos.close()
    } catch (e: Exception) {
        try {
            objFos.close()
            fos.close()
        } catch (e: Exception) {
        }
    }
}

/**
 * 根据序列化类取出对象
 */
fun <V : Serializable> getConfig(value: Class<*>): LiveData<V> {
    val data = MutableLiveData<V>()
    val file =
        File(AppLifecyclesImpl.appContext.filesDir.absolutePath + "/config/${value.simpleName}")
    if (file.exists()) {
        Observable.just(file)
            .observeOn(Schedulers.newThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({
                val fis = FileInputStream(it)
                val objIns = ObjectInputStream(fis)
                try {
                    val obj = objIns.readObject() as V
                    fis.close()
                    objIns.close()
                    data.postValue(obj)
                } catch (e: Exception) {
                    try {
                        fis.close()
                        objIns.close()
                    } catch (e: Exception) {
                    }
                    data.postValue(null)
                }
            }, {
                data.postValue(null)
            })
    } else
        data.postValue(null)
    return data
}

/**
 * 根据序列化类取出对象
 */
fun <V : Serializable> getConfigRx(value: Class<*>): Observable<V> {
    val file =
        File(AppLifecyclesImpl.appContext.filesDir.absolutePath + "/config/${value.simpleName}")
    if (file.exists()) {
        return Observable.just(file).applySchedulers()
            .map {
                val fis = FileInputStream(it)
                val objIns = ObjectInputStream(fis)
                try {
                    var obj = objIns.readObject() as V
                    fis.close()
                    objIns.close()
                    obj
                } catch (e: Exception) {
                    try {
                        fis.close()
                        objIns.close()
                    } catch (e: Exception) {
                    }
                    null
                }
            }
    }
    return Observable.just(null)
}

/**
 * 根据序列化类取出对象
 */
fun <V : Serializable> getConfigWithName(key: String, value: Class<*>): V? {
    val file = File(AppLifecyclesImpl.appContext.filesDir.absolutePath + "/config/$key")
    if (file.exists()) {
        val fis = FileInputStream(file)
        val objIns = ObjectInputStream(fis)
        try {
            val obj = objIns.readObject() as V
            fis.close()
            objIns.close()
            return obj
        } catch (e: Exception) {
            try {
                fis.close()
                objIns.close()
            } catch (e: Exception) {
            }
        }
    }
    return null
}

/**
 * 获取顶部活动名称
 */
private fun topAppPackageName(context: Context): String {
    var topActivity = ""
    return try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            val m = context.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
            if (m != null) {
                val now = System.currentTimeMillis()
                //获取60秒之内的应用数据
                val stats = m.queryUsageStats(UsageStatsManager.INTERVAL_BEST, now - 60 * 1000, now)
                //取得最近运行的一个app，即当前运行的app
                if (stats != null && stats.isNotEmpty()) {
                    var j = 0
                    for (i in stats.indices) {
                        if (stats[i].lastTimeUsed > stats[j].lastTimeUsed) {
                            j = i
                        }
                    }
                    topActivity = stats[j].packageName
                    topActivity
                } else {
                    topActivity
                }
            } else {
                topActivity
            }
        } else {
            topActivity
        }
    } catch (e: Exception) {
        e.printStackTrace()
        topActivity
    }
}

/**
 * 获取栈定的activity 高版本已经失效
 */
fun getTopActivity(): String {
    val mActivityManager =
        AppLifecyclesImpl.appContext.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    var strName = ""
    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            strName = topAppPackageName(AppLifecyclesImpl.appContext)
        } else {
            if (mActivityManager != null) {
                strName = mActivityManager.getRunningTasks(1)[0].topActivity!!.className
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return strName
}

/**
 * md5工具类
 */
fun getStringMd5(plainText: String): String? {
    var md: MessageDigest? = null
    try {
        md = MessageDigest.getInstance("MD5")
        md.update(plainText.toByteArray())
    } catch (e: Exception) {
        return null
    }
    return encodeHex(md.digest())
}

/**
 * ASE加密相关
 */
private fun encodeHex(data: ByteArray?): String? {
    if (data == null) {
        return null
    }
    val HEXES = "0123456789abcdef"
    val len = data.size
    val hex = StringBuilder(len * 2)
    for (i in 0 until len) {
        hex.append(HEXES[data[i].toInt() and 0xF0 ushr 4])
        hex.append(HEXES[data[i].toInt() and 0x0F])
    }
    return hex.toString()
}

/**
 * 获取文件MD5值
 */
fun getFileMD5(path: String?): String? {
    var bi: BigInteger? = null
    println("begin:" + System.currentTimeMillis())
    try {
        val buffer = ByteArray(8192)
        var len = 0
        val md = MessageDigest.getInstance("MD5")
        val f = File(path)
        val fis = FileInputStream(f)
        println("length:" + f.length())
        while (fis.read(buffer).also { len = it } != -1) {
            md.update(buffer, 0, len)
        }
        fis.close()
        val b = md.digest()
        bi = BigInteger(1, b)
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    println("end:" + System.currentTimeMillis())
    return bi!!.toString(16)
}

/**
 * 手机号码验证
 */
fun isPhoneNumber(input: String): Boolean {// 判断手机号码是否规则
    val regex = "(1[0-9][0-9]|15[0-9]|18[0-9])\\d{8}"
    val p = Pattern.compile(regex);
    return p.matcher(input).matches()//如果不是号码，则返回false，是号码则返回true

}


/**
 * 获取mac地址（适配所有Android版本）
 * @return
 */
fun getMac(): String? {
    var mac: String? = ""
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        mac = getMacDefault(AppLifecyclesImpl.appContext)
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
        mac = getMacAddress()
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        mac = getMacFromHardware()
    }
    return mac
}

/**
 * Android 7.0之后获取Mac地址
 * 遍历循环所有的网络接口，找到接口是 wlan0
 * 必须的权限 <uses-permission android:name="android.permission.INTERNET"></uses-permission>
 * @return
 */
private fun getMacFromHardware(): String {
    try {
        val all = Collections.list(NetworkInterface.getNetworkInterfaces())
        for (nif in all) {
            if (!nif.name.equals("wlan0", ignoreCase = true))
                continue
            val macBytes = nif.hardwareAddress ?: return ""
            val res1 = StringBuilder()
            for (b in macBytes) {
                res1.append(String.format("%02X:", b))
            }
            if (!TextUtils.isEmpty(res1)) {
                res1.deleteCharAt(res1.length - 1)
            }
            return res1.toString()
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return ""
}

/**
 * Android 6.0-Android 7.0 获取mac地址
 */
private fun getMacAddress(): String {
    var macSerial: String = ""
    var str: String? = ""
    try {
        val pp = Runtime.getRuntime().exec("cat/sys/class/net/wlan0/address")
        val ir = InputStreamReader(pp.inputStream)
        val input = LineNumberReader(ir)
        while (null != str) {
            str = input.readLine()
            if (str != null) {
                macSerial = str.trim()//去空格
                break
            }
        }
    } catch (ex: IOException) {
        // 赋予默认值
        ex.printStackTrace()
    }
    return macSerial
}

/**
 * Android 6.0 之前（不包括6.0）获取mac地址
 * 必须的权限 <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"></uses-permission>
 * @param context * @return
 */
private fun getMacDefault(context: Context): String? {
    var mac: String? = ""
    if (context == null) {
        return mac
    }
    val wifi = context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    var info: WifiInfo? = null
    try {
        info = wifi.connectionInfo
    } catch (e: Exception) {
        e.printStackTrace()
    }

    if (info == null) {
        return null
    }
    mac = info.macAddress
    if (!TextUtils.isEmpty(mac)) {
        mac = mac.toUpperCase(Locale.ENGLISH)
    }
    return mac
}


class ParameterizedTypeImpl(var clazz: Class<*>) : ParameterizedType {

    override fun getActualTypeArguments(): Array<Type> {
        return arrayOf(clazz)
    }

    override fun getRawType(): Type {
        return MutableList::class.java
    }

    override fun getOwnerType(): Type? {
        return null
    }
}


/**
 * 隐藏键盘
 * 弹窗弹出的时候把键盘隐藏掉
 */
fun hideInputKeyboard(v: View) {
    val imm =
        AppLifecyclesImpl.appContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(v.windowToken, 0)
}

/**
 * 计算时间差  参数单位为秒  非毫秒  注意
 */
fun getPeriod(duration: Long): ArrayList<String> {
    val date = arrayListOf<String>()
    val minFlag = 60
    val hourFlag = minFlag * 60
    val dayFlag = hourFlag * 24
    val monthFlag = dayFlag * 30
    val yearFlag = monthFlag * 12

    val year = duration / yearFlag
    val yearMore = duration % yearFlag
    date.add("$year")

    val month = yearMore / monthFlag
    val monthMore = yearMore % monthFlag
    date.add("$month")

    val day = monthMore / dayFlag
    val dayMore = monthMore % dayFlag
    date.add("$day")

    val hour = dayMore / hourFlag
    val hourMore = dayMore % hourFlag
    date.add("$hour")

    val min = hourMore / minFlag
    val minMore = hourMore % minFlag
    date.add("$min")
    date.add("$minMore")
    return date
}

/**
 * 计算时间差天 时分秒
 */
fun getPeriodDHMS(duration: Long): ArrayList<String> {
    val date = arrayListOf<String>()
    val minFlag = 60
    val hourFlag = minFlag * 60
    val dayFlag = hourFlag * 24

    val day = duration / dayFlag
    val dayMore = duration % dayFlag
    date.add("${if (day > 9) day else "0$day"}")

    val hour = dayMore / hourFlag
    val hourMore = dayMore % hourFlag
    date.add("${if (hour > 9) hour else "0$hour"}")

    val min = hourMore / minFlag
    val minMore = hourMore % minFlag
    date.add("${if (min > 9) min else "0$min"}")
    date.add("${if (minMore > 9) minMore else "0$minMore"}")
    return date
}

/**
 * 安卓颜色值转字符串
 */
fun getHexString(color: Int): String {
    var s = "#"
    val colorStr =
        color and -0x1000000 or (color and 0x00ff0000) or (color and 0x0000ff00) or (color and 0x000000ff)
    s += Integer.toHexString(colorStr)
    return s
}

/**
 * 多值判空
 */
fun <T1 : Any, T2 : Any, R : Any> safeLet(p1: T1?, p2: T2?, block: (T1, T2) -> R?): R? {
    return if (p1 != null && p2 != null) block(p1, p2) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    block: (T1, T2, T3) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null) block(p1, p2, p3) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    block: (T1, T2, T3, T4) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null) block(p1, p2, p3, p4) else null
}

fun <T1 : Any, T2 : Any, T3 : Any, T4 : Any, T5 : Any, R : Any> safeLet(
    p1: T1?,
    p2: T2?,
    p3: T3?,
    p4: T4?,
    p5: T5?,
    block: (T1, T2, T3, T4, T5) -> R?
): R? {
    return if (p1 != null && p2 != null && p3 != null && p4 != null && p5 != null) block(
        p1,
        p2,
        p3,
        p4,
        p5
    ) else null
}

/**
 * 格式化时间
 */
fun formatTime(timeMillis: Long, formatString: String): String {
    val simpleDateFormat = SimpleDateFormat(formatString)
    return simpleDateFormat.format(timeMillis)
}

/**
 * 检测辅助功能是否开启<br>
 */
fun isAccessibilitySettingsOn(mContext: Context, clazz: Class<*>): Boolean {
    var accessibilityEnabled = 0
    val service = mContext.packageName + "/" + clazz.canonicalName
    try {
        accessibilityEnabled = Settings.Secure.getInt(
            mContext.applicationContext.contentResolver,
            Settings.Secure.ACCESSIBILITY_ENABLED
        )
    } catch (e: Settings.SettingNotFoundException) {
    }
    val mStringColonSplitter = TextUtils.SimpleStringSplitter(':')
    if (accessibilityEnabled == 1) {
        val settingValue = Settings.Secure.getString(
            mContext.applicationContext.contentResolver,
            Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES
        )
        if (settingValue != null) {
            mStringColonSplitter.setString(settingValue)
            while (mStringColonSplitter.hasNext()) {
                val accessibilityService = mStringColonSplitter.next()
                if (accessibilityService == service) {
                    return true
                }
            }
        }
    } else {

    }
    return false
}

/**
 * 判断是否是全面屏
 */
private var mHasCheckAllScreen = false
private var mIsAllScreenDevice = false
fun isAllScreenDevice(context: Context): Boolean {
    if (mHasCheckAllScreen) {
        return mIsAllScreenDevice
    }
    mHasCheckAllScreen = true
    mIsAllScreenDevice = false
    // 低于 API 21的，都不会是全面屏。。。
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
        return false
    }
    val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    if (windowManager != null) {
        val display = windowManager.getDefaultDisplay()
        val point = Point()
        display.getRealSize(point);
        var width = 0
        var height = 0
        if (point.x < point.y) {
            width = point.x
            height = point.y
        } else {
            width = point.y
            height = point.x
        }
        if (height / width >= 1.97f) {
            mIsAllScreenDevice = true
        }
    }
    return mIsAllScreenDevice
}