package com.dresses.library.utils

import com.dresses.library.AppLifecyclesImpl
import java.io.File

/**
 * @author HUAN
 * @date
 */
class CacheUtils {

    companion object {
        private fun getCacheSize(): Long {
            val rootFile = AppLifecyclesImpl.appContext.cacheDir ?: return 0L
            return getFileCacheFileSize(rootFile)
        }

        private fun getFileCacheFileSize(file: File): Long {
            val chileFiles = file.listFiles ()
            var size = 0L
            chileFiles?.let {
                for (file in it) {
                    if (file.isFile) {
                        size += file.length()
                    } else {
                        size += getFileCacheFileSize(file)
                    }
                }
            }
            return size
        }

        fun getCacheSizeString(): String {
            val size = getCacheSize()
            return "${String.format("%.2f", size.toFloat() / 1024 / 1024)}M"
        }
        fun clearCash() {
            val rootFile = AppLifecyclesImpl.appContext.cacheDir?:return
            clearCash(rootFile)
        }

        private fun clearCash(file: File) {
            val chileFiles = file.listFiles ()
            chileFiles?.let {
                for (file in it) {
                    if (file.isFile) {
                        file.delete()
                    } else {
                        clearCash(file)
                    }
                }
            }
        }
    }
}