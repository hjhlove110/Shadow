package com.dresses.library.api

/**
 * @param status 请求状态码
 * @param msg 请求返回信息 可能为空
 * @param data 返回数据  可能为list 也可能为空
 */
data class BaseResponse<T>(val status: Int, val data: T, val msg: String)

data class BaseListBean<T>(
    val list: MutableList<T>
)
