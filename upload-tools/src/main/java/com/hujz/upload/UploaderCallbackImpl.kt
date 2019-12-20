package com.hujz.upload

/**
 * <pre>
 *     author : Hjz
 *     time   : 2019/12/05
 *     desc   : Image load callback
 * </pre>
 */
class UploaderCallbackImpl : UploaderCallback {

    private var onSuccess: ((fileUrl: String) -> Unit)? = null
    private var onFailed: (() -> Unit)? = null

    override fun onSuccess(fileUrl: String) {
        onSuccess?.invoke(fileUrl)
    }

    override fun onFailed() {
        onFailed?.invoke()
    }

    fun success(listener: (fileUrl: String) -> Unit) {
        onSuccess = listener
    }

    fun failed(listener: () -> Unit) {
        onFailed = listener
    }
}