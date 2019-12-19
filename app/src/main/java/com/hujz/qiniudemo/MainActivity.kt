package com.hujz.qiniudemo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.CollectionUtils
import com.blankj.utilcode.util.LogUtils
import com.hujz.upload.Uploader
import com.hujz.upload.UploaderListCallback
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .forResult(1)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) {
            return
        }
        if (requestCode == 1) {
            val pathList = PictureSelector.obtainMultipleResult(data)
            Uploader.instance.uploadList(
                filePathList = pathList.map { it.path },
                prefix = "http://q1ocmbum8.bkt.clouddn.com/",
                token = "LD0KJv0PNx2FuLg6TULl6pRYcc-aJvOYzTRl-87q:jkefU8j-r7qpo0UKtTQufGF9EdM=:eyJzY29wZSI6InJtYml0aW9uMyIsImRlYWRsaW5lIjoxNTc2NzQyOTAzfQ==",
                callback = object : UploaderListCallback {
                    override fun onSuccess(fileUrl: List<String>) {
                        LogUtils.e("onSuccess：" + CollectionUtils.toString(fileUrl))
                    }

                    override fun onFailed() {
                        LogUtils.e("上传失败")
                    }

                    override fun onSingleSuccess(fileUrl: String) {
                        LogUtils.e("onSingleSuccess：$fileUrl")
                    }
                }
            )
        }
    }
}
