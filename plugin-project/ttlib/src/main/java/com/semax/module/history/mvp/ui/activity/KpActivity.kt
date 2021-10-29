package com.semax.module.history.mvp.ui.activity

import android.os.Bundle
import com.dresses.library.base.BaseMvpActivity

import com.jess.arms.di.component.AppComponent
import com.semax.module.history.R


import com.semax.module.history.di.component.DaggerKpComponent
import com.semax.module.history.di.module.KpModule
import com.semax.module.history.mvp.contract.KpContract
import com.semax.module.history.mvp.presenter.KpPresenter


class KpActivity : BaseMvpActivity<KpPresenter>(), KpContract.View {

    override fun setupActivityComponent(appComponent: AppComponent) {
        DaggerKpComponent //如找不到该类,请编译一下项目
            .builder()
            .appComponent(appComponent)
            .kpModule(KpModule(this))
            .build()
            .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_kp //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    override fun initTitle() {
        //如果不需要header不作任何操作
        // getHeaderBuild().setTitle("Start activity").build()
    }

    override fun initDataContinue(savedInstanceState: Bundle?) {

    }

}