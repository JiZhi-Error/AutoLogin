package com.jizhi.autologin

import android.content.Context
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.NetworkUtils
import com.jizhi.autologin.databinding.ActivityMainBinding
import rxhttp.RxHttp
import kotlin.system.exitProcess


class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = this::class.java.name
    private lateinit var binding: ActivityMainBinding
    private lateinit var sp: SharedPreferences
    private lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sp = getSharedPreferences("login", Context.MODE_PRIVATE)

        initView()
        val wifiStateChangedBroadcastReceiver = WIFIStateChangedBroadcastReceiver()
        val intentFilter = IntentFilter("android.net.wifi.STATE_CHANGE")
        registerReceiver(wifiStateChangedBroadcastReceiver,intentFilter)
    }

    override fun onStart() {
        super.onStart()
        val username = sp.getString("username", "")
        val password = sp.getString("password", "")
        if (!username.isNullOrBlank() && !password.isNullOrBlank()) {
            binding.etUsername.setText(username)
            binding.etPassword.setText(password)

        }

    }

    override fun onResume() {
        super.onResume()
        if (NetworkUtils.isWifiConnected()) {
            binding.tvCurrentIp.text = NetworkUtils.getIpAddressByWifi()
            NetworkUtils.isWifiAvailableAsync {
                if (it) {
                    binding.tvWifiState.text = "WiFi可用"
                }else{
                    binding.tvWifiState.text = "WIFI不可用"
                }
            }

        } else {
            binding.tvWifiState.text = "WiFi未链接"
        }
    }


    private fun initView() {
        binding.butLogIn.setOnClickListener(this)
        binding.butLogOut.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.but_log_in -> {
                val username = binding.etUsername.text.toString()
                val password = binding.etPassword.text.toString()
                if (username.isNotBlank() && password.isNotBlank()) {
                    editor = sp.edit()
                    editor.putString("username", username)
                    editor.putString("password", password)
                    editor.apply()

                    RxHttp.postForm("/portal3/portal.jsp")
                            .add("username", username)
                            .add("password", password)
                            .add("wlanuserip", NetworkUtils.getIpAddressByWifi())
                            .add("func", "Login")
                            .asString()
                            .subscribe {
                                Log.i(TAG, "onClick: $it")
                            }

                } else {
                    Toast.makeText(this, "用户名或密码为空", Toast.LENGTH_SHORT).show()
                }
            }
            R.id.but_log_out -> {

            }

        }
    }

    override fun onPause() {
        super.onPause()
//        exitProcess(0)
    }
}