package com.dresses.library.interceptor

import com.google.gson.Gson
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import kotlin.collections.set

/**
 * Author:Ljb
 * Time:2018/8/9
 * There is a lot of misery in life
 **/
class AddGlobalParamInterceptor : Interceptor {
    private val gson = Gson()
    override fun intercept(chain: Interceptor.Chain): Response {
        //需要加签的公共参数
        val paramMap = HttpConfig.getParam()?.toMutableMap()

        val oldRequest = chain.request()

        //添加请求时间轴
        paramMap?.let {
            it["token"] = System.currentTimeMillis().toString()
        }
        val requestParams = hashMapOf<String, String?>()
        if (oldRequest.method() == "GET") {
            //获取Get请求中的参数
            val urlBuilder = oldRequest.url()
            val keys = urlBuilder.queryParameterNames()
            for (key in keys) {
                requestParams[key] = urlBuilder.queryParameter(key)
            }
            return try {
                chain.proceed(oldRequest)
            } catch (e: Exception) {
                chain.proceed(oldRequest)
            }
        } else {
            // 构造新的请求表单
            val builder = FormBody.Builder()
            oldRequest.body()?.let {
                if (it is FormBody) {
                    for (i in 0 until it.size()) {
                        builder.add(it.encodedName(i), it.encodedValue(i))
                    }
                } else {
                    val buffer = Buffer()
                    it.writeTo(buffer)
                    val requestBody = buffer.readByteString().utf8()
                    val map = gson.fromJson(requestBody, Map::class.java)
                    map?.map { itx ->
                        //将以前的参数添加
                        builder.add(itx.key.toString(), itx.value.toString())
                    }
                }
            }
            //追加新的参数
            for (key in paramMap!!.keys) {
                builder.add(key, paramMap[key])
            }
            val newRequest = oldRequest.newBuilder().post(builder.build()).build()
            return try {
                chain.proceed(newRequest)
            } catch (e: Exception) {
                chain.proceed(oldRequest)
            }
        }
    }

}