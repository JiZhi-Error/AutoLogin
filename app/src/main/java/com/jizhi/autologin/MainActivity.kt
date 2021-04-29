package com.jizhi.autologin

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.NetworkUtils
import com.jizhi.autologin.databinding.ActivityMainBinding
import rxhttp.RxHttp


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

        binding.tvCurrentIp.text = NetworkUtils.getIpAddressByWifi()

    }


    private fun initView() {
        binding.butLogIn.setOnClickListener(this)
        binding.butLogOut.setOnClickListener(this)
        val username = sp.getString("username", "")
        val password = sp.getString("password", "")
        if (!username.isNullOrBlank() && !password.isNullOrBlank()) {
            binding.etUsername.setText(username)
            binding.etPassword.setText(password)
        }
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
}