package com.semax.module.history.mvp.model

import android.app.Application
import com.google.gson.Gson
import com.jess.arms.integration.IRepositoryManager
import com.jess.arms.mvp.BaseModel

import com.jess.arms.di.scope.ActivityScope
import javax.inject.Inject

import com.semax.module.history.mvp.contract.KpContract

@ActivityScope
class KpModel
@Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager),
    KpContract.Model {
    @Inject
    lateinit var mGson: Gson;

    @Inject
    lateinit var mApplication: Application;

    override fun onDestroy() {
        super.onDestroy();
    }
}