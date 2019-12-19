package com.hujz.base.network.rxhttp.parser

import com.google.gson.annotations.SerializedName

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/13
 *     desc   :
 * </pre>
 */
open class DefaultResponse<T> {

    @SerializedName("status")
    val code: Int = 0
    @SerializedName("message")
    val msg: String? = null
    var data: T? = null

}