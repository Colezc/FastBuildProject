package com.juice.baselibrary.base

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.IBinder
import android.text.TextUtils
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.tea.httplibrary.mvvm.BaseViewModel
import com.juice.baselibrary.view.IVaryViewHelperController
import com.juice.baselibrary.view.IView
import com.juice.baselibrary.view.VaryViewHelperController
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.tea.httplibrary.utils.ActivityUtils
import com.tea.httplibrary.utils.ToastUtils
import java.lang.reflect.ParameterizedType

/**
 * @date 2020/3/16.
 * module：
 * description：
 */
abstract class BaseActivity<VM : BaseViewModel<*>, DB : ViewDataBinding> : AppCompatActivity(),
    IView {

    //viewmodel
    protected lateinit var mViewModel: VM
    //databing
    protected var mBinding: DB? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    /**
     * @return 该View 替换为显示loadingView 或者 emptyView 或者 netWorkErrorView
     */
    abstract fun getReplaceView(): View

    /**
     * 初始化
     */
    abstract fun init(savedInstanceState: Bundle?)

    /**
     * 替换view
     */
    var viewController: IVaryViewHelperController? = null

    /**
     * 弹窗
     */
    private var dialog: ProgressDialog? = null

    /**
     * 刷新相关 因为单界面不存在加载，这样只针对是否开启刷新功能做处理，可设置为null，为null则不具备刷新相关能力
     */
    abstract fun getSmartRefreshLayout(): SmartRefreshLayout?

    private var mRefreshEnable = true //是否能进行下拉刷新

    abstract fun refreshData()


    override fun onCreate(savedInstanceState: Bundle?) {
        ActivityUtils.get()?.addActivity(this)
        super.onCreate(savedInstanceState)
        initViewDataBinding()
        createViewModel()
        viewController = initVaryViewHelperController()
        lifecycle.addObserver(mViewModel)
        registerViewChange()
        initRefresh()
        init(savedInstanceState)
    }

    /***
     * view
     */
    protected open fun initVaryViewHelperController(): IVaryViewHelperController {
        return VaryViewHelperController(getReplaceView())
    }

    private fun initRefresh() {
        if (getSmartRefreshLayout() != null) {
            getSmartRefreshLayout()?.isEnabled = mRefreshEnable
            //不具备加载功能
            getSmartRefreshLayout()?.setEnableLoadMore(false)
            if (mRefreshEnable) {
                getSmartRefreshLayout()?.setOnRefreshListener {
                    refreshData()
                }
            }
        }
    }

    /**
     *     DataBinding
     *     actualTypeArguments[0]  BaseViewModel
     *     actualTypeArguments[1]  ViewDataBinding
     *
     */
    private fun initViewDataBinding() {
        val cls: Class<*> =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1] as Class<*>
        if (ViewDataBinding::class.java != cls && ViewDataBinding::class.java.isAssignableFrom(cls)) {
            mBinding = DataBindingUtil.setContentView(this, getLayoutId())
            mBinding?.lifecycleOwner = this
        } else {
            setContentView(getLayoutId())
        }

    }

    /**
     *     创建viewmodel
     *     actualTypeArguments[0]  BaseViewModel
     *     actualTypeArguments[1]  ViewDataBinding
     *
     */
    private fun createViewModel() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val tp = type.actualTypeArguments[0]
            val tClass = tp as? Class<VM> ?: BaseViewModel::class.java
            mViewModel = ViewModelProviders.of(this)[tClass] as VM
        }
    }

    /**
     * 注册视图变化事件
     */
    private fun registerViewChange() {
        mViewModel.viewChange.showLoading.observe(this, Observer {
            viewController?.let {
                if (!it.isHasRestore) {
                    showLoading()
                }
            }
        })
        mViewModel.viewChange.showDialogProgress.observe(this, Observer {
            showDialogProgress(it)
        })
        mViewModel.viewChange.dismissDialog.observe(this, Observer {
            dismissDialog()
        })
        mViewModel.viewChange.showToast.observe(this, Observer {
            showToast(it)
        })
        mViewModel.viewChange.showTips.observe(this, Observer {
            showTips(it)
        })
        mViewModel.viewChange.showEmpty.observe(this, Observer {
            showEmpty(it)
        })
        mViewModel.viewChange.showNetworkError.observe(this, Observer {
            showNetworkError(it, mViewModel.listener)
        })
        mViewModel.viewChange.restore.observe(this, Observer {
            viewController?.restore()
            //代表有设置刷新
            if (getSmartRefreshLayout() != null) {
                getSmartRefreshLayout()?.finishRefresh()
            }
        })
    }


    /**
     * 相关view替换
     */

    override fun showTips(msg: String) {
//        val snackBar = TSnackbar.make(
//            findViewById(android.R.id.content),
//            msg,
//            TSnackbar.LENGTH_SHORT
//        )
//        val snackBarView = snackBar.view
//        snackBarView.setBackgroundColor(resources.getColor(R.color.mCCE4FF))
//        val textView =
//            snackBarView.findViewById<TextView>(com.androidadvance.topsnackbar.R.id.snackbar_text)
//        textView.setTextColor(resources.getColor(R.color.m177AE6))
//        snackBar.show()
    }

    /**
     * 展示弹窗
     */
    override fun showDialogProgress(msg: String) {
        showDialogProgress(msg, true, null)
    }

    override fun showDialogProgress(
        msg: String,
        cancelable: Boolean,
        onCancelListener: DialogInterface.OnCancelListener?
    ) {
        try {
            if (dialog == null) {
                dialog = ProgressDialog(this)
                dialog?.setCancelable(cancelable)
                dialog?.setCanceledOnTouchOutside(cancelable)
                dialog?.setOnCancelListener(onCancelListener)
            }
            if (!TextUtils.isEmpty(msg)) dialog?.setMessage(msg)
            dialog?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * 消失
     */
    override fun dismissDialog() {
        dialog?.let {
            if (it.isShowing) it.dismiss()
        }
    }

    /**
     * loading
     */
    override fun showLoading() {
        viewController?.showLoading()
    }

    /***
     * loading 带文字
     */
    override fun showLoading(msg: String?) {
        viewController?.showLoading(msg)
    }

    /**
     * 无数据，空白页
     */
    override fun showEmpty(emptyMsg: String?) {
        viewController?.showEmpty(emptyMsg)
    }

    /**
     * 无数据，空白页
     */
    override fun showEmpty(
        emptyMsg: String?,
        listener: View.OnClickListener?
    ) {
        viewController?.showEmpty(emptyMsg, listener)
    }

    /**
     * 网络错误
     */
    override fun showNetworkError(listener: View.OnClickListener?) {
        viewController?.showNetworkError(listener)
    }

    /**
     * 网络错误
     */
    override fun showNetworkError(
        msg: String?,
        listener: View.OnClickListener?
    ) {
        viewController?.showNetworkError(msg, listener)
    }

    /**
     * 自定义view展示
     */
    override fun showCustomView(
        drawableInt: Int,
        title: String?,
        msg: String?,
        btnText: String?,
        listener: View.OnClickListener?
    ) {
        viewController?.showCustomView(drawableInt, title, msg, btnText, listener)
    }

    /**
     * 恢复
     */
    override fun restore() {
        viewController?.restore()
    }

    override val isHasRestore: Boolean
        get() = viewController?.isHasRestore ?: false

    /**
     * toast
     */
    override fun showToast(msg: String) {
        ToastUtils.showShortToastSafe(mActivity, msg)
    }

    override fun showToast(msg: Int) {
        ToastUtils.show(mActivity, msg)
    }

    override val mActivity: Activity
        get() = this

    override val mContext: Context
        get() = this

    override val mAppContext: Context
        get() = applicationContext


    /**
     *  @param refreshEnable 设置是否刷新操作
     */
    open fun setRefreshEnable(refreshEnable: Boolean) {
        //不为空才可以刷新
        if (getSmartRefreshLayout() != null) {
            mRefreshEnable = refreshEnable
            getSmartRefreshLayout()?.isEnabled = mRefreshEnable
        }
    }

    /**
     * 销毁
     */
    override fun onDestroy() {
        super.onDestroy()
        //activity出栈
        ActivityUtils.get()?.remove(this)
        lifecycle.removeObserver(mViewModel)
        //相关销毁，相关事件置空
        if (mBinding != null) {
            mBinding = null
        }
        if (viewController != null) {
            viewController = null
        }
        if (dialog != null) {
            dialog = null
        }
        if (getSmartRefreshLayout() != null) {
            getSmartRefreshLayout()?.setOnRefreshListener(null)
            getSmartRefreshLayout()?.setOnLoadMoreListener(null)
            getSmartRefreshLayout() == null
        }
    }

    /**
     * 获取点击事件
     */
    @CallSuper
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN) {
            val view = currentFocus
            if (isShouldHideKeyBord(view, ev)) {
                hideSoftInput(view!!.windowToken)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    /**
     * 判定当前是否需要隐藏
     */
    private fun isShouldHideKeyBord(v: View?, ev: MotionEvent): Boolean {
        if (v != null && v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.height
            val right = left + v.width
            return !(ev.x > left && ev.x < right && ev.y > top && ev.y < bottom)
        }
        return false
    }

    /**
     * 隐藏软键盘
     */
    private fun hideSoftInput(token: IBinder?) {
        if (token != null) {
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager!!.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    /**
     * 显示键盘
     *
     * @param et 输入焦点
     */
    open fun showInput(et: EditText) {
        et.requestFocus()
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
    }

}