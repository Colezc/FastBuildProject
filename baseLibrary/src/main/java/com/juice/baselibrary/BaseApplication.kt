package com.juice.baselibrary

import android.app.Application

/**
 * @des
 * @author zc
 * @date 2021/11/25 17:05
 **/
abstract class BaseApplication: Application() {
    abstract fun initBaseConfig() //初始化配置参数

    override fun onCreate() {
        super.onCreate()
        initBaseConfig()
    }
}