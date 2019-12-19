package com.hujz.upload

/**
 * <pre>
 *     author : Hjz
 *     time   : 2019/12/05
 *     desc   : Image load callback
 * </pre>
 */
interface UploaderListCallback {
    fun onSingleSuccess(fileUrl: String)
    fun onSuccess(fileUrl: List<String>)
    fun onFailed(index: Int = -1)
}