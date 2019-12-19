package com.hujz.base.delegate

import android.view.View
import androidx.annotation.LayoutRes
import com.hujz.base.LoadingType

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/12
 *     desc   :
 * </pre>
 */
interface IView {

    fun bindLayout(): Int

    fun setRootLayout(@LayoutRes layoutId: Int)

    fun doBusiness(loadingType: LoadingType)

    fun initLoadingStatusViewIfNeed()

    fun showLoading()

    fun showLoadSuccess()

    fun showLoadFailed()

    fun showNetWorkError()

    fun showEmpty()

    fun onLoadRetry(): Runnable

    fun initLoadingDialogIfNeed(isCancelable: Boolean)

    fun showDialogLoading(msg: String, isCancelable: Boolean)

    fun hideDialogLoading()

    fun onDebouchingClick(view: View)

}