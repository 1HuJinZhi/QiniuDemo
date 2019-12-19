package com.hujz.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.billy.android.loading.Gloading
import com.gyf.immersionbar.components.SimpleImmersionFragment
import com.hujz.base.delegate.IFragment
import com.lxj.xpopup.impl.LoadingPopupView
import java.lang.Exception

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/12
 *     desc   :
 * </pre>
 */
abstract class BaseFragment : SimpleImmersionFragment(), IFragment {

    protected var mContext: Context? = null
    private var mContentView: View? = null
    private var mDialog: LoadingPopupView? = null
    private var mGloadingHolder: Gloading.Holder? = null
    protected var canResumeDoBusiness = true
    var loadingType: LoadingType = LoadingType.NONE
    val mDebouchingClick by lazy { View.OnClickListener { onDebouchingClick(it) } }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setRootLayout(bindLayout())
        return mContentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData(arguments)
        initView(savedInstanceState)
        if (useLazyLoad()) return
        doBusiness(loadingType)
    }

    override fun onResume() {
        super.onResume()
        if (!useLazyLoad()) return
        if (canResumeDoBusiness) {
            doBusiness(loadingType)
            canResumeDoBusiness = false
        }
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
    }

    override fun setRootLayout(layoutId: Int) {
        try {
            mContentView = layoutInflater.inflate(layoutId, null)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun initImmersionBar() {

    }

    override fun initLoadingStatusViewIfNeed() {
        if (mGloadingHolder != null) return
        mGloadingHolder = getStatusViewHolder {
            gravity = Gravity.CENTER
        }
    }

    override fun showLoading() {
        initLoadingStatusViewIfNeed()
        mGloadingHolder?.showLoading()
    }

    override fun onLoadRetry(): Runnable {
        return Runnable {
            loadingType = LoadingType.STATUS_VIEW
            doBusiness(LoadingType.STATUS_VIEW)
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
        mDialog = getLoadingDialog(mContext, isCancelable)
    }

    override fun showDialogLoading(msg: String, isCancelable: Boolean) {
        initLoadingDialogIfNeed(isCancelable)
        mDialog?.apply {
            setTitle(msg)
            show()
        }
    }

    override fun hideDialogLoading() {
        mDialog?.apply {
            if (isDismiss) return
            dismiss()
        }
    }

    override fun useLazyLoad(): Boolean {
        return false
    }
}