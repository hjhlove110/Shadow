package com.downloader.m3u8

import android.content.Context
import jaygoo.library.m3u8downloader.M3U8Downloader
import jaygoo.library.m3u8downloader.M3U8DownloaderConfig
import jaygoo.library.m3u8downloader.OnDeleteTaskListener
import jaygoo.library.m3u8downloader.OnM3U8DownloadListener

object M3u8DownloaderHelper {

    /**
     * 初始化M3U8下载器
     * @param filePath M3U8下载存储路径
     */
    fun init(context: Context, filePath: String) {
        M3U8DownloaderConfig.build(context)
            .setSaveDir(filePath)
            .setConnTimeout(10000)
            .setReadTimeout(10000)
            .setThreadCount(3)
            .setDebugMode(true)
    }

    fun downloadTask(url: String) {
        M3U8Downloader.getInstance().download(url)
    }

    fun setOnDownloaderListener(listener: OnM3U8DownloadListener){
        M3U8Downloader.getInstance().setOnM3U8DownloadListener(listener)
    }

    fun cancelTask(url: String) {
        M3U8Downloader.getInstance().cancel(url)
    }

    fun cancelAndDelete(url: String, listener: OnDeleteTaskListener) {
        M3U8Downloader.getInstance().cancelAndDelete(url, listener)
    }
}