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
 * [DefaultResponse]
 */
class DefaultParser<T>(type: Class<T>) : AbstractParser<T>(type) {

    @Throws(IOException::class)
    override fun onParse(response: okhttp3.Response): T {
        val type = ParameterizedTypeImpl.get(DefaultResponse::class.java, mType)
        val data = convert<DefaultResponse<T>>(response, type)
        val t = data.data
        if (data.code != REQUEST_DATA_OK || t == null) {
            throw ParseException(data.code.toString(), data.msg, response)
        }
        return t
    }

}