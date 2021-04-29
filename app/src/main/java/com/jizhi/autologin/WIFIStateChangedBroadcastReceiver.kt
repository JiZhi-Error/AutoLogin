package com.jizhi.autologin

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.util.Log
import android.widget.Toast

class WIFIStateChangedBroadcastReceiver : BroadcastReceiver() {

    private val TAG = this::class.java.name

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == WifiManager.NETWORK_STATE_CHANGED_ACTION) {
            Log.i(TAG, "onReceive: 改变状态")
            Toast.makeText(context,"onReceive: 改变状态",Toast.LENGTH_SHORT).show()
        }
    }
}