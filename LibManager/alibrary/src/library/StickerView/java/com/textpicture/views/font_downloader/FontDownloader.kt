package com.textpicture.views.font_downloader

import android.os.Environment
import android.text.TextUtils
import androidx.annotation.Keep
import com.dresses.library.api.RepositoryProvider
import com.dresses.library.utils.get
import com.dresses.library.utils.showToastShort
import com.jess.arms.integration.EventBusManager
import com.jess.arms.utils.LogUtils
import okhttp3.Request
import java.io.Closeable
import java.io.File
import java.io.FileOutputStream

/**
 * @author HUAN
 * @date
 */
class FontDownloader {
    companion object {
        const val EVENT_BUS_PROGRESS_DOWNLOAD_FONT = "event_bus_progress_download_font"
        private const val BASE_FONT_URL = "base_font_url"
        private val TAG = FontDownloader::class.java.simpleName
        private val fontUrls by lazy {
            mutableMapOf<String, String>().also {
                it["keai"] = "${getBaseFontUrl()}keai.ttf"
                it["shouxie"] = "${getBaseFontUrl()}shouxie.ttf"
                it["manhua"] = "${getBaseFontUrl()}manhua.ttf"
                it["qianming"] = "${getBaseFontUrl()}qianming.ttf"
                it["xiawucha"] = "${getBaseFontUrl()}xiawucha.ttf"
                it["gangbi"] = "${getBaseFontUrl()}gangbi.ttf"
                it["make"] = "${getBaseFontUrl()}make.ttf"
                it["tese"] = "${getBaseFontUrl()}tese.ttf"
                it["kaile"] = "${getBaseFontUrl()}kaile.ttf"
                it["rixi"] = "${getBaseFontUrl()}rixi.ttf"
                it["haibao"] = "${getBaseFontUrl()}haibao.ttf"
                it["xianer"] = "${getBaseFontUrl()}xianer.ttf"
                it["cushu"] = "http://zone.liaoxingqiu.com/xiaojingling/xjlmh/ttf/cushu.ttf"
            }
        }

        fun getTypeFaceDownloadUrl(typefaceName: String): String {
            return fontUrls[typefaceName] ?: ""
        }

        fun getBaseFontUrl(): String {
            return get(BASE_FONT_URL, "http://zone.liaoxingqiu.com/xiaojingling/xjlmh/ttf/")
        }

        fun getPath(name: String): String? {
            val path =
                (Environment.getExternalStorageDirectory().toString() + File.separator
                        + "我的次元" + File.separator + "字体")
            val appDir = File(path)
            if (!appDir.exists()) {
                appDir.mkdirs()
            }
            //LogUtils.dd("字体路径: " + path);
            return path + File.separator + name + ".ttf"
        }

        fun deleteTypeFace(name: String) {
            val file = File(getPath(name!!))
            if (file.exists()) {
                file.delete()
            }
        }

        fun isExistFont(name: String?): Boolean {
            val file = File(getPath(name!!))
            // LogUtils.dd("字体文件 " + name + " 存在: " + file.exists());
            return file.exists()
        }

        val current = mutableListOf<String>()

        fun startDownload(name: String?, url: String?, callback: DownloadCallback?) {
//            if (current.contains(name)) {
//                return
//            }
//            if (TextUtils.isEmpty(url)) return
//            val path = getPath(name!!)
//            val client = RepositoryProvider.httpClient
//            val request = Request.Builder().url(url!!)
//                .build()
//
//            val file = File(path)
//            if (!file.parentFile.exists()) {
//                file.parentFile.mkdirs()
//
//                if (!file.parentFile.exists()){
//                    showToastShort("请授予撩星球文件权限")
//                    return
//                }
//            }
//            if (file.exists()) file.delete()
//            current.add(name)
//            val reposeBody = client.newCall(request).execute().body()
//            val contentLength = reposeBody?.contentLength() ?: 0
//            LogUtils.debugInfo(TAG, "contentLength = $contentLength")
//            val stream = reposeBody?.byteStream() ?: return
//            val fileOps = FileOutputStream(file)
//            try {
//                val byteArray = ByteArray(1024)
//                var readSize = 0
//                var progress = 0
//                while (stream.read(byteArray).also { readSize = it } != -1) {
//                    fileOps.write(byteArray, 0, readSize)
//                    progress += readSize
//                    val progressValue = progress * 100 / contentLength
//                    LogUtils.debugInfo(TAG, "progress = $progressValue")
//                    EventBusManager.getInstance().post(
//                        ProgressData(progressValue.toInt(), name),
//                        EVENT_BUS_PROGRESS_DOWNLOAD_FONT
//                    )
//                }
//                callback?.onSuccess(file)
//
//            } catch (e: Exception) {
//                callback?.onError(e)
//                deleteTypeFace(name)
//            } finally {
//                closeCloseable(stream)
//                closeCloseable(fileOps)
//                if (current.contains(name)) {
//                    current.remove(name)
//                }
//            }

        }

        private fun closeCloseable(closeable: Closeable) {
            try {
                closeable.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    interface DownloadCallback {
        fun onSuccess(result: File?)
        fun onError(ex: Throwable?)
    }


}
@Keep
data class ProgressData(
    var progress: Int = 0,
    var name: String = ""
)