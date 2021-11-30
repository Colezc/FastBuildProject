package com.coffee.swap.model

import androidx.lifecycle.MutableLiveData
import com.coffee.swap.api.ApiService
import com.coffee.swap.asLiveData
import com.coffee.swap.setEvent
import com.coffee.swap.utils.SingleLiveEvents
import com.tea.httplibrary.mvvm.BaseViewModel

/**
 * @des
 * @author zc
 * @date 2021/11/30 14:47
 **/
class MainViewModel: BaseViewModel<ApiService>() {

    private val _viewState = MutableLiveData(MainViewState())
    val viewState = _viewState.asLiveData()
    private val _viewEvents: SingleLiveEvents<MainViewEvent> = SingleLiveEvents()
    val viewEvents = _viewEvents.asLiveData()

    fun dispatch(viewAction: MainViewAction) {
        when(viewAction) {
            is MainViewAction.PageRequest -> pageRequest()
            is MainViewAction.PartRequest -> partRequest()
        }
    }

    private fun pageRequest() {
        launchOnlyresult(
            block = {
                _viewState.value = MainViewState(pageStatus = PageStatus.Loading)
                getApiService().getSlideCode("")
            },
            success = {
                _viewState.value = MainViewState(it, pageStatus = PageStatus.Success)
            },
            error = {
                _viewState.value = MainViewState(pageStatus = PageStatus.Error(it))
            }
        )
    }

    private fun partRequest() {
        launchOnlyresult(
            block = {
                _viewEvents.setEvent(MainViewEvent.ShowLoadingDialog)
                getApiService().getSlideCode("")
            },
            success = {
                _viewState.value = MainViewState(it, pageStatus = PageStatus.Success)
                _viewEvents.setEvent(
                    MainViewEvent.DismissLoadingDialog,
                    MainViewEvent.ShowToast(it)
                )
            },
            error = {
                _viewEvents.setEvent(MainViewEvent.DismissLoadingDialog)
            }
        )
    }
}