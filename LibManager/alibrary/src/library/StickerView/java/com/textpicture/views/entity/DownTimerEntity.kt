package com.textpicture.views.entity

import java.io.Serializable

/**
 * @author: zzs
 * @date: 2019/4/25
 */
class DownTimerEntity : Serializable {
    /**
     * 计时器模板ID
     */
    var timerTemplateId = 0
    //背景颜色
    var bgColor: String? = null
        get() = if (field == null) "" else field
    //标题字体大小
    var titleTextSize = 0f
    //时间字体大小
    var timeTextSize = 0f
    //文字字体
    var textTypeface: String? = null
        get() = if (field == null) "" else field
    //文字颜色
    var textColor: String? = null
        get() = if (field == null) "" else field
    //文字荧光颜色
    var textShaderColor: String? = null
        get() = if (field == null) "" else field
    //文字内容
    var titleContent: String? = null
        get() = if (field == null) "" else field
    //时间
    var time: Long = 0
    //颜色类型
    var colorType = 0
    var colorPosition = 0
    var typeFacePosition = 0
    /**
     * 第一个字体
     */
    var font1: String? = null
        get() = if (field == null) "" else field
    /**
     * 第二个字体
     */
    var font2: String? = null
        get() = if (field == null) "" else field
    /**
     * 第三个字体
     */
    var font3: String? = null
        get() = if (field == null) "" else field
    /**
     * 分割线资源名
     */
    var dividerResName: String? = null
        get() = if (field == null) "" else field

    companion object {
        private const val serialVersionUID = 4496877068148959227L
    }
}
