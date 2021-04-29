package com.jizhi.autologin

import rxhttp.wrapper.annotation.DefaultDomain


/**
 *    author : JiZhi
 *    date   : 2021/4/29 12:52
 */
class Url {


    companion object {
        @DefaultDomain
        @JvmField
        val baseUrl = "http://27.129.43.18/"
    }
////    @DefaultDomain //设置为默认域名
//    @JvmStatic
//    @JvmField
//    var baseUrl = "http://27.129.43.18/"
}