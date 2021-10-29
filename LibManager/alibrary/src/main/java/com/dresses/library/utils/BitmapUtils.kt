package com.dresses.library.utils

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PaintFlagsDrawFilter
import android.view.View
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException


object BitmapUtils {
    fun saveImageToCustomDirectory(
        context: Activity,
        bitmapType: String,
        bmp: Bitmap,
        bitmpName: String
    ): String? {
        var path = AndroidQFileHelper.getDefaultPath() + bitmapType
        val appDir = File(path)
        if (!appDir.exists()) {
            appDir.mkdirs()
        }
        val fileName = "$bitmpName.jpg"
        val file = File(appDir, fileName)
        if (!file.parentFile.exists()) {
            file.parentFile.mkdirs()
        }
        if (!file.exists()) {
            file.createNewFile()
        }
        try {
            val fos = FileOutputStream(file)
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos)
            fos.flush()
            fos.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
            file.delete()
            return ""
        } catch (e: IOException) {
            e.printStackTrace()
            file.delete()
            return ""
        }
        SingleMediaScanner(context, file, null).scanFile()
        if (file.exists()) {
            path = file.path
        }
        return path
    }

    fun loadBitmapFromView(v: View): Bitmap {
        val screenshot = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(screenshot)
//        canvas.drawFilter = PaintFlagsDrawFilter(
//            0,
//            Paint.ANTI_ALIAS_FLAG
//        )
        canvas.translate(-v.scrollX.toFloat(), -v.scrollY.toFloat())
        v.draw(canvas)// 将 view 画到画布上
        return screenshot
    }
}