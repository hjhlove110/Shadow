package com.dresses.library.screenshot

import android.provider.MediaStore
import android.util.Log

class ScreenShotHelper {
    companion object {

        const val TAG = "ScreenShotLog"

        /**
         * 读取媒体数据库时需要读取的列
         */
        val MEDIA_PROJECTIONS = arrayOf(
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_TAKEN
        )

        /**
         * 读取媒体数据库时需要读取的列，其中 width、height 字段在 API 16 之后才有
         */
        val MEDIA_PROJECTIONS_API_16 = arrayOf(
            MediaStore.Images.ImageColumns.DATA,
            MediaStore.Images.ImageColumns.DATE_TAKEN,
            MediaStore.Images.ImageColumns.WIDTH,
            MediaStore.Images.ImageColumns.HEIGHT
        )

        /**
         * 截屏路径判断的关键字
         */
        val KEYWORDS = arrayOf(
            "screenshot", "screen_shot", "screen-shot", "screen shot",
            "screencapture", "screen_capture", "screen-capture", "screen capture",
            "screencap", "screen_cap", "screen-cap", "screen cap"
        )


    }
}