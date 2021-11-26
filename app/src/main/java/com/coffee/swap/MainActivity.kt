package com.coffee.swap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.ViewDataBinding
import com.coffee.swap.api.ApiService
import com.juice.baselibrary.base.BaseActivity
import com.juice.baselibrary.view.IVaryViewHelperController
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.tea.httplibrary.mvvm.BaseViewModel

class MainActivity : BaseActivity<BaseViewModel<ApiService>, ViewDataBinding>() {

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun init(savedInstanceState: Bundle?) {

    }

    override fun initViewModel() {
        super.initViewModel()
    }

    override fun load() {
        super.load()

    }

}