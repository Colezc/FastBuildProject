package com.coffee.swap.interceptor

import android.app.Activity
import android.content.Context
import android.util.Log
import com.tea.httplibrary.interceptor.IReturnCodeErrorInterceptor

/**
 * @author madreain
 * @date 2019/3/4.
 * module：
 * description：returnCode返回session_100 拦截处理
 */
class SessionInterceptor(val mContext: Context) : IReturnCodeErrorInterceptor {
    //和接口定义互踢的相关参数返回，然后在doWork方法进行跳转
    override fun intercept(returnCode: String?): Boolean {
        if (returnCode == "1006" || returnCode == "1002" || returnCode == "1003") {
            return true
        }
        return "-100" == returnCode
    }

    override fun doWork(returnCode: String?, msg: String?) {
        if (returnCode == "1006" || returnCode == "1002" || returnCode == "1003") {
//            TokenManager.clearToken()
//            AccountSharedPreferences.instance.clearAccountData()
//            if (isFirstLogin) {
//                isFirstLogin = false
//                if (msg != null) {
//                    showToast(mContext, msg)
//                }
//                startAct(mContext, LoginActivity::class.java)
//            } else {
//
//            }
        }
//        if (returnCode == "1006" || returnCode == "1003") {
//            if (msg != null) {
//                showToast(mContext, msg)
//            }
//        }

    }

}