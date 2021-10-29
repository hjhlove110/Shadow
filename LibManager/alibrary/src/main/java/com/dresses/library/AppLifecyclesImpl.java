/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dresses.library;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.dresses.library.utils.UMTools;
import com.google.android.gms.ads.MobileAds;
import com.jess.arms.base.delegate.AppLifecycles;
import com.tencent.mmkv.MMKV;

import io.reactivex.plugins.RxJavaPlugins;
import timber.log.Timber;

import static com.dresses.library.utils.ExtKt.logDebug;
import static com.dresses.library.utils.ExtKt.testImportSharedPreferences;

/**
 * ================================================
 * 展示 {@link AppLifecycles} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:12
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class AppLifecyclesImpl implements AppLifecycles {

    public static Context appContext;
    public static Application mApplication;

    @Override
    public void attachBaseContext(@NonNull Context base) {
        MultiDex.install(base);  //分包
    }

    @Override
    public void onCreate(@NonNull Application application) {
        appContext = application;
        mApplication = application;
        MMKV.initialize(application);
        testImportSharedPreferences();
        //UM统计初始化
        UMTools.INSTANCE.preInit(application, null);

        initARouter(application);

        if (BuildConfig.DEBUG) {//Timber初始化
            Timber.plant(new Timber.DebugTree());
        }

        RxJavaPlugins.setErrorHandler(throwable -> {
            //把rxjava的异常吃掉
        });

        MobileAds.initialize(application.getBaseContext(), initializationStatus ->
                logDebug("===============>ads初始化完成"));
        initFaceBook();
    }

    private void initFaceBook() {

    }

    private String getProcessName(Context context) {
        if (context == null) return null;
        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == android.os.Process.myPid()) {
                return processInfo.processName;
            }
        }
        return null;
    }

    /**
     * 初始化路由
     *
     * @param application
     */
    private void initARouter(@NonNull Application application) {
        if (BuildConfig.DEBUG) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application); // 尽可能早，推荐在Application中初始化
    }

    @Override
    public void onTerminate(@NonNull Application application) {

    }
}
