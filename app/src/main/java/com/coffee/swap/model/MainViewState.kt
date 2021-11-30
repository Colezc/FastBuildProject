package com.coffee.swap.model

/**
 * @des
 * @author zc
 * @date 2021/11/30 14:48
 **/
data class MainViewState (
    val content: String = "等待网络请求内容",
    val pageStatus: PageStatus = PageStatus.Success
)

sealed class PageStatus {
    object Loading: PageStatus()
    object Success: PageStatus()
    data class Error(val throwable: Throwable): PageStatus()
}

sealed class MainViewEvent {
    data class ShowToast(val message: String): MainViewEvent()
    object ShowLoadingDialog: MainViewEvent()
    object DismissLoadingDialog: MainViewEvent()
}

sealed class MainViewAction {
    object PageRequest: MainViewAction()
    object PartRequest: MainViewAction()
}