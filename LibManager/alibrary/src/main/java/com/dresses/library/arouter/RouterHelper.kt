package com.dresses.library.arouter

import android.app.Activity
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.alibaba.android.arouter.launcher.ARouter
import com.jess.arms.base.BaseDialogFragment
import java.io.Serializable


object RouterHelper {

    private var lastPress = 0L
    private fun isRouterEnable(): Boolean {
        val current = System.currentTimeMillis()
        if (current - lastPress < 1000) {
            return false
        }
        lastPress = current
        return true
    }

    /**
     * 路由跳转
     */
    fun jump(path: String) {
        if (!isRouterEnable()) {
            return
        }
        ARouter.getInstance()
            .build(path)
            .navigation()
    }

    /**
     * 路由uri跳转
     */
    fun jumpUri(uri: Uri) {
        ARouter.getInstance()
            .build(uri)
            .navigation()
    }


    fun goVideoTable() {
        ARouter.getInstance().build(ARouterPath.ModeTree_MATE_MAIN).navigation()
    }

    fun goCollections() {
        ARouter.getInstance().build(ARouterPath.ModeTree_COLLECTION_MAIN).navigation()
    }

    fun goSearchMain(keyWords: String = "") {
        ARouter.getInstance()
            .build(ARouterPath.ModeTree_SEARCH_MAIN)
            .withString("key", keyWords)
            .navigation()
    }

    fun goAllActMain(
        type: Int = 0,
        genre: Int = 100,
        title: String = "",
        tlist_id: Int = 1000020,
        cover: String = ""
    ) {
        ARouter.getInstance()
            .build(ARouterPath.ModeTree_SEARCH_ACT_MAIN)
            .withInt("type", type)
            .withInt("tlist_id", tlist_id)
            .withString("title", title)
            .withString("cover", cover)
            .withInt("genre", genre)
            .navigation()
    }

    fun goRankActMain(
        g1: Int = 0,
        g2: Int = 0,
        title: String
    ) {
        ARouter.getInstance()
            .build(ARouterPath.ModeTree_RANK_MAIN)
            .withInt("g1", g1)
            .withInt("g2", g2)
            .withString("title", title)
            .navigation()
    }

    fun goSeasonMovieMain(title: String = "", id: Int = 0) {
        ARouter.getInstance()
            .build(ARouterPath.ModeTree_SEASON_MOVIE_MAIN)
            .withString("title", title)
            .withInt("id", id)
            .navigation()
    }

    /**
     * 默认单集：myflixer  剧集：tv_mflx
     */
    fun goPlayMain(
        name: String,
        id: String,
        playlist_key: String = "",
        localPath: String = ""
    ) {
        val mType = when (playlist_key) {
            null, "", "1" -> {
                "myfx"
            }
            "myfx", "tt_mflx" -> {
                playlist_key
            }
            else -> {
                "tt_mflx"
            }
        }
        ARouter.getInstance()
            .build(ARouterPath.ModeTree_PLAY_VIDEO)
            .withString("name", name)
            .withString("id", id)
            .withString("localPath", localPath)
            .withString("mType", mType)
            .withString(
                "playlist_key",
                if (playlist_key == "tt_mflx" || playlist_key == "myfx") "" else playlist_key
            )
            .navigation()
    }

    /**
     * 默认单集：myflixer  剧集：tv_mflx
     */
    fun goPlayCacheVideo(name: String, localPath: String = "") {
        ARouter.getInstance()
            .build(ARouterPath.ModeTree_Cache_PLAY)
            .withString("name", name)
            .withString("localPath", localPath)
            .navigation()
    }

    /**
     * activity startActivityForResult
     */
    fun jumpForBack(path: String, activity: Activity, requestCode: Int) {
        ARouter.getInstance()
            .build(path)
            .navigation(activity, requestCode)
    }


    /**
     * 传递Bundle
     */
    fun jumpWithBundle(path: String, params: Bundle) {
        if (!isRouterEnable()) {
            return
        }
        ARouter.getInstance()
            .build(path)
            .with(params)
            .navigation()
    }

    /**
     *传递对象
     */
    fun jumpWithObject(path: String, key: String, obj: Serializable) {
        ARouter.getInstance()
            .build(path)
            .withSerializable(key, obj)
            .navigation()
    }

    /**
     * 展示  过滤条件弹窗
     */
    fun showLotteryMuilt(
        fragmentManager: FragmentManager, poolId: Int

    ) {
        if (!isRouterEnable()) {
            return
        }
        val fragment = (ARouter.getInstance()
            .build(ARouterPath.ModeTree_MATE_MAIN)
            .navigation() as BaseDialogFragment<*>)
        fragment.setData(poolId)
        fragment.showDialog(fragmentManager)
    }
}