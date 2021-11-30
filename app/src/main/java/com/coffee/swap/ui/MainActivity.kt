package com.coffee.swap.ui

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.coffee.swap.R
import com.coffee.swap.model.*
import com.coffee.swap.observeEvent
import com.coffee.swap.observeState
import com.juice.baselibrary.base.BaseActivity

class MainActivity : BaseActivity<MainViewModel, ViewDataBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun initViewModel() {
        super.initViewModel()
        mViewModel.viewState.let { state ->
            state.observeState(this, MainViewState::pageStatus) {
                when (it) {
                    is PageStatus.Success -> {

                    }
                    is PageStatus.Error -> {
                        mViewModel.viewChange.showNetworkError.call()
                    }
                    is PageStatus.Loading -> {
                        mViewModel.viewChange.showLoading.call()
                    }
                }
            }
            state.observeState(this, MainViewState::content) {

            }
        }
        mViewModel.viewEvents.observeEvent(this) {
            when(it) {
                is MainViewEvent.ShowLoadingDialog -> {
                    showLoadingDialog()
                }
                is MainViewEvent.DismissLoadingDialog -> {
                    dismissLoadingDialog()
                }
                is MainViewEvent.ShowToast -> {
                    showToast(it.message)
                }
            }
        }
    }

    fun simpleRequest(view: View) {
        mViewModel.dispatch(MainViewAction.PageRequest)
    }

    fun partRequest(view: View) {
        mViewModel.dispatch(MainViewAction.PartRequest)
    }

    override fun load() {
        super.load()

    }

    private var progressDialog: ProgressDialog? = null

    private fun showLoadingDialog() {
        if (progressDialog == null)
            progressDialog = ProgressDialog(this)
        progressDialog?.show()
    }

    private fun dismissLoadingDialog() {
        progressDialog?.takeIf { it.isShowing }?.dismiss()
    }

}