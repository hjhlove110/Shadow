package com.dresses.library.utils

import okhttp3.internal.Util
import java.util.concurrent.Executors
import java.util.concurrent.SynchronousQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

object ThreadPool {
    /**
     * 全局线程池
     */
    val executorService by lazy {
        ThreadPoolExecutor(
            0, Integer.MAX_VALUE, 60
            , TimeUnit.SECONDS, SynchronousQueue<Runnable>()
            , Util.threadFactory("Tools", false)
        )
    }

    val singleThreadExecutor by lazy {
        Executors.newSingleThreadExecutor()
    }
}