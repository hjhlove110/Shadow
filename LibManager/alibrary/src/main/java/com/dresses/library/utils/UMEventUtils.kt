package com.dresses.library.utils

import com.dresses.library.AppLifecyclesImpl

object UMEventUtils {


    fun onEvent(eventId: String, event: HashMap<String, String> = hashMapOf()) {
        //
        if (event.isEmpty()) {
        } else {
        }
    }


    const val EVENT_ID_ZHUANGBAN = "zhuangban"//点击装扮
    const val EVENT_ID_ZHAOXIANG = "zhaoxiang"//点击照相
    const val EVENT_ID_SHEZHI = "shezhi"//设置
    const val EVENT_ID_DONGTAIBIZHI = "dongtaibizhi"//动态壁纸
    const val EVENT_ID_MOFAKAI = "mofaka"//魔法卡
    const val EVENT_ID_FULI = "fuli"//每日福利
    const val EVENT_ID_BAOCUN = "baocun"//保存装扮
    const val EVENT_ID_YIFUXUANXIANG = "yifuxuanxiang"//衣服类型选项点击  + 通过参数区分 套装/发型/妆容/上装等...点击量

    const val EVENT_ID_TIXING01 = "tixing01"//通过参数区分 新建提醒/管理所有提醒/关闭所有提醒
    const val EVENT_ID_TIXING02 = "tixing02"//通过参数区分 起床/睡觉/吃饭...等选择情况
    const val EVENT_ID_TIXING_BAOCUN = "tixing-baocun"//需要知道用户保存提醒时，设置的内容（时间、标题、重复周期、日期、铃声）
    const val EVENT_ID_TIXING_LEIXING = "tixing-leixing"//通过参数区分 好的，知道了/还是 再等等

    const val EVENT_ID_ZHAOXIANG_LUXIANG = "zhaoxiang-luxiang"//开始录像
    const val EVENT_ID_ZHAOXIANG_PAIZHAO = "zhaoxiang-paizhao"//开始拍照
    const val EVENT_ID_ZHAOXIANG_BIANJI = "zhaoxiang-bianji"//开始编辑
    const val EVENT_ID_ZHAOXIANG_FENXIANG = "zhaoxiang-fenxiang"//开始分享
    const val EVENT_ID_ZHAOXIANG_BAOCUN = "zhaoxiang-baocun"//需要知道 保存时，用户选择的是相框类型（头像壁纸）及相框ID
    const val EVENT_ID_SHEZHI_RENSHE = "shezhi-renshe"//人设的选择情况

    const val EVENT_ID_SHEZHI_TUISONG = "shezhi-tuisong"//人设的选择情况


    const val EVENT_ID_YINSI2_YES = "yinsi2-yes"//隐私2设置-同意
    const val EVENT_ID_YINSI2_NO = "yinsi2-no"//隐私2设置-同意
    const val EVENT_ID_ZHUCE = "zhuce"//隐私2设置-同意

    const val EVENT_ID_YINSI_YES = "yinsi-yes"//隐私设置-同意
    const val EVENT_ID_YINSI_NO = "yinsi-no"//隐私设置-不同意
    const val EVENT_ID_DENGLU = "denglu"//每种登录方式的选择情况
    const val EVENT_ID_FULI_QIANDAO = "fuli-qiandao"//签到成功


    const val EVENT_ID_ZHUANZHU = "zhuanzhu"//点击-专注计时
    const val EVENT_ID_XIGUAN = "xiguan"//点击-专注计时
    const val EVENT_ID_TIXING = "tixing"//点击提醒

    // attention
    const val EVENT_ID_ZHUANZHU_KAISHI = "zhuanzhu-kaishi"//点击开始专注
    const val EVENT_ID_ZHUANZHU_JIESHU = "zhuanzhu-jieshu"//点击结束专注
    const val EVENT_ID_ZHUANZHU_TONGJI = "zhuanzhu-tongji"//点击统计
    const val EVENT_ID_ZHUANZHU_YINYUE = "zhuanzhu-yinyue"//点击白噪音
    const val EVENT_ID_ZHUANZHU_FENXIANG = "zhuanzhu-fenxiang"//点击分享按钮次数

    //habit
    const val EVENT_ID_XIGUAN_WANCHENG = "xiguan-wancheng"//点击完成任务
    const val EVENT_ID_XIGUAN_TONGJI = "xiguan-tongji"//点击人物统计
    const val EVENT_ID_XIGUAN_XIANGQING = "xiguan-xiangqing"//点击分享按钮次数

