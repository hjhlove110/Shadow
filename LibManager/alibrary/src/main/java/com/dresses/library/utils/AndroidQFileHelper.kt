package com.dresses.library.utils

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.StatFs
import android.text.format.Formatter
import android.util.Log
import androidx.core.content.FileProvider
import com.dresses.library.AppLifecyclesImpl
import java.io.*
import java.io.File.separator


object AndroidQFileHelper {

    const val TAG = "AndroidQFileHelper"
    /**
     * 兼容android10的文件获取方式
     */
    private fun getSdcardFilePath(): String {
        return if (hasSDCard())
            Environment.getExternalStorageDirectory().absolutePath
        else
            AppLifecyclesImpl.appContext.filesDir.absolutePath
    }

    fun getDefaultPath(outSide: Boolean = true): String {
        return if (outSide) {
            getSdcardFilePath() + separator + "换装物语" + separator
        } else {
            AppLifecyclesImpl.appContext.filesDir.absolutePath
        }
    }

    /**
     * 获取文件路径
     *
     * @return 路径
     */
    fun getDCIMDir(): File? {
        val dir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
                .absolutePath)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        return dir
    }

    /**
     * 是否存在SD卡
     *
     * @return
     */
    fun hasSDCard(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    /**
     * 根据路径获取内存状态
     *
     * @param context 上下文
     * @param path    磁盘路径
     * @return
     */
    fun getMemoryInfo(context: Context, path: File = File(getDefaultPath())): String { // 获得一个磁盘状态对象
        val stat = StatFs(path.path)
        val blockSize = stat.blockSize.toLong() // 获得一个扇区的大小
        val totalBlocks = stat.blockCount.toLong() // 获得扇区的总数
        val availableBlocks = stat.availableBlocks.toLong() // 获得可用的扇区数量
        // 总空间
        val totalMemory = Formatter.formatFileSize(context, totalBlocks * blockSize)
        // 可用空间
        val availableMemory = Formatter.formatFileSize(context, availableBlocks * blockSize)
        return "可用空间: $availableMemory/总空间: $totalMemory"
    }


    /**
     * 转换文件大小
     *
     * @param context 上下文
     * @param fileS   目标文件
     * @return
     */
    fun formatFileSize(context: Context, fileS: Long): String {
        return Formatter.formatFileSize(context, fileS)
    }

    /**
     * 获取指定文件大小
     */
    fun getFileSize(file: File): Long {
        var size: Long = 0
        if (file.exists()) {
            var fis: FileInputStream? = null
            try {
                fis = FileInputStream(file)
                size = fis.available().toLong()
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            Log.e(TAG,"获取文件大小,文件不存在!")
        }
        return size
    }

    /**
     * 删除文件夹
     */
    fun deleteFile(filePath: String): Boolean {
        val file = File(filePath)
        return if (file.exists()) {
            if (file.isFile) {
                file.delete()
            } else {
                val filePaths = file.list()
                for (path in filePaths) {
                    deleteFile(filePath + separator + path)
                }
                file.delete()
            }
        } else
            true
    }

    /**
     * 文件拷贝
     *
     * @param from
     * @param to
     * @return
     */
    @Throws(FileNotFoundException::class, IOException::class, Exception::class)
    fun copyFile(from: String, to: String): Boolean {
        var result = false
        val size = 1024
        var `in`: FileInputStream? = null
        var out: FileOutputStream? = null
        try {
            `in` = FileInputStream(from)
            out = FileOutputStream(to)
            val buffer = ByteArray(size)
            var bytesRead = -1
            while (`in`.read(buffer).also { bytesRead = it } != -1) {
                out.write(buffer, 0, bytesRead)
            }
            out.flush()
            result = true
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            try {
                `in`?.close()
            } catch (e: IOException) {
            }
            try {
                out?.close()
            } catch (e: IOException) {
            }
        }
        return result
    }

    /**
     * 文件转Uri
     */
    fun getUriFromFile(context: Context, srcFile: File): Uri {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", srcFile)
        } else {
            Uri.fromFile(srcFile)
        }
    }


}