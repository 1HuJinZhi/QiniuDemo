package com.hujz.base.network.rxhttp

import com.google.gson.JsonSyntaxException
import rxhttp.wrapper.exception.HttpStatusCodeException
import rxhttp.wrapper.exception.ParseException

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/17
 *     desc   :
 * </pre>
 */
class ErrorInfo(val throwable: Throwable) {

    var errorCode: Int? = null
    var errorMsg: String? = null

    init {
        errorMsg = ExceptionHelper.handleNetworkException(throwable)
        val localizedMessage = throwable.localizedMessage
        when (throwable) {
            is HttpStatusCodeException -> {
                if ("416" == localizedMessage) errorMsg = "请求范围不符合要求"
            }
            is JsonSyntaxException -> {
                errorMsg = "数据解析失败,请稍后再试"
            }
            is ParseException -> {
                errorCode = localizedMessage?.toInt()
                errorMsg = throwable.message
                if (errorMsg.isNullOrEmpty()) errorMsg = localizedMessage
            }
        }
    }

}