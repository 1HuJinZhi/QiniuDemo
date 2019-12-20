package com.hujz.upload

/**
 * <pre>
 *     author : Hjz
 *     time   : 2019/12/05
 *     desc   : Image load callback
 * </pre>
 */
interface UploaderListCallback {
    fun onSuccess(fileUrl: List<String>, index: Int)
    fun onFailed(index: Int = -1)
}