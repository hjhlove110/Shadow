package com.dresses.library.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dresses.library.R
import com.dresses.library.loadingpage.adapter.EmptyAdapter
import com.dresses.library.loadingpage.adapter.ErrorAdapter
import com.dresses.library.loadingpage.adapter.LoadingAdapter
import com.dresses.library.widget.LoadingDialog
import com.dylanc.loadinghelper.LoadingHelper
import com.dylanc.loadinghelper.ViewType
import com.jess.arms.base.BaseFragment
import com.jess.arms.mvp.IPresenter
import com.jess.arms.mvp.IView
import com.jess.arms.utils.ArmsUtils
import javax.inject.Inject

abstract class BaseMvpFragment<P : IPresenter> : BaseFragment<P>(), IView, View.OnClickListener {
    @Inject
    @JvmField
    var emptyInject: EmptyInject? = null
    private var loadingDialog: LoadingDialog? = null
    private val errorAdapter by lazy {
        ErrorAdapter(this)
    }
    private val emptyAdapter by lazy {
        EmptyAdapter(this)
    }

    private var loadingHelper: LoadingHelper? = null
    override fun showLoading() {
        hideLoading()
        loadingDialog = LoadingDialog(context!!)
        loadingDialog?.show()
    }

    override fun hideLoading() {
        try {
            if (loadingDialog != null && loadingDialog!!.isShowing) {
                loadingDialog?.dismiss()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.vCLEmpty, R.id.vCLError -> {
                onReload()
            }
            else -> {
                onViewClick(v)
            }
        }
    }

    open fun onViewClick(v: View?) {

    }

    open fun onFirstClick() {}
    open fun onReload() {}
    open fun onSecondClick() {}
    open fun onLeftClick() {}

    abstract fun initView()

    private var rootView: View? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (rootView == null) {
            rootView = if (isUseLoadingPage()) {
                loadingHelper = LoadingHelper(initView(inflater, container, savedInstanceState))
                loadingHelper?.register(ViewType.LOADING, LoadingAdapter())
                loadingHelper?.register(ViewType.ERROR, errorAdapter)
                loadingHelper?.register(ViewType.EMPTY, emptyAdapter)
                loadingHelper!!.decorView
            } else {
                super.onCreateView(inflater, container, savedInstanceState)

            }
        }
        hasCreateView = true
        if (userVisibleHint) {
            dispatchFragmentVisibleChange(true)//viewpager????????????fragment???????????????
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    open fun isUseLoadingPage(): Boolean {
        return true
    }

    fun showLoadingPage() {
        loadingHelper?.showLoadingView()
    }

    fun showEmptyPage(emptyText: String? = "", emptyResId: Int = 0) {
        if (emptyText?.isNotEmpty() == true) {
            emptyAdapter.setEmptyText(emptyText)
        }
        if (emptyResId != 0) {
            emptyAdapter.setEmptyRes(emptyResId)
        }
        loadingHelper?.showEmptyView()
    }

    fun showErrorPage(errorText: String? = "", errorResId: Int = 0) {
        if (errorText?.isNotEmpty() == true) {
            errorAdapter.setErrorText(errorText)
        }
        if (errorResId != 0) {
            errorAdapter.setErrorRes(errorResId)
        }
        loadingHelper?.showErrorView()
    }

    fun showContentPage() {
        loadingHelper?.showContentView()
    }

    override fun initData(savedInstanceState: Bundle?) {
    }

    override fun setData(data: Any?) {
    }

    /**
     * ?????????????????????
     */
    private var mIsVisibleToUser = false

    /**
     * rootView?????????????????????????????????????????????rootView?????????????????????
     */
    private var hasCreateView = false

    /**
     * ??????Fragment??????????????????????????????????????????ViewPager?????????????????????????????????????????????
     */
    var isFragmentVisible = false

    /**
     * ?????????????????????????????????
     */
    var hasFetchData = false
    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (hasCreateView) {
            dispatchFragmentVisibleChange(isVisibleToUser)
        }

    }

    open fun dispatchFragmentVisibleChange(isVisible: Boolean) {
        if (!mIsVisibleToUser && isVisible) {// ??????????????????
            onVisibleLoadData()
        } else if (mIsVisibleToUser && !isVisible) {//??????????????????
            onInvisibleCancelAll()
        }
        mIsVisibleToUser = isVisible
    }

    open fun onVisibleLoadData() {

    }

    open fun onInvisibleCancelAll() {

    }

    override fun onPause() {
        super.onPause()
        if (mIsVisibleToUser && userVisibleHint){
            dispatchFragmentVisibleChange(false)
        }
    }

    override fun onResume() {
        super.onResume()
        if (!mIsVisibleToUser && userVisibleHint){
            dispatchFragmentVisibleChange(true)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        hasCreateView = false
        mIsVisibleToUser = false
    }
}