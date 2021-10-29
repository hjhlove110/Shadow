package com.dresses.library.arouter

object EventTags {

    const val EVENT_TAG_FINISH_ACTIVITY = "event_tag_finish_activity"//结束指定页面
    const val EVENT_TAG_NETWORK_STATE_CHANGE = "event_tag_network_state_change"//网络状态发生变化

    const val EVENT_TAG_NETWORK_STATE_WIFI = "event_tag_network_state_wifi"//网络状态发生变化
    const val EVENT_TAG_COINS_UPDATE = "event_tag_coins_update"//次元币变化

    const val EVENT_TAG_UPDATE_DIALOG = "event_tag_update_dialog"//首页弹窗更新

    // splash
    const val EVENT_TAG_SKIP_VERSION_UP = "event_tag_skip_version_up"//跳过版本更新
    const val EVENT_TAG_AGREE_CONFIRM = "event_tag_agree_confirm"//同意用户协议

    //==================== dress main start =============


    const val UPDATE_USER_INFO = "update_user_info"//更新用户信息
    const val EVENT_DRESSES_UP_DATE = "event_dresses_up_date"// 更新商城
    const val EVENT_DRESSES_CHANGE_DRESSES = "event_dresses_change_dresses"//用户更换装扮
    const val EVENT_DRESSES_CHANGE_DRESSES_SUCCESS = "event_dresses_change_dresses_success"//用户更换装扮
    const val EVENT_DRESSES_CHANGE_ROLE_SUCCESS = "event_dresses_change_role_success"//用户更换人设
    const val EVENT_DRESSES_CHANGE_ROLE = "event_dresses_change_role"//用户更换人设

    const val EVENT_DRESSES_SHOW_MALL = "event_dresses_show_mall"//显示商城

    const val UPDATE_NOTIFY_INFO = "update_notify_info"//更新提醒数据？


    const val UPDATE_HAIR_COLOR = "update_hair_color"//更换了头发颜色
    const val UPDATE_SHOW_GUIDE_MODE = "update_show_guide_mode"//更新引导时模型的大小

    const val UPDATE_LIVE2D_BG = "update_live2d_bg"//更新背景图
    const val EVENT_TAG_MUSIC_VOLUME = "event_tag_music_volume"//音乐音量
    const val EVENT_TAG_CHAR_VOLUME = "event_tag_char_volume"//人物音量

    const val EVENT_TAG_RELOAD_LIVE2D = "event_tag_reload_live2d"//重新加载线上模型

    const val EVENT_TAG_FINISH_LIVE2D = "event_tag_finish_live2d"//结束正主页正在运行的模型
    const val EVENT_TAG_RESTART_LIVE2D = "event_tag_restart_live2d"//重新启动主页模型

    const val EVENT_TAG_LIVE_MODEL_VIEW_STATE = "event_tag_live_model_view_state"//重新加载线上模型

    const val EVENT_TAG_SPLASH_DISMISS = "event_tag_splash_dismiss"//重新加载线上模型
    const val EVENT_TAG_TO_NORMAL = "event_tag_to_normal"//显示正常情况  1 正常模式  2 导航模式
    const val EVENT_TAG_CAN_STOP_MUSIC = "event_tag_can_stop_music"//不停止播放

    const val EVENT_TAG_ALERT_DIALOG_DISMISS = "event_tag_alert_dialog_dismiss"//提醒弹窗消失
    const val EVENT_TAG_UPDATE_DIAMOND = "event_tag_update_diamond"//换装更新钻石数量
    const val EVENT_TAG_ALERT_SHOW = "event_tag_alert_show"//弹出提醒
    const val EVENT_TAG_REQUEST_ACTIVITY = "event_tag_request_activity"//领取活动

    const val EVENT_TAG_BASE_MODEL_DOWNLOAD = "EVENT_TAG_BASE_MODEL_DOWNLOAD"//首次安装  基本模型下载进度时间

    const val UPDATE_LIVE2D_MODEL_IMM = "UPDATE_LIVE2D_MODEL_IMM"//立刻更新当前模型文件  可当这修复模式
    //==================== dress main  end =============

    //==================== dress edit  start =============

    const val EVENT_EDIT_UP_DATE_FRAMES = "event_edit_up_date_frames"
    //==================== dress edit  end =============


    //==================== attention start =============

    const val USER_ADD_TAG = "user_add_tag"
    const val USER_SCREEN_SWITCH = "user_screen_switch"
    const val USER_SWITCH_MODEL = "user_switch_model"
    const val USER_SWITCH_MODEL_MEMORY = "user_switch_model_memory"
    const val USER_SWITCH_MODEL_DRESS = "user_switch_model_dress"
    const val USER_ATTENTION_FINISH = "user_attention_finish"
    const val USER_MUSIC_SWITCH = "user_music_switch"
    const val USER_NOTIFY_HIDE = "user_notify_hide"
    const val USER_SHOW_UNZIP = "user_show_unzip"
    //==================== attention edit  end =============


    //==================== dress edit  start =============

    const val USER_LOG_OUT = "user_log_out"
    const val EVENT_TO_MAIN = "event_to_main"
    const val EVENT_FIX_MODEL = "event_fix_model"
    const val EVENT_FIX_MODEL_FINISH = "event_fix_model_finish"
    const val USER_INFO_DIALOG_DISMISS = "user_info_dialog_dismiss"
    //==================== dress edit  end =============


    //==================== share  start =============

    const val SHARE_EVENT = "share_event" //参数传1 会使得最初的分享界面finish
    //==================== share  end =============

    //==================== habit  start =============

    const val HABIT_CHANGE_EVENT = "habit_change_event" //更新习惯
    const val HABIT_LINK_ALERT = "habit_link_alert"//习惯发送通知，让提醒处理相关关联信息
    const val HABIT_LINK_ALERT_DELETE = "habit_link_alert_delete"//删除习惯
    //==================== habit  end =============

    //=============预览页面=============
    const val PREVIEW_MODEL_DRESS_UP = "preview_model_dress_up"//

    //=============sign 模块页面=============
    const val SIGN_DAILY_WELFARE_UPDATE = "SIGN_DAILY_WELFARE_UPDATE"//
    const val EVENT_GIFT_RECEIVED = "event_gift_received"// 获取了礼物

    //=============抽奖 模块页面=============
    const val EVENT_TAG_UPDATE_LOTTERY = "event_tag_update_lottery"//更新奖池
}