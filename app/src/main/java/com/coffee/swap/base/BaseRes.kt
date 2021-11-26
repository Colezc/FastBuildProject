package com.coffee.swap.base

import androidx.annotation.Keep
import com.tea.httplibrary.mvvm.IRes

/**
 * Created by zc on 2020/10/11.
 * description: 接口封装类
 */
@Keep
data class BaseRes<T>(
    val message: String,
    val code: String,
    val result: T
) : IRes<T> {

    override fun getBaseMsg() = message

    override fun getBaseCode() = code

    override fun getBaseResult() = result

}