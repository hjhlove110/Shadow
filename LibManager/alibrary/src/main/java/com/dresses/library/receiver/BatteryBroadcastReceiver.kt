package com.dresses.library.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.BatteryManager
import com.dresses.library.receiver.bean.BatteryData
import com.jess.arms.integration.EventBusManager

class BatteryBroadcastReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {

        val action = intent?.action
        if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
            // 得到电池状态：
            // BatteryManager.BATTERY_STATUS_CHARGING：充电状态。
            // BatteryManager.BATTERY_STATUS_DISCHARGING：放电状态。
            // BatteryManager.BATTERY_STATUS_NOT_CHARGING：未充满。
            // BatteryManager.BATTERY_STATUS_FULL：充满电。
            // BatteryManager.BATTERY_STATUS_UNKNOWN：未知状态。
            val status = intent!!.getIntExtra("status", 0);
            // 得到健康状态：
            // BatteryManager.BATTERY_HEALTH_GOOD：状态良好。
            // BatteryManager.BATTERY_HEALTH_DEAD：电池没有电。
            // BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE：电池电压过高。
            // BatteryManager.BATTERY_HEALTH_OVERHEAT：电池过热。
            // BatteryManager.BATTERY_HEALTH_UNKNOWN：未知状态。
            val health = intent.getIntExtra("health", 0)
            // boolean类型
            val present = intent.getBooleanExtra("present", false)
            // 得到电池剩余容量
            val level = intent.getIntExtra("level", 0);
            // 得到电池最大值。通常为100。
            val scale = intent.getIntExtra("scale", 0);
            // 得到图标ID
            val iconSmall = intent.getIntExtra("icon-small", 0);
            // 充电方式：　BatteryManager.BATTERY_PLUGGED_AC：AC充电。　BatteryManager.BATTERY_PLUGGED_USB：USB充电。
            val plugged = intent.getIntExtra("plugged", 0);
            // 得到电池的电压
            val voltage = intent.getIntExtra("voltage", 0);
            // 得到电池的温度,0.1度单位。例如 表示197的时候，意思为19.7度
            val temperature = intent.getIntExtra("temperature", 0);
            // 得到电池的类型
            val technology = intent.getStringExtra("technology");
            // 得到电池状态
            var statusString = "";
            // 根据状态id，得到状态字符串
            EventBusManager.getInstance().post(BatteryData(status,health,level,scale,plugged))
//            when (status) {
//                 BatteryManager.BATTERY_STATUS_UNKNOWN->
//                statusString = "unknown";
//                 BatteryManager.BATTERY_STATUS_CHARGING->
//                statusString = "charging";
//                 BatteryManager.BATTERY_STATUS_DISCHARGING->
//                statusString = "discharging";
//                 BatteryManager.BATTERY_STATUS_NOT_CHARGING->
//                statusString = "not charging";
//                 BatteryManager.BATTERY_STATUS_FULL->
//                statusString = "full";
//            }
        }
    }
}