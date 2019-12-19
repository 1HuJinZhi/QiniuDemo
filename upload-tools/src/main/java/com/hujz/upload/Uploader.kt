package com.hujz.upload

import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.RegexUtils
import com.qiniu.android.storage.UploadManager
import java.util.*

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/19
 *     desc   :
 * </pre>
 */
class Uploader private constructor() {

    companion object {
        val instance: Uploader by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { Uploader() }
    }

    fun upload(
        filePath: String,
        prefix: String,
        key: String = getKey(filePath),
        token: String,
        callback: UploaderCallback
    ) {
        if (!RegexUtils.isURL(prefix)) callback.onFailed()
        UploadManager().put(filePath, key, token, { resultKey, info, _ ->
            if (info.isOK) callback.onSuccess(resultKey)
            else callback.onFailed()
        }, null)
    }

    fun uploadList(
        filePathList: List<String>,
        prefix: String,
        keyList: List<String>? = null,
        token: String,
        callback: UploaderListCallback
    ) {
        if (!RegexUtils.isURL(prefix)) callback.onFailed()
        val fullKeyList = mutableListOf<String>()
        fullKeyList.addAll(keyList ?: listOf())
        filePathList.forEachIndexed { index: Int, s: String ->
            if (fullKeyList.lastIndex <= index) return@forEachIndexed
            fullKeyList.add(getKey(s))
        }
        val fileUrlList = mutableListOf<String>()
        var count = 0
        fullKeyList.forEachIndexed { index: Int, s: String ->
            UploadManager().put(filePathList[index], s, token, { resultKey, info, res ->
                count++
                if (info.isOK) {
                    val fileUrl = res.getString("key")
                    LogUtils.e("resultKey:$resultKey")
                    LogUtils.e("fileUrl:$fileUrl")
                    fileUrlList[index] = fileUrl
                    callback.onSingleSuccess(fileUrl)
                    if (count == fullKeyList.lastIndex) callback.onSuccess(fileUrlList)
                } else {
                    callback.onFailed()
                    LogUtils.e(info.error)
                }
            }, null)
        }

    }

    private fun getKey(filePath: String): String {
        return AppUtils.getAppPackageName() + (UUID.randomUUID().toString() + System.currentTimeMillis()).hashCode() + filePath
    }
}


