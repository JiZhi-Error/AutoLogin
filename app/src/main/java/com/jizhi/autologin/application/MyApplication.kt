package com.jizhi.autologin.application

import android.app.Application
import androidx.multidex.MultiDex
/**
 *    author : JiZhi
 *    date   : 2021/4/29 12:35
 */
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)

//        RxHttp.init(, BuildConfig.DEBUG)


    }
}