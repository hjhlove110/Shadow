package com.textpicture.views.utils

import android.graphics.Color
import android.graphics.Typeface
import android.text.TextUtils
import android.view.Gravity
import android.widget.FrameLayout
import com.dresses.library.utils.ThreadPool
import com.dresses.library.utils.dp2px
import com.google.gson.Gson
import com.textpicture.views.entity.TextShadowStyleInfo
import com.textpicture.views.font_downloader.FontDownloader
import com.textpicture.views.freetext.data.DrawData
import com.textpicture.views.freetext.view.SEditTextView
import java.io.File

/**
 * @author HUAN
 * @date
 */
class SEditTextUtils {

    companion object {
        /**
         * 设置投影的特效
         */
        fun setSpecialShadowStyle(textShadowStyleInfo: TextShadowStyleInfo, stext: SEditTextView) {
            stext.setData(createDefaultDrawData())
            stext.setShadowLayer(
                textShadowStyleInfo.radius, textShadowStyleInfo.offX,
                textShadowStyleInfo.offY, Color.parseColor(textShadowStyleInfo.shadowColor)
            )
            stext.setTextColor(Color.parseColor(textShadowStyleInfo.textColor))
        }

        /**
         * 设置荧光特效
         */
        fun setSpecialStyle(drawData: DrawData, stext: SEditTextView) {
            setTextColor(null, null, stext)
            stext.setData(drawData)
        }

        /**
         * 设置位置排版
         *
         * @param gravity 排版方式
         */
        fun setGravity(gravity: Int, stext: SEditTextView) {
            val layoutParams = FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
            )
            layoutParams.gravity = gravity or Gravity.CENTER_VERTICAL
            stext.layoutParams = layoutParams
            stext.gravity = gravity or Gravity.CENTER_VERTICAL
        }


        /**
         * 设置文本颜色
         *
         * @param textColor   颜色
         * @param shadowColor 边框颜色
         */
        fun setTextColor(textColor: String?, shadowColor: String?, stext: SEditTextView) {
            if (!TextUtils.isEmpty(textColor) && !TextUtils.isEmpty(shadowColor)) {
                stext.setData(createDefaultDrawData())
            }
            val colorInt: Int
            colorInt = try {
                Color.parseColor(if (TextUtils.isEmpty(textColor)) "#FFFFFF" else textColor)
            } catch (e: Exception) {
                e.printStackTrace()
                Color.WHITE
            }
            stext.setTextColor(colorInt)
            stext.setHintTextColor(colorInt)
            if (!TextUtils.isEmpty(shadowColor) && shadowColor != "#00000000") {
                val shadowColorInt: Int
                shadowColorInt = try {
                    Color.parseColor(shadowColor)
                } catch (e: Exception) {
                    e.printStackTrace()
                    Color.TRANSPARENT
                }
                val radius: Int = dp2px(3).toInt()
                stext.setShadowLayer(radius.toFloat(), 0f, 0f, shadowColorInt)
            } else {
                stext.setShadowLayer(0f, 0f, 0f, Color.TRANSPARENT)
            }
        }

        fun createDefaultDrawData(): DrawData {
            val defaultJsonStr =
                "{\"fontStyle\": \"Normal\",\"layers\": [ {\"degree\": 0.0,\"drawParam\": {},\"name\": \"1层-文\",\"offsetX\": 0.0,\"offsetY\": 0.0,\"paintParam\": {},\"scale\": 0.0, \"type\": 0}]}"
            return Gson().fromJson<DrawData>(defaultJsonStr, DrawData::class.java)
        }


        /**
         * 设置字体
         */
        fun setTypeface(
            typefaceName: String?,
            typefaceUrl: String?,
            text: SEditTextView?,
            callback: FontDownloader.DownloadCallback?
        ) {
            if (!TextUtils.isEmpty(typefaceName) && typefaceName != "默认体") {
                if (FontDownloader.isExistFont(typefaceName)) {
                    setTypeFace(typefaceName!!, text)
                    callback?.onSuccess(null)
                } else {
//                    ThreadPool.executorService.execute {
//                        FontDownloader.startDownload(typefaceName, typefaceUrl,
//                            object : FontDownloader.DownloadCallback {
//                                override fun onSuccess(result: File?) {
//                                    text?.postDelayed( {
//                                        setTypeFace(typefaceName!!, text)
//                                        callback?.onSuccess(result)
//                                    },1000)
//                                }
//
//                                override fun onError(ex: Throwable?) {
//                                    text?.post { callback?.onError(ex) }
//                                }
//                            })
//                    }

                }
            } else {
                text?.typeface = Typeface.DEFAULT
            }

        }

        private fun setTypeFace(typefaceName: String, text: SEditTextView?) {
            try {
                val typeface = Typeface.createFromFile(FontDownloader.getPath(typefaceName))
                text?.typeface = typeface
            } catch (e: Exception) {
//                FontDownloader.deleteTypeFace(typefaceName)
            }
        }


    }


}