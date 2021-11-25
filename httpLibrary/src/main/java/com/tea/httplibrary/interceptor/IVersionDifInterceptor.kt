package com.tea.httplibrary.interceptor

interface IVersionDifInterceptor {
    /**
     * 根据返回的version，判断是否进行拦截
     *
     * @param version
     * @return
     */
    fun intercept(version: String?): Boolean

    /**
     * intercept(String returnCode)方法为true的时候调用该方法
     *
     * @param serviceVersion
     */
    fun doWork(serviceVersion: String?)
}