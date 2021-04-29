package com.jizhi.autologin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.NetworkUtils

class MainActivity : AppCompatActivity() {

    private val TAG = this::class.java.name

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ipAddressByWifi = NetworkUtils.getIpAddressByWifi()


    }
}