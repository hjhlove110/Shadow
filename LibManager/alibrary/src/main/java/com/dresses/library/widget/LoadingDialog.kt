package com.dresses.library.widget

import android.animation.ObjectAnimator
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import com.dresses.library.R
import kotlinx.android.synthetic.main.loding_dialog.*


class LoadingDialog(context: Context) : Dialog(context, R.style.CustomAlertDialogStyle) {
    init {
        setCancelable(true)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.loding_dialog)

        //创建一个AnimationDrawable
        //属性动画初始化
        val loadingAnim: ObjectAnimator = ObjectAnimator.ofFloat(animation_view, "rotation", 0f, 359f)

        val interpolator = LinearInterpolator()
        //无限循环
        loadingAnim.repeatCount = -1
        //动画执行时间
        loadingAnim.duration = 1800
        //线性插值器
        loadingAnim.interpolator = interpolator

        loadingAnim.start()
    }

    override fun dismiss() {
        super.dismiss()
        animation_view.clearAnimation()
        animation_view.clearAnimation()
    }

}