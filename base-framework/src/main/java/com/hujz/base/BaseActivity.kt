package com.hujz.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.billy.android.loading.Gloading
import com.gyf.immersionbar.ktx.immersionBar
import com.hujz.network.R
import com.hujz.base.delegate.IActivity
import com.lxj.xpopup.impl.LoadingPopupView
import java.lang.Exception

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/12
 *     desc   :
 * </pre>
 */
abstract class BaseActivity : AppCompatActivity(), IActivity {

    private var mDialog: LoadingPopupView? = null
    private var mGloadingHolder: Gloading.Holder? = null

    val mDebouchingClick by lazy { View.OnClickListener { onDebouchingClick(it) } }

    var loadingType: LoadingType = LoadingType.NONE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setRootLayout(bindLayout())
        initImmersionBar()
        initData(intent.extras)
        initView(savedInstanceState)
        doBusiness(loadingType)
    }

    override fun setRootLayout(layoutId: Int) {
        try {
            setContentView(layoutId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun initImmersionBar() {
        immersionBar {
            fitsSystemWindows(true)
            statusBarDarkFont(true, 0.2f)
            statusBarColor(R.color.barColor)
            navigationBarEnable(false)
        }
    }

    override fun initLoadingStatusViewIfNeed() {
        if (mGloadingHolder != null) return
        mGloadingHolder = getStatusViewHolder {
            gravity = Gravity.TOP
        }
    }

    override fun showLoading() {
        initLoadingStatusViewIfNeed()
        mGloadingHolder?.showLoading()
    }

    override fun onLoadRetry(): Runnable {
        return Runnable {
            loadingType = LoadingType.STATUS_VIEW
            doBusiness(loadingType)
        }
    }

    override fun showLoadSuccess() {
        initLoadingStatusViewIfNeed()
        mGloadingHolder?.showLoadSuccess()
    }

    override fun showLoadFailed() {
        initLoadingStatusViewIfNeed()
        mGloadingHolder?.showLoadFailed()
    }

    override fun showNetWorkError() {
        initLoadingStatusViewIfNeed()
//        mGloadingHolder?.showLoadingStatus()
    }

    override fun showEmpty() {
        initLoadingStatusViewIfNeed()
        mGloadingHolder?.showEmpty()
    }

    override fun initLoadingDialogIfNeed(isCancelable: Boolean) {
        if (mDialog != null) return
        mDialog = getLoadingDialog(this, isCancelable)
    }

    override fun showDialogLoading(msg: String, isCancelable: Boolean) {
        initLoadingDialogIfNeed(isCancelable)
        mDialog?.setTitle(msg)
        mDialog?.show()
    }

    override fun hideDialogLoading() {
        if (mDialog?.isDismiss == true) return
        mDialog?.dismiss()
    }

}