package com.smx.tankyou;

import android.content.Context;

import com.jess.arms.base.BaseApplication;
import com.tencent.shadow.sample.introduce_shadow_lib.InitApplication;

public class MyApplication extends BaseApplication {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        InitApplication.onApplicationCreate(this);
    }
}
