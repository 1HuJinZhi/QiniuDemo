package com.hujz.base

import android.os.Bundle
import com.hujz.network.R
import com.hujz.base.delegate.IRefreshView
import com.hujz.base.network.rxhttp.ErrorInfo
import com.hujz.base.network.rxhttp.OnError
import com.rxjava.rxlife.lifeOnMain
import com.scwang.smartrefresh.layout.api.RefreshLayout
import kotlinx.android.synthetic.main.common_refresh_horizontal.*
import kotlinx.android.synthetic.main.common_refresh_vertical.*

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/16
 *     desc   :
 * </pre>
 */
abstract class BaseRefreshFragment<T> : BaseFragment(), IRefreshView<T> {

    var mSrlCommonRefresh: RefreshLayout? = null

    override fun setRootLayout(layoutId: Int) {
        if (isHorizontalRefresh()) {
            super.setRootLayout(R.layout.common_refresh_horizontal)
            layoutInflater.inflate(layoutId, mFlRefreshHorizontalContent)
        } else {
            super.setRootLayout(R.layout.common_refresh_vertical)
            layoutInflater.inflate(layoutId, mFlRefreshVerticalContent)
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mSrlCommonRefresh =
            if (isHorizontalRefresh()) mSrlCommonHorizontalRefresh else mSrlCommonVerticalRefresh
        mSrlCommonRefresh?.apply {
            setEnableRefresh(isEnableRefresh())
            setEnableLoadMore(false)
            setOnRefreshListener {
                loadingType = LoadingType.NONE
                doBusiness(loadingType)
            }
        }
    }

    override fun doBusiness(loadingType: LoadingType) {
        val observable = getObservable()
        val observableLife = when (loadingType) {
            LoadingType.STATUS_VIEW -> wrapLoading(this, observable)
            LoadingType.DIALOG -> wrapDialogLoading(this, observable)
            LoadingType.NONE -> observable.lifeOnMain(this)
        }
        observableLife.subscribe(this, handleException(), this)
    }

    override fun accept(t: T) {
        businessCallback(t)
    }

    override fun handleException(): OnError {
        return object : OnError {
            override fun onError(error: ErrorInfo) {
                businessComplete(false)
            }
        }
    }

    override fun run() {
        businessComplete(true)
    }

    override fun businessComplete(success: Boolean, noMoreData: Boolean) {
        if (loadingType == LoadingType.STATUS_VIEW) {
            if (success) showLoadSuccess()
            else showLoadFailed()
        } else {
            mSrlCommonRefresh.defaultHandleRefresh(success, noMoreData)
            showLoadSuccess()
        }
    }

    override fun isEnableRefresh(): Boolean {
        return true
    }

    override fun isHorizontalRefresh(): Boolean {
        return false
    }


}