    const val EVENT_ID_SHARE = "share"//点击分享按钮次数
    const val EVENT_ID_SHOUYEUI = "shouyeui"//ui开启关闭按钮
    const val EVENT_ID_SHOUYEFENXIANG = "shouyefenxiang"//首页分享按钮
    const val EVENT_ID_ZHUANGBAN_GOUMAI = "zhuangban_goumai"//购买装扮弹窗
    const val EVENT_ID_ZHUANGBAN_GOUMAICLICK = "zhuangban_goumaiclick"//购买装扮弹窗
    const val EVENT_ID_ZHUANGBAN_GOUMAI_CHENGGONG = "zhuangban_goumai_chenggong"//购买装扮弹窗-点击购买按钮-购买成功
    const val EVENT_ID_ZHUANGBAN_GOUMAI_ZHIFU =
        "zhuangban_goumai_zhifu"//购买装扮弹窗-点击购买按钮-跳转支付页面 // 换成了from的方式
    const val EVENT_ID_ZHIFU = "zhifu"//购买装扮弹窗-点击购买按钮-跳转支付页面
    const val EVENT_ID_ZHIFU_XIADAN = "zhifu-xiadan"//进入支付页面-点击商品
    const val EVENT_ID_ZHIFU_ZHIFUCHENGGONG = "zhifu-zhifuchenggongn"//支付成功
    const val EVENT_ID_QIANDAO_TIXING = "qiandao_tixing"//签到提醒
    const val EVENT_ID_FULI_SHIPIN = "fuli_shipin"//每日福利视频


    const val EVENT_ID_GUANGGAO_GUANKAN = "guanggao_guankan"//总广告观看次数
    const val EVENT_ID_FULI_QIANDAO_FANBEI = "fuli-qiandao-fanbei"//签到成功翻倍看视频
    const val EVENT_ID_ZHUANGBAN_MENG = "zhuangban_meng"//梦系列预览页进入
    const val EVENT_ID_ZHUANGBAN_MENG_GOUMAI = "zhuangban_meng_goumai"//梦系列点击购买
    const val EVENT_ID_ZHUANGBAN_MENG_GOUMAI_CHENGGONG =
        "zhuangban_meng_goumai_chenggong"//进入梦·系列预览页购买成功
    const val EVENT_ID_SHOUYE_GONGAO = "shouye_gongao"//首页-公告点击量
    const val EVENT_ID_GONGGAO_BANNER = "gonggao_banner"//首页-公告-内容点击量
    const val EVENT_ID_DENGLU_RENSHE = "denglu_renshe"//记录用户登录后默认加载的人设


    //1.7.0 增加
    const val EVENT_ID_FULI_MRXY = "fuli-mrxy"//福利中心-每日许愿
    const val EVENT_ID_FULI_TTLJ = "fuli-ttlj"//福利中心-天天领奖
    const val EVENT_ID_FULI_MRXY_1 = "fuli-mrxy-1"//福利中心-每日许愿-许愿
    const val EVENT_ID_FULI_TTLJ_1 = "fuli-ttlj-1"//福利中心-天天领奖-领奖
    const val EVENT_ID_XINRENFULI = "xinrenfuli"//新人福利弹窗展示 参数区分主动弹出和点击
    const val EVENT_ID_XINRENFULI_1 = "xinrenfuli-1"//新人福利弹窗展示 参数区分1～5次的完成数量
    const val EVENT_ID_QIANDAO7 = "qiandao7"//7日签到弹窗展示 参数区分主动弹出和点击
    const val EVENT_ID_QIANDAO7_1 = "qiandao7-1"//7日签到-签到 区分签到的是哪一天
    const val EVENT_ID_QIANDAO7_2 = "qiandao7-2"//7日签到-双倍签到 区分签到的是哪一天
    const val EVENT_ID_QIANDAO7_3 = "qiandao7-3"//7日签到-补签 区分签到的是哪一天
    const val EVENT_ID_QIANDAO7_JJL = "qiandao7-ljjl"//7日签到-累计奖励 参数区分领取的是哪一个奖励
    const val EVENT_ID_GUANGGAO_DIANJI = "guanggao_dianji"//7日签到-累计奖励 参数区分领取的是哪一个奖励


}