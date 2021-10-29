package com.dresses.library.utils

import android.content.Context
import android.content.Intent
import android.content.pm.ShortcutInfo
import android.content.pm.ShortcutManager
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.DrawableRes
import com.dresses.library.AppLifecyclesImpl

class ShortcutUtils {
    companion object {
        const val SHORT_CUT_ID_1 = "SHORT_CUT_ID_1"
        const val SHORT_CUT_ID_2 = "SHORT_CUT_ID_2"
        const val SHORT_CUT_ID_3 = "SHORT_CUT_ID_3"

        const val SHORT_CUT_ID_4 = "SHORT_CUT_ID_4"
        const val SHORT_CUT_ID_5 = "SHORT_CUT_ID_5"
        const val SHORT_CUT_ID_6 = "SHORT_CUT_ID_6"

        @JvmStatic
        fun setShortCutMenu(context: Context, shortcutInfoList: MutableList<ShortcutInfo?>) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
                val shortcutManager = context.getSystemService(ShortcutManager::class.java)
                shortcutManager.dynamicShortcuts = shortcutInfoList
            }
        }

        @JvmStatic
        fun getShortcutInfo(
            shortCutId: String,
            intent: Intent,
            content: String,
            @DrawableRes resId: Int
        ): ShortcutInfo? {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1) return null
            intent.action = Intent.ACTION_VIEW
            intent.putExtra("url", "https://support.qq.com/products/301176")
            intent.putExtra("userInfo", true)
            intent.putExtra("title", "意见反馈")
            return ShortcutInfo.Builder(AppLifecyclesImpl.appContext, shortCutId)
                .setIntent(intent)
                .setShortLabel(content)
                .setLongLabel(content)
                .setIcon(Icon.createWithResource(AppLifecyclesImpl.appContext, resId))
                .build()
        }
    }
}