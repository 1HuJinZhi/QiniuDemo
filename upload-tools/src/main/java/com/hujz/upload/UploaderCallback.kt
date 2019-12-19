package com.hujz.upload

/**
 * <pre>
 *     author : Hjz
 *     time   : 2019/12/05
 *     desc   : Image load callback
 * </pre>
 */
interface UploaderCallback {
    fun onSuccess(fileUrl: String)
    fun onFailed()
}