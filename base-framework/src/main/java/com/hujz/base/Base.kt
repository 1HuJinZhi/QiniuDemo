package com.hujz.base

import android.content.Context
import android.view.View
import androidx.lifecycle.LifecycleOwner
import com.billy.android.loading.Gloading
import com.blankj.utilcode.util.ClickUtils
import com.hujz.base.delegate.IView
import com.hujz.base.widget.statusview.GloadingData
import com.hujz.imageloader.loader.ImageLoader
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.impl.LoadingPopupView
import com.rxjava.rxlife.ObservableLife
import com.rxjava.rxlife.lifeOnMain
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.constant.RefreshState
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/12
 *     desc   : 尽量封装一些重复没有逻辑的代码
 * </pre>
 */

const val REQUEST_DATA_OK = 2000000

const val DEFAULT_PAGE_NUMBER = 1

const val DEFAULT_PAGE_SIZE = 10

val imageLoader:ImageLoader = ImageLoader.instance

fun applyDebouchingClickListener(
    vararg views: View,
    click: View.OnClickListener
) {
    ClickUtils.applyGlobalDebouncing(views, click)
    ClickUtils.applyPressedViewScale(*views)
}

fun BaseActivity.getStatusViewHolder(data: GloadingData.() -> Unit): Gloading.Holder {
    return Gloading.getDefault().wrap(this).withData(GloadingData().apply(data))
        .withRetry(onLoadRetry())
}

fun BaseFragment.getStatusViewHolder(data: GloadingData.() -> Unit): Gloading.Holder {
    return Gloading.getDefault().wrap(view).withData(GloadingData().apply(data))
        .withRetry(onLoadRetry())
}

fun getLoadingDialog(context: Context?, isCancelable: Boolean = true): LoadingPopupView {
    return XPopup.Builder(context).dismissOnTouchOutside(isCancelable)
        .dismissOnBackPressed(isCancelable).asLoading()
}

fun <T> LifecycleOwner.wrapLoading(
    view: IView,
    observable: Observable<T>
): ObservableLife<T> {
    return observable.doOnSubscribe {
        view.showLoading()
    }.lifeOnMain(this)
}

fun <T> LifecycleOwner.wrapDialogLoading(
    view: IView,
    observable: Observable<T>,
    msg: String = "正在加载中",
    isCancelable: Boolean = true
): ObservableLife<T> {
    return observable.doOnSubscribe { view.showDialogLoading(msg, isCancelable) }
        .observeOn(AndroidSchedulers.mainThread()).doFinally { view.hideDialogLoading() }
        .lifeOnMain(this)
}

// 把具体功能作用影响范围最小的点上，减少耦合
fun RefreshLayout?.defaultHandleRefresh(success: Boolean = false, noMoreData: Boolean = false) {
    this?.apply {
        when (state) {
            RefreshState.Refreshing -> {
                when {
                    success && noMoreData -> finishRefresh(true)
                    success && !noMoreData -> finishRefreshWithNoMoreData()
                    !success -> finishRefresh(false)
                }
            }
            RefreshState.Loading -> {
                when {
                    success && noMoreData -> finishLoadMore(true)
                    success && !noMoreData -> finishLoadMoreWithNoMoreData()
                    !success -> finishLoadMore(false)
                }
            }
            else -> {
            }
        }
    }
}




