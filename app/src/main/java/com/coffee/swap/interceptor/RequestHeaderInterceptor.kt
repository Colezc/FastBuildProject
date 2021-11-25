package com.coffee.swap.interceptor

import com.tea.httplibrary.interceptor.BaseUrlInterceptor
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * @date 2019-07-06.
 * module：
 * description：请求头拦截器,如果需要设置多baseurl得继承BaseUrlInterceptor去写
 *                                   单baseurl直接继承Interceptor去写
 */
class RequestHeaderInterceptor : BaseUrlInterceptor() {

//    override fun addHeaders(): Headers {
//        return Headers.Builder()
//            .add(TokenManager.TOKEN, TokenManager.getToken())
////            .add("app_id", "wpkxpsejggapivjf")
////            .add("app_secret", "R3FzaHhSSXh4L2tqRzcxWFBmKzBvZz09")
//            .build()
//    }


    //单baseurl的写法参考 统一请求头的封装根据自身项目添加
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authorised: Request
        val headers = Headers.Builder()
//            .add(TokenManager.TOKEN, TokenManager.getToken())
            .build()
        authorised = request.newBuilder().headers(headers).build()
        return chain.proceed(authorised)
    }
}