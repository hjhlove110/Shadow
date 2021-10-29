package com.nineton.comm.selector

import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import com.dresses.library.R
import com.dresses.library.utils.ViewAnimationUtils


abstract class BaseSelector(val activity: FragmentActivity?, val isBgClickable: Boolean = true) {

    open val parentView by lazy {
        activity?.layoutInflater?.inflate(
            getLayoutId(),
            null,
            false
        ) as ViewGroup
    }

    private var view:ViewGroup? = null
            protected var isAddView = false
    private var isShow = false

    fun showView() {
        addView()
        if (!isShow) {
            ViewAnimationUtils.runViewDownToHideAnimator(parentView, true, View.GONE)
            onViewShow()
        }
        isShow = true
    }

    fun addView() {
        if (!isAddView) {
            parentView.visibility = View.GONE
            val contentView =
                activity?.findViewById<FrameLayout>(android.R.id.content)?.getChildAt(0) as ViewGroup

            view =
                if (contentView is ConstraintLayout) contentView else contentView.getChildAt(1) as ViewGroup// 加了一个header
            val params =
                ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.MATCH_PARENT,
                    ConstraintLayout.LayoutParams.MATCH_PARENT
                )
            params.startToStart = R.id.parent
            params.endToEnd = R.id.parent
            params.bottomToBottom = R.id.parent
            params.topToTop = R.id.parent
            parentView.layoutParams = params
            view?.post {
                if (view?.indexOfChild(parentView) == -1)
                    view?.addView(parentView)
            }
            parentView.post {
                initView()
            }
            isAddView = true
            if (isBgClickable) {
                parentView.setOnClickListener {
                    hideView()
                }
            }
        }
    }

    fun isShow(): Boolean {
        return isShow
    }

    fun hideView() {
        if (!isAddView || !isShow) return
        ViewAnimationUtils.runViewDownToHideAnimator(parentView, false, View.GONE)
        isShow = false
        onViewHide()
    }

    open fun onViewHide() {}
    open fun onViewShow() {}
    open fun initView() {}
    open fun initViewLater() {}

    abstract fun getLayoutId(): Int

    protected fun <V : View> findViewById(viewId: Int): V {
        return parentView.findViewById(viewId) as V
    }

}