package com.hujz.upload.qiniu

import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.RegexUtils
import com.hujz.upload.UploaderCallbackImpl
import com.hujz.upload.UploaderListCallbackImpl
import com.qiniu.android.storage.UpCancellationSignal
import com.qiniu.android.storage.UpProgressHandler
import com.qiniu.android.storage.UploadManager
import com.qiniu.android.storage.UploadOptions
import java.util.*

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/19
 *     desc   :
 * </pre>
 */

class QiniuUploader private constructor() {

    private val mUploadManager by lazy { UploadManager() }

    companion object {
        val instance: QiniuUploader by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { QiniuUploader() }
    }

    fun upload(
        filePath: String,
        prefix: String,
        key: String = getKey(filePath),
        token: String,
        uploaderCallback: UploaderCallbackImpl.() -> Unit,
        progressCallback: UpProgressHandler? = null,
        cancelCallback: UpCancellationSignal? = null
    ) {
        val callback = UploaderCallbackImpl().apply(uploaderCallback)
        if (!RegexUtils.isURL(prefix)) callback.onFailed()
        mUploadManager.put(filePath, key, token, { resultKey, info, _ ->
            if (info.isOK) callback.onSuccess(prefix + resultKey)
            else callback.onFailed()
        }, UploadOptions(null, null, false, progressCallback, cancelCallback))
    }

    fun uploadList(
        filePathList: List<String>,
        prefix: String,
        token: String,
        uploaderListCallback: UploaderListCallbackImpl.() -> Unit,
        keyList: List<String>? = null,
        progressCallbackList: List<UpProgressHandler>? = null,
        cancelCallbackList: List<UpCancellationSignal>? = null
    ) {
        val callback = UploaderListCallbackImpl().apply(uploaderListCallback)
        if (!RegexUtils.isURL(prefix)) callback.onFailed()
        val fullKeyList = mutableListOf<String>()
        fullKeyList.addAll(keyList ?: listOf())
        val fullProgressCallbackList = mutableListOf<UpProgressHandler?>()
        fullProgressCallbackList.addAll(progressCallbackList ?: listOf())
        val fullCancelCallbackList = mutableListOf<UpCancellationSignal?>()
        fullCancelCallbackList.addAll(cancelCallbackList ?: listOf())
        filePathList.forEachIndexed { index: Int, s: String ->
            if (fullKeyList.lastIndex >= index) return@forEachIndexed
            fullKeyList.add(getKey(s))
            fullProgressCallbackList.add(null)
            fullCancelCallbackList.add(null)
        }
        val fileUrlList = MutableList(fullKeyList.size) { "" }
        fullKeyList.forEachIndexed { index: Int, s: String ->
            mUploadManager.put(
                filePathList[index],
                s,
                token,
                { resultKey, info, _ ->
                    if (info.isOK) {
                        fileUrlList[index] = prefix + resultKey
                        callback.onSuccess(fileUrlList, index)
                    } else {
                        callback.onFailed(index)
                    }
                },
                UploadOptions(
                    null,
                    null,
                    false,
                    fullProgressCallbackList[index],
                    fullCancelCallbackList[index]
                )
            )
        }
    }

    private fun getKey(filePath: String): String {
        return UUID.randomUUID().toString() + FileUtils.getFileMD5ToString(filePath) + System.currentTimeMillis()
    }
}


