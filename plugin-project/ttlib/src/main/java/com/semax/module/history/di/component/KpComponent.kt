package com.semax.module.history.di.component

import dagger.Component
import com.jess.arms.di.component.AppComponent

import com.semax.module.history.di.module.KpModule

import com.jess.arms.di.scope.ActivityScope
import com.semax.module.history.mvp.ui.activity.KpActivity

@ActivityScope
@Component(modules = arrayOf(KpModule::class), dependencies = arrayOf(AppComponent::class))
interface KpComponent {

    fun inject(activity: KpActivity)

}