package com.hujz.upload

/**
 * <pre>
 *     author : Hjz
 *     time   : 2019/12/05
 *     desc   : Image load callback
 * </pre>
 */

class UploaderListCallbackImpl : UploaderListCallback {

    private var onSuccess: ((fileUrl: List<String>, index: Int) -> Unit)? = null
    private var onFailed: ((index: Int) -> Unit)? = null

    override fun onSuccess(fileUrl: List<String>, index: Int) {
        onSuccess?.invoke(fileUrl, index)
    }

    override fun onFailed(index: Int) {
        onFailed?.invoke(index)
    }

    fun success(listener: (fileUrl: List<String>, index: Int) -> Unit) {
        onSuccess = listener
    }

    fun failed(listener: (index: Int) -> Unit) {
        onFailed = listener
    }
}