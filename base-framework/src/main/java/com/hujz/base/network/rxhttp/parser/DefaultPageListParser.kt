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
 * [DefaultResponse],[DefaultPageList]
 */
class DefaultPageListParser<T>(type: Class<T>) : AbstractParser<DefaultPageList<T>>(type) {

    @Throws(IOException::class)
    override fun onParse(response: okhttp3.Response): DefaultPageList<T> {
        val type = ParameterizedTypeImpl.get(
            DefaultResponse::class.java,
            DefaultPageList::class.java,
            mType
        )
        val data = convert<DefaultResponse<DefaultPageList<T>>>(response, type)
        val t = data.data
        if (data.code != REQUEST_DATA_OK || t == null) {
            throw ParseException(data.code.toString(), data.msg, response)
        }
        return t
    }

}