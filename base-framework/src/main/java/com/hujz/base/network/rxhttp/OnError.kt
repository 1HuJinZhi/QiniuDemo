package com.hujz.base.network.rxhttp

import io.reactivex.functions.Consumer

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/17
 *     desc   :
 * </pre>
 */
interface OnError : Consumer<Throwable> {

    override fun accept(t: Throwable) {
        onError(ErrorInfo(t))
    }

    @Throws(Exception::class)
    fun onError(error: ErrorInfo)

}