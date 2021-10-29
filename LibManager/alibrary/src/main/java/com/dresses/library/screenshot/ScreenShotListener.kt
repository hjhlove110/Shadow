package com.dresses.library.screenshot

import android.Manifest
import android.content.Context
import android.database.ContentObserver
import android.database.Cursor
import android.graphics.BitmapFactory
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.view.Display
import android.view.WindowManager
import androidx.fragment.app.FragmentActivity
import com.dresses.library.utils.PermissionUtil
import com.dresses.library.utils.getRealScreenSizeHeight
import com.jess.arms.utils.LogUtils
import java.util.*
import kotlin.collections.ArrayList

/**
 * 2020-01-15 17:28 Wednesday
 * Description: 截屏监听
 */
class ScreenShotListener constructor(context: Context?) {

    private val TAG = "ScreenShotListener"
    private var mContext: Context
    private var mScreenRealSize: Point? = null
    private val mHasCallbackPaths: ArrayList<String> = ArrayList()
    private var mListener: OnScreenShotListener? = null
    private var mStartListenTime: Long = 0
    /**
     * 内部存储器内容观察者
     */
    private var mInternalObserver: MediaContentObserver? = null
    /**
     * 外部存储器内容观察者
     */
    private var mExternalObserver: MediaContentObserver? = null
    /**
     * 运行在 UI 线程的 Handler, 用于运行监听器回调
     */
    private var mUiHandler = Handler(Looper.getMainLooper())

    init {
        LogUtils.debugInfo(TAG,"init")
        assertInMainThread()
        requireNotNull(context) { "The context must not be null." }
        mContext = context

        if (mScreenRealSize == null) {
            mScreenRealSize = getRealScreenSize()
            if (mScreenRealSize != null) {
                LogUtils.debugInfo(TAG,"Screen Real Size: " + mScreenRealSize!!.x + " * " + mScreenRealSize!!.y)
            } else {
                LogUtils.debugInfo(TAG,"Get screen real size failed.")
            }
        }
    }

    /**
     * 单例
     */

    /**
     * 开启监听
     */
    fun startListener(activity: FragmentActivity) {
        if (!PermissionUtil.isHavePermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE))return
        assertInMainThread()

        try {// 记录开始监听的时间戳
            mStartListenTime = System.currentTimeMillis()

            // 创建内容观察者
            mInternalObserver =
                MediaContentObserver(MediaStore.Images.Media.INTERNAL_CONTENT_URI, mUiHandler)
            mExternalObserver =
                MediaContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mUiHandler)

