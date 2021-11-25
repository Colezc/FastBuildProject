package com.coffee.swap

import android.app.Application
import android.content.Context
import android.os.Vibrator
import com.coffee.swap.consts.Constants.Companion.BASE_URL
import com.coffee.swap.consts.Constants.Companion.OPEN_LOG
import com.tea.httplibrary.config.BaseConfig
import com.coffee.swap.interceptor.RequestHeaderInterceptor
import com.coffee.swap.interceptor.SessionInterceptor
import com.juice.baselibrary.BaseApplication
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.tea.httplibrary.ApiClient
import com.tea.httplibrary.utils.Utils
import okhttp3.logging.HttpLoggingInterceptor
import kotlin.properties.Delegates

/**
 * Created by zc on 2020/10/10.
 * description:
 */
class MyApplication : BaseApplication() {

    var mVibrator: Vibrator? = null

    companion object {
        var context: Context by Delegates.notNull()
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }

    override fun initBaseConfig() {
        BaseConfig.builder()
            .setRetSuccess("0000")
            .setBaseUrl(BASE_URL)
            .setLogOpen(OPEN_LOG)
            .setArouterOpen(false)
            .addOkHttpInterceptor(RequestHeaderInterceptor())
            .addOkHttpInterceptor(true, HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addRetCodeInterceptors(SessionInterceptor(applicationContext))
            .setRetrofit(
                ApiClient.getInstance().getRetrofit(
                ApiClient.getInstance().getOkHttpClient(
                    BaseConfig.getOkHttpInterceptors()
                )
            ))
            .build()
        Utils.init(this)
        //SmartRefreshLayout的统一设置
        initSmartRefreshLayout()

    }

    /**
     * SmartRefreshLayout的统一设置
     */
    private fun initSmartRefreshLayout() {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context: Context, refreshLayout: RefreshLayout ->
            //            refreshLayout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white)
            return@setDefaultRefreshHeaderCreator ClassicsHeader(context)
        }

        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context: Context, refreshLayout: RefreshLayout ->
            return@setDefaultRefreshFooterCreator ClassicsFooter(context).setDrawableSize(20f)
        }
    }


}