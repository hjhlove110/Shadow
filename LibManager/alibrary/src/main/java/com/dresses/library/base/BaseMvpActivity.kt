package com.dresses.library.base

import ToastUtils
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.dresses.library.R
import com.dresses.library.loadingpage.adapter.CustomHeaderAdapter
import com.dresses.library.loadingpage.adapter.EmptyAdapter
import com.dresses.library.loadingpage.adapter.ErrorAdapter
import com.dresses.library.loadingpage.adapter.LoadingAdapter
import com.dresses.library.widget.LoadingDialog
import com.dylanc.loadinghelper.LoadingHelper
import com.dylanc.loadinghelper.ViewType
import com.gyf.immersionbar.ImmersionBar
import com.jess.arms.base.BaseActivity
import com.jess.arms.mvp.IPresenter
import com.jess.arms.mvp.IView
import com.jess.arms.utils.LogUtils
import java.lang.reflect.Field
import java.lang.reflect.Method
import javax.inject.Inject

abstract class BaseMvpActivity<P : IPresenter> : BaseActivity<P>(), IView, View.OnClickListener {

    private lateinit var mActivity: AppCompatActivity


    @Inject
    @JvmField
    var emptyInject: EmptyInject? = null
//    private var loadingDialog: LoadingDialog? = null
//
//    private val loadingHelper by lazy {
//        LoadingHelper(this)
//    }
//
    private val headerBuilder by lazy {
        CustomHeaderAdapter.Builder()
    }
//
//    private val errorAdapter by lazy {
//        ErrorAdapter(this)
//    }
//    private val emptyAdapter by lazy {
//        EmptyAdapter(this)
//    }
    private var isUseHeader = false

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isSetStyle) {
            setTheme(R.style.AppThemeSplash)
        }else{
            setTheme(R.style.AppThemeNoAnim)

        }
        requestedOrientation = getOrientation()
        supportActionBar?.run {
            hide()
        }
        mActivity = this
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).init()
    }
    private var isSetStyle = true
    open fun isSetStyle (isSetStyle:Boolean){
        this.isSetStyle = isSetStyle
    }

    override fun getOrientation(): Int {
        return ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }

    override fun showMessage(message: String) {
        ToastUtils.showShortToast(message)
//        ArmsUtils.snackbarText(message)
    }

    override fun initData(savedInstanceState: Bundle?) {
//        if (isUseLoadingPage()) {
//            loadingHelper.register(ViewType.LOADING, LoadingAdapter())
//            loadingHelper.register(ViewType.ERROR, errorAdapter)
//            loadingHelper.register(ViewType.EMPTY, emptyAdapter)
//        }
        initTitle()
        setTitle()
        initDataContinue(savedInstanceState)
    }

    abstract fun initTitle()
    abstract fun initDataContinue(savedInstanceState: Bundle?)
    open fun isUseLoadingPage(): Boolean {
        return false
    }

    fun showLoadingPage() {
//        loadingHelper.showLoadingView()
    }

    fun showEmptyPage(emptyText: String? = "", emptyResId: Int = 0) {
//        if (emptyText?.isNotEmpty() == true) {
//            emptyAdapter.setEmptyText(emptyText)
//        }
//        if (emptyResId != 0) {
//            emptyAdapter.setEmptyRes(emptyResId)
//        }
//        loadingHelper.showEmptyView()
    }

    fun showErrorPage(errorText: String? = "", errorResId: Int = 0) {
//        if (errorText?.isNotEmpty() == true) {
//            errorAdapter.setErrorText(errorText)
//        }
//        if (errorResId != 0) {
//            errorAdapter.setErrorRes(errorResId)
//        }
//        loadingHelper.showErrorView()
    }

    fun showContentPage() {
//        loadingHelper.showContentView()
    }

    /**
     * 需要用到通用header 使用这个方法设置
     */
    fun getHeaderBuild(): CustomHeaderAdapter.Builder {
        isUseHeader = true
        headerBuilder.setListener(this)
        return headerBuilder
    }

    private fun setTitle() {
        if (isUseHeader) {
//            loadingHelper.register(ViewType.TITLE, headerBuilder.build())
//            loadingHelper.setDecorHeader(ViewType.TITLE)
        }
    }

     var lastPress = 0L
     fun isCurrentClickEnable(): Boolean {
        val current = System.currentTimeMillis()
        if (current - lastPress < 800) {
            return false
        }
         LogUtils.debugInfo("isCurrentClickEnable","${current - lastPress}")
        lastPress = current
        return true
    }

    override fun onClick(v: View?) {
        if (!isCurrentClickEnable()) return
        when (v?.id) {
            R.id.ivBaseBack -> {
                onLeftClick()
            }
            R.id.btn_first -> {
                onFirstClick()
            }
            R.id.btn_second -> {
                onSecondClick()
            }
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

    override fun showLoading() {
//        if (loadingDialog != null && loadingDialog!!.isShowing) {
//            return
//        }
//        loadingDialog = LoadingDialog(this)
//        loadingDialog?.show()
    }

    override fun hideLoading() {
//        try {
//            if (loadingDialog != null && loadingDialog!!.isShowing) {
//                loadingDialog?.dismiss()
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
    }

    override fun killMyself() {
        onBackPressed()
    }

    open fun getActivity(): FragmentActivity {
        return mActivity
    }


    /**
     *  解决 Can not perform this action after onSaveInstanceState at
     */

    open fun isInvokeSaveInstanceState():Boolean{
        return false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (isInvokeSaveInstanceState()){
            invokeFragmentManagerNoteStateNotSaved()
        }
    }

    private var noteStateNotSavedMethod: Method? = null
    private var fragmentMgr: Any? = null
    private val activityClassName =
        arrayOf("Activity", "FragmentActivity")
    //no view found for id xxx for fragment xxx
    private fun invokeFragmentManagerNoteStateNotSaved() {
        //java.lang.IllegalStateException: Can not perform this action after onSaveInstanceState
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return
        }
        try {
            if (noteStateNotSavedMethod != null && fragmentMgr != null) {
                noteStateNotSavedMethod?.invoke(fragmentMgr)
                return
            }
            var cls: Class<*>? = javaClass
            do {
                cls = cls!!.superclass
            } while (!(activityClassName[0] == cls!!.simpleName
                        || activityClassName[1] == cls.simpleName)
            )
            val fragmentMgrField: Field? = prepareField(cls, "mFragments")
            if (fragmentMgrField != null) {
                fragmentMgr = fragmentMgrField.get(this)
                noteStateNotSavedMethod = getDeclaredMethod(fragmentMgr!!, "noteStateNotSaved")
                if (noteStateNotSavedMethod != null) {
                    noteStateNotSavedMethod?.invoke(fragmentMgr)
                }
            }
        } catch (ex: Exception) {
        }
    }

    @Throws(NoSuchFieldException::class)
    private fun prepareField(c: Class<*>?, fieldName: String): Field? {
        var c = c
        while (c != null) {
            return try {
                val f: Field = c.getDeclaredField(fieldName)
                f.setAccessible(true)
                f
            } finally {
                c = c.superclass
            }
        }
        throw NoSuchFieldException()
    }

    private fun getDeclaredMethod(
        `object`: Any,
        methodName: String,
        vararg parameterTypes: Class<*>
    ): Method? {
        var method: Method? = null
        var clazz: Class<*>? = `object`.javaClass
        while (clazz != Any::class.java) {
            try {
                method = clazz!!.getDeclaredMethod(methodName, *parameterTypes)
                return method
            } catch (e: Exception) {
            }
            clazz = clazz!!.superclass
        }
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        hideLoading()
    }
}