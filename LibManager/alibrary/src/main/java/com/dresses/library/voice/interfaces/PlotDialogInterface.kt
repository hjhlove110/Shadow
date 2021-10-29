package com.dresses.library.voice.interfaces

/**
 * 对话内容 文字 与 对应的音频文件
 */
interface PlotDialogInterface {
    fun getVoiceUrl():String
    fun getVoiceText():String
    fun getMotionNo():Int
    fun getMenu():List<MenuInterface>

}