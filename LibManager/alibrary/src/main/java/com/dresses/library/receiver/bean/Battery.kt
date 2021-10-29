package com.dresses.library.receiver.bean

import androidx.annotation.Keep

@Keep
data class BatteryData (
    val status:Int//eg:BatteryManager.BATTERY_STATUS_CHARGING
    ,val health:Int//eg:BatteryManager.BATTERY_HEALTH_GOOD
    ,val level:Int//得到电池剩余容量
    ,val scale:Int// 得到电池最大值。通常为100。
    ,val plugged:Int// 充电方式。eg:BatteryManager.BATTERY_PLUGGED_AC：AC充电
)