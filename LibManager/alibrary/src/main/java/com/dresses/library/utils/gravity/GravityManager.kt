package com.dresses.library.utils.gravity

import android.content.Context
import android.hardware.*
import com.dresses.library.AppLifecyclesImpl
import com.jess.arms.utils.LogUtils
import kotlin.math.abs
import kotlin.math.absoluteValue

object GravityManager : SensorEventListener {

    //速度阈值，当摇晃速度达到这值后产生作用
    private const val SPEED_SHRESHOLD = 5000

    //两次检测的时间间隔
    private const val UP_TATE_INTERVAL_TIME = 70


    private var isFlip = false

    //传感器管理器
    private val sensorManager: SensorManager by lazy {
        //获得传感器管理器
        AppLifecyclesImpl.appContext.getSystemService(Context.SENSOR_SERVICE) as (SensorManager)
    }

    //传感器
    private val sensor: Sensor by lazy {
        //获得重力传感器
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    }

    //手机上一个位置时重力感应坐标
    private var lastX = 0F
    private var lastY = 0F
    private var lastZ = 0F

    //上次检测时间
    private var lastUpdateTime = System.currentTimeMillis()

    private val gravityListeners = mutableListOf<OnGravityListener>()

    private var isStart = false

    //开始
    fun start() {
        if (!isStart) {
            sensorManager.registerListener(
                this, sensor,
                SensorManager.SENSOR_DELAY_NORMAL
            )
            isStart = true
        }
    }


    //停止检测
    fun stop() {
        if (!isStart) return
        sensorManager.unregisterListener(this)
        isStart = false
    }

    fun release(){
        sensorManager.cancelTriggerSensor(object : TriggerEventListener(){
            override fun onTrigger(event: TriggerEvent?) {

            }
        }, sensor)
        stop()
        gravityListeners.clear()
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (!isStart) return
        //获得x,y,z坐标
        var x = event!!.values[0]
        var y = event.values[1]
        var z = event.values[2]
//        LogUtils.debugInfo("GravityManager", "x = $x ,y = $y,z = $z ")
//        flip(z, x, y)//翻转
        shack(x, y, z)//摇晃
        checkHandStand(x, y, z)//摇晃
//        countShack(x, y, z)
    }


    fun addOnCountShackListener(listener: OnGravityListener) {
        if (!gravityListeners.contains(listener)) {
            gravityListeners.add(listener)
        }
    }

    fun removeCountShackListener(listener: OnGravityListener) {
        if (gravityListeners.contains(listener)) {
            gravityListeners.remove(listener)
        }
    }

    private var lastCountTime = 0L
    private fun isCountTimeCodeEnable(): Boolean {
        //两次检测的时间间隔
        var timeInterval = System.currentTimeMillis() - lastCountTime
        //判断是否达到了检测时间间隔
        //现在的时间变成last时间
        return timeInterval > 3000
    }

    private fun flip(z: Float, x: Float, y: Float) {
        if (!isCountTimeCodeEnable() || gravityListeners.isEmpty()) {
            return
        }
        if (z < -9 && !isFlip && abs(x) < 2 && abs(y) < 2) {
            LogUtils.debugInfo("GravityManager", "翻转了")
            for (listener in gravityListeners)
                listener.onFlip()
            isFlip = true
            lastCountTime = System.currentTimeMillis()
        } else if (z > 0) {
            isFlip = false
        }
    }
    private fun checkHandStand(x: Float, y: Float, z: Float) {
        if (!isCountTimeCodeEnable() || gravityListeners.isEmpty()) {
            return
        }
        if (y < -9 && abs(x) < 2 && abs(z) < 2) {
            lastCountTime = System.currentTimeMillis()
            LogUtils.debugInfo("GravityManager", "倒立了")
            for (listener in gravityListeners)
                listener.onHandstand()
        }
    }


    private fun shack(x: Float, y: Float, z: Float) {
        if (!isCountTimeCodeEnable() || gravityListeners.isEmpty()) {
            return
        }
        var currentUpdateTime = System.currentTimeMillis()
        //两次检测的时间间隔
        var timeInterval = currentUpdateTime - lastUpdateTime
        //判断是否达到了检测时间间隔
        if (timeInterval < UP_TATE_INTERVAL_TIME) {
            return
        }
        //现在的时间变成last时间
        lastUpdateTime = currentUpdateTime


        //获得x,y,z的变化值
        var deltaX = x - lastX
        var deltaY = y - lastY
        var deltaZ = z - lastZ

        //将现在的坐标变成last坐标
        lastX = x
        lastY = y
        lastZ = z

        var speed = (Math.sqrt(
            (deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ).toDouble()
        ) / timeInterval * 10000)
        //达到速度阀值，发出提示

        if (speed >= SPEED_SHRESHOLD) {
            lastCountTime = System.currentTimeMillis()
            LogUtils.debugInfo("GravityManager", "晃了一下")
            //手机晃动
            for (listener in gravityListeners) {
                listener.onShack()
            }
        }
    }
}

interface OnGravityListener {
    fun onShack()
    fun onHandstand()
    fun onFlip()
}