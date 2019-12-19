package com.hujz.base.network.rxhttp

import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.Utils
import com.hujz.network.R
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/17
 *     desc   :
 * </pre>
 */
object ExceptionHelper {

    fun handleNetworkException(throwable: Throwable): String? {
        var stringId = -1
        when (throwable) {
            is UnknownHostException -> {
                stringId = if (NetworkUtils.isConnected()) R.string.network_error
                else R.string.notify_no_network
            }
            is SocketTimeoutException, is TimeoutException -> {
                stringId = R.string.time_out_please_try_again_later
            }
            is ConnectException -> {
                stringId = R.string.esky_service_exception
            }
        }
        return if (stringId == -1) null else Utils.getApp().getString(stringId)
    }
}