            // 注册内容观察者
            mContext.contentResolver.registerContentObserver(
                MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                false,
                mInternalObserver!!
            )
            mContext.contentResolver.registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                false,
                mExternalObserver!!
            )
        } catch (e: Exception) {
        }
    }

    fun stopListener(activity: FragmentActivity) {
        if (!PermissionUtil.isHavePermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE))return

        assertInMainThread()

        // 注销内容观察者
        try {
            if (mInternalObserver != null) {
                try {
                    mContext.contentResolver.unregisterContentObserver(mInternalObserver!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                mInternalObserver = null
            }
            if (mExternalObserver != null) {
                try {
                    mContext.contentResolver.unregisterContentObserver(mExternalObserver!!)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                mExternalObserver = null
            }

            // 清空数据
            mStartListenTime = 0
            mListener = null
        } catch (e: Exception) {
        }
    }

    /**
     * 处理媒体数据库的内容改变
     */
    fun handleMediaContentChange(contentUri: Uri) {
        var cursor: Cursor? = null
        try {
            cursor = mContext.contentResolver.query(
                contentUri,
                if (Build.VERSION.SDK_INT < 16) ScreenShotHelper.MEDIA_PROJECTIONS else ScreenShotHelper.MEDIA_PROJECTIONS_API_16,
                null, null,
                "${MediaStore.Images.ImageColumns.DATE_ADDED} desc limit 1"
            )

            if (cursor == null) {
                LogUtils.debugInfo(TAG,"Deviant logic.")
                return
            }

            if (!cursor.moveToFirst()) {
                LogUtils.debugInfo(TAG,"Cursor no data.")
                return
            }

            // 获取各列的索引
            val dataIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
            val dateTakenIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_TAKEN)
            var widthIndex = -1
            var heightIndex = -1
            if (Build.VERSION.SDK_INT >= 16) {
                widthIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.WIDTH)
                heightIndex = cursor.getColumnIndex(MediaStore.Images.ImageColumns.HEIGHT)
            }

            // 获取行数据
            val data = cursor.getString(dataIndex)
            val dateTaken = cursor.getLong(dateTakenIndex)
            var width = 0
            var height = 0
            if (widthIndex >= 0 && heightIndex >= 0) {
                width = cursor.getInt(widthIndex)
                height = cursor.getInt(heightIndex)
            } else {
                val size = getImageSize(data)
                width = size.x
                height = size.y
            }

            // 处理获取到的第一行数据
            handleMediaRowData(data, dateTaken, width, height)
        } catch (e: Exception) {
            LogUtils.debugInfo(TAG,"Exception: ${e.message}")
            e.printStackTrace()
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
    }

    private fun getImageSize(imagePath: String): Point {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(imagePath, options)
        return Point(options.outWidth, options.outHeight)
    }

    /**
     * 处理获取到的一行数据
     */
    private fun handleMediaRowData(data: String, dateTaken: Long, width: Int, height: Int) {
        if (checkScreenShot(data, dateTaken, width, height)) {
            LogUtils.debugInfo(TAG,"ScreenShot: path = $data; size = $width * $height; date = $dateTaken")
            if (mListener != null && !checkCallback(data)) {
                mListener!!.onScreenShot(data)
            }
        } else {
            // 如果在观察区间媒体数据库有数据改变，又不符合截屏规则，则输出到 log 待分析
            LogUtils.debugInfo(TAG,"Media content changed, but not screenshot: path = $data; size = $width * $height; date = $dateTaken")
        }
    }

    /**
     * 判断指定的数据行是否符合截屏条件
     */
    private fun checkScreenShot(data: String?, dateTaken: Long, width: Int, height: Int): Boolean {
        // 判断依据一: 时间判断
        // 如果加入数据库的时间在开始监听之前, 或者与当前时间相差大于10秒, 则认为当前没有截屏
        if (dateTaken < mStartListenTime || System.currentTimeMillis() - dateTaken > 10 * 1000) {
            return false
        }

        // 判断依据二: 尺寸判断
        if (mScreenRealSize != null) {
            // 如果图片尺寸超出屏幕, 则认为当前没有截屏
            if (!(width <= mScreenRealSize!!.x && height <= mScreenRealSize!!.y)
                || (height <= mScreenRealSize!!.x && width <= mScreenRealSize!!.y)
            ) {
                return false
            }
        }

        // 判断依据三: 路径判断
        if (data.isNullOrEmpty()) {
            return false
        }

        val lowerData = data.toLowerCase(Locale.getDefault())
        // 判断图片路径是否含有指定的关键字之一, 如果有, 则认为当前截屏了
        for (keyWork in ScreenShotHelper.KEYWORDS) {
            if (lowerData.contains(keyWork)) {
                return true
            }
        }
        return false
    }

    /**
     * 判断是否已回调过, 某些手机ROM截屏一次会发出多次内容改变的通知; <br></br>
     * 删除一个图片也会发通知, 同时防止删除图片时误将上一张符合截屏规则的图片当做是当前截屏.
     */
    private fun checkCallback(imagePath: String): Boolean {
        if (mHasCallbackPaths.contains(imagePath)) {
            LogUtils.debugInfo(TAG,"ScreenShot: imgPath has done; imagePath = $imagePath")
            return true
        }
        // 大概缓存15~20条记录便可
        if (mHasCallbackPaths.size >= 20) {
            for (i in 0..4) {
                mHasCallbackPaths.removeAt(0)
            }
        }
        mHasCallbackPaths.add(imagePath)
        return false
    }

    /**
     * 获取屏幕分辨率
     */
    private fun getRealScreenSize(): Point? {
        var screenSize: Point? = null
        getRealScreenSizeHeight()

        try {
            screenSize = Point()
            val windowManager = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                windowManager.defaultDisplay.getRealSize(screenSize)
            } else {
                windowManager.defaultDisplay.getSize(screenSize)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return screenSize
    }

    private fun assertInMainThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            val stackTrace = Thread.currentThread().stackTrace
            var methodMsg: String? = null
            if (stackTrace != null && stackTrace.size >= 4) {
                methodMsg = stackTrace[3].toString()
            }
            LogUtils.debugInfo(TAG,"Call the method must be in main thread: $methodMsg")
        }
    }

    /**
     * 媒体内容观察者
     */
    private inner class MediaContentObserver(var contentUri: Uri, handler: Handler) :
        ContentObserver(handler) {

        override fun onChange(selfChange: Boolean) {
            super.onChange(selfChange)
            handleMediaContentChange(contentUri)
        }
    }

    /**
     * 设置截屏监听器回调
     */
    fun setListener(listener: OnScreenShotListener) {
        this.mListener = listener
    }

    /**
     * 截屏监听接口
     */
    interface OnScreenShotListener {
        fun onScreenShot(picPath: String)
    }
}