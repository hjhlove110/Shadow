package com.dresses.library.api

import ToastUtils
import com.dresses.library.AppLifecyclesImpl
import com.dresses.library.ResponseErrorListenerImpl
import me.jessyan.rxerrorhandler.core.RxErrorHandler
import me.jessyan.rxerrorhandler.handler.ErrorHandleSubscriber

object RepositoryProvider {
    val errorHandler: RxErrorHandler by lazy {
        RxErrorHandler.builder()
            .with(AppLifecyclesImpl.appContext)
            .responseErrorListener(ResponseErrorListenerImpl()).build()

    }
}

abstract class CommHandleSubscriber<T>(errorHandler: RxErrorHandler = RepositoryProvider.errorHandler) :
    ErrorHandleSubscriber<BaseResponse<T>>(errorHandler) {

    override fun onNext(t: BaseResponse<T>) {
        if (t?.status == 200) {
            onResult(t.data)
        } else {
            onRspError(t?.status ?: 0, t?.msg ?: "", true)
        }
    }

    abstract fun onResult(data: T?)
    open fun onRspError(code: Int, msg: String, showToast: Boolean) {
        if (showToast) {
            ToastUtils.showShortToast(msg)
        }
    }

}

