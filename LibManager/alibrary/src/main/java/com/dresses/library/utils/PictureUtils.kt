package com.dresses.library.utils

import android.content.ContentValues
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.MediaScannerConnection
import android.provider.MediaStore
import com.dresses.library.AppLifecyclesImpl
import java.io.File

object PictureUtils {
    /**
     * 保存图片到相册(适配安卓11)
     */
    fun saveBitmapPhoto(imageFilePath: String) {
        if (File(imageFilePath).exists()) {
            val bm = BitmapFactory.decodeFile(imageFilePath)
            val resolver = AppLifecyclesImpl.appContext.contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, "cap_${System.currentTimeMillis()}")
                put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            }
            val uri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            if (uri != null) {
                resolver.openOutputStream(uri).use {
                    bm.compress(Bitmap.CompressFormat.JPEG, 100, it)
                }
            }
        }
    }

    /**
     * 刷新SD卡（用于刷新保存图片的地址）
     */
    private fun scanSingleFile(filePath: File) {
        MediaScannerConnection.scanFile(
            AppLifecyclesImpl.appContext,
            arrayOf(filePath.toString()),
            null
        ) { path, uri ->
            val cr = AppLifecyclesImpl.appContext.contentResolver
            var datemodified: Long = 0
            var dateadded: Long = 0
            val cursor = cr.query(uri, null, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                datemodified = cursor.getLong(
                    cursor
                        .getColumnIndex(MediaStore.MediaColumns.DATE_MODIFIED)
                )
                dateadded = cursor.getLong(
                    cursor
                        .getColumnIndex(MediaStore.MediaColumns.DATE_ADDED)
                )
                cursor.close()
            }
            val values = ContentValues()
            if (datemodified > 0
                && datemodified.toString().length > 10
            ) {
                values.put(
                    MediaStore.MediaColumns.DATE_MODIFIED,
                    datemodified / 1000
                )
            }
            if (dateadded > 0
                && dateadded.toString().length > 13
            ) {
                values.put(
                    MediaStore.MediaColumns.DATE_ADDED,
                    dateadded / 1000
                )
            }
            if (values.size() > 0) {
                cr.update(uri, values, null, null)
            }
        }
    }

}