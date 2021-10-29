package com.dresses.library.arouter.provider

import android.content.Context
import android.os.Parcelable
import com.alibaba.android.arouter.facade.template.IProvider
import io.reactivex.Observable

/**
 * @author: zzs
 * @date: 2020/10/28
 */
public interface DressProvider : IProvider {
    fun getRoleName():String
    fun getModelFileName():String
    fun getModelSex():Int
    fun getModelBg():String

    fun downloadVoice(url:String):Observable<String>

    fun setWallVoiceOpen(isOpen:Boolean)
    fun getWallVoiceOpen():Boolean
    fun getClothes():List<Parcelable>
    fun getModelViewStyle(): Boolean
}
