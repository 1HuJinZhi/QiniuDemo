package com.hujz.base.network.rxhttp.parser

import com.hujz.base.REQUEST_DATA_OK
import rxhttp.wrapper.entity.ParameterizedTypeImpl
import rxhttp.wrapper.exception.ParseException
import rxhttp.wrapper.parse.AbstractParser
import java.io.IOException

/**
 * <pre>
 *     author : Hjz
 *     time   : 2019/12/13
 *     desc   :
 * </pre>
 * [com.hujz.network.base.network.rxhttp.Response]
 */
class DefaultNoDataParser : AbstractParser<DefaultResponse<Any>>() {

    @Throws(IOException::class)
    override fun onParse(response: okhttp3.Response): DefaultResponse<Any> {
        val type = ParameterizedTypeImpl.get(DefaultResponse::class.java, mType)
        val data = convert<DefaultResponse<Any>>(response, type)
        if (data.code != REQUEST_DATA_OK) {
            throw ParseException(data.code.toString(), data.msg, response)
        }
        return data
    }

}