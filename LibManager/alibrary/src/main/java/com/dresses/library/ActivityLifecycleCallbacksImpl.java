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

import android.app.ActionBar;
import android.app.Activity;
import android.app.Application;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.dresses.library.arouter.EventTags;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.EventBusManager;

import timber.log.Timber;

/**
 * ================================================
 * 展示 {@link Application.ActivityLifecycleCallbacks} 的用法
 * <p>
 * Created by JessYan on 04/09/2017 17:14
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ActivityLifecycleCallbacksImpl implements Application.ActivityLifecycleCallbacks {

    private boolean isInBackstage = false;
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

        ActionBar bar = activity.getActionBar();
        if (bar != null){
            bar.hide();
        }
        Timber.i("%s - onActivityCreated", activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Timber.i("%s - onActivityStarted", activity);

    }

    @Override
    public void onActivityResumed(Activity activity) {
        Timber.i("%s - onActivityResumed", activity);
        if (isInBackstage && AppManager.getAppManager().getCurrentActivity()!= null){//后台回到app
            if (AppManager.getAppManager().getCurrentActivity().getClass().getSimpleName().contains("Attention")) {
                EventBusManager.getInstance().post(1,EventBusTags.ATTENTION_BG_MUSIC_STATUS);//后台回到应用 没在专注相关界面
            }else {
                EventBusManager.getInstance().post(1,EventBusTags.BG_MUSIC_STATUS_CHANGE_3);//后台回到应用
            }
        }

        isInBackstage = false;

    }

    @Override
    public void onActivityPaused(Activity activity) {
        Timber.i("%s - onActivityPaused", activity);
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Timber.i("%s - onActivityStopped", activity);
        if (AppManager.getAppManager().getCurrentActivity() == null){//进入后台
            isInBackstage = true;
            EventBusManager.getInstance().post(2, EventTags.EVENT_TAG_CAN_STOP_MUSIC);
            EventBusManager.getInstance().post(2,EventBusTags.BG_MUSIC_STATUS_CHANGE_3);
            EventBusManager.getInstance().post(2,EventBusTags.ATTENTION_BG_MUSIC_STATUS);
        }
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Timber.i("%s - onActivitySaveInstanceState", activity);
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Timber.i("%s - onActivityDestroyed", activity);
        //横竖屏切换或配置改变时, Activity 会被重新创建实例, 但 Bundle 中的基础数据会被保存下来,移除该数据是为了保证重新创建的实例可以正常工作
        activity.getIntent().removeExtra("isInitToolbar");
    }
}
