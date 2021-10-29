package com.dresses.library.base

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.dresses.library.R
import com.gyf.immersionbar.ImmersionBar
import com.jess.arms.base.BaseDialogFragment
import com.jess.arms.mvp.IPresenter

abstract class BaseFullScreenDialogFragment<P : IPresenter> :BaseDialogFragment<P>(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(
            DialogFragment.STYLE_NO_TITLE,
            R.style.custom_dialog_new
        )
        ImmersionBar.with(activity!!).statusBarDarkFont(true, 0.2f).init()
    }
}