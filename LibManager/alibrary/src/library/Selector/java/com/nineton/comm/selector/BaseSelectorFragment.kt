package com.nineton.comm.selector

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.dresses.library.base.BaseMvpFragment
import com.jess.arms.mvp.IPresenter

/**
 * @author HUAN
 * @date
 */
abstract class BaseSelectorFragment<P : IPresenter, M : BaseSelector> : BaseMvpFragment<P>() {
    open var mSelector: M? = null
    open fun setSelector(selector: M?): Fragment {
        mSelector = selector
        return this
    }
}