package com.hujz.base.delegate

import com.hujz.base.network.rxhttp.OnError
import io.reactivex.Observable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/17
 *     desc   :
 * </pre>
 */
interface IRefreshView<T> : Consumer<T>, Action {

    fun isEnableRefresh(): Boolean

//    fun isEnableLoadMore(): Boolean

    fun isHorizontalRefresh(): Boolean

    fun getObservable(): Observable<T>

    /**
     * repeat callback
     */
    fun businessCallback(data: T)

    fun businessComplete(success: Boolean = true, noMoreData: Boolean = false)

    fun handleException(): OnError
}