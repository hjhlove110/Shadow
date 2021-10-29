package com.semax.module.history.di.module

import com.jess.arms.di.scope.ActivityScope

import dagger.Module
import dagger.Provides

import com.semax.module.history.mvp.contract.KpContract
import com.semax.module.history.mvp.model.KpModel

@Module
//构建KpModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
class KpModule(private val view: KpContract.View) {
    @ActivityScope
    @Provides
    fun provideKpView(): KpContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    fun provideKpModel(model: KpModel): KpContract.Model {
        return model
    }
}