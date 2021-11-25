package com.juice.baselibrary.view

import android.annotation.SuppressLint
import android.view.View
import androidx.databinding.BindingAdapter
import com.jakewharton.rxbinding2.view.RxView
import java.util.concurrent.TimeUnit

/**
 * Created by zc on 2020/6/3.
 * description:
 */
object CommandOnClick {

    private const val CLICK_INTERVAL: Long = 1

    /**
     * requireAll 是意思是是否需要绑定全部参数, false为否
     * View的onClick事件绑定
     * onClickCommand 绑定的命令,
     * isThrottleFirst 是否开启防止过快点击
     */
    @SuppressLint("CheckResult")
    @BindingAdapter(value = ["android:OnClickListener", "isThrottleFirst"], requireAll = false)
    @JvmStatic
    fun onClickCommand(view: View, isThrottleFirst: Boolean, method: ((view: View) -> Unit)? = null) {
        if (isThrottleFirst) {
            RxView.clicks(view)
                .subscribe {
                    method?.invoke(view)
//                    clickCommand?.onClick(view)
                }
        }else {
            RxView.clicks(view)
                .throttleFirst(CLICK_INTERVAL, TimeUnit.SECONDS)
                .subscribe {
                    method?.invoke(view)
//                    clickCommand?.onClick(view)
                }
        }
    }
}