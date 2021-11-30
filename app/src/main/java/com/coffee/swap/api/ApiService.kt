package com.coffee.swap.api

import com.coffee.swap.base.BaseRes
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @des
 * @author zc
 * @date 2021/11/26 10:14
 **/
interface ApiService {

    @GET("/app/v3/user/get/slideCode")
    suspend fun getSlideCode(@Query("phone") phone: String): BaseRes<String>

}