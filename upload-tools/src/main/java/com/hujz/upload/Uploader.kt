package com.hujz.upload

import com.blankj.utilcode.util.FileUtils
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
            if (info.isOK) callback.onSuccess(prefix + resultKey)
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
            if (fullKeyList.lastIndex >= index) return@forEachIndexed
            fullKeyList.add(getKey(s))
        }
        val fileUrlList = MutableList(fullKeyList.size) { "" }
        var count = 0
        var atLast: Boolean
        fullKeyList.forEachIndexed { index: Int, s: String ->
            UploadManager().put(filePathList[index], s, token, { resultKey, info, _ ->
                count++
                atLast = count >= fullKeyList.lastIndex + 1
                if (info.isOK) {
                    val fileUrl = prefix + resultKey
                    fileUrlList[index] = fileUrl
                    callback.onSingleSuccess(fileUrl)
                    if (atLast) callback.onSuccess(fileUrlList)
                } else if (atLast) {
                    callback.onFailed(index)
                }
            }, null)
        }

    }

    private fun getKey(filePath: String): String {
        return UUID.randomUUID().toString() + FileUtils.getFileMD5ToString(filePath) + System.currentTimeMillis()
    }
}


