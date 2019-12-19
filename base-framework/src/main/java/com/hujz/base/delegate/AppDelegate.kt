package com.hujz.base.delegate

import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import com.billy.android.loading.Gloading
import com.blankj.utilcode.util.Utils
import com.hujz.base.imageLoader
import com.hujz.base.widget.statusview.SimpleGloadingAdapter
import com.hujz.imageloader.glide.GlideLoaderStrategy
import com.hujz.network.BuildConfig
import com.hujz.network.R
import com.scwang.smartrefresh.horizontal.SmartRefreshHorizontal
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.footer.ClassicsFooter
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import rxhttp.wrapper.param.RxHttp

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/19
 *     desc   :
 * </pre>
 */
class AppDelegate private constructor() : AppLifecycle {

    companion object {
        val instance: AppDelegate by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) { AppDelegate() }
    }

    override fun attachBaseContext(base: Context) {

    }

    override fun onCreate(application: Application) {
        imageLoader.init(GlideLoaderStrategy(R.color.placeholder))
        Utils.init(application)
        RxHttp.setDebug(BuildConfig.DEBUG)
        Gloading.debug(BuildConfig.DEBUG)
        Gloading.initDefault(SimpleGloadingAdapter())
        initSmartRefresh()
    }

    override fun onLowMemory(application: Application) {
        imageLoader.clearMemoryCache(application)
    }

    override fun onTrimMemory(application: Application, level: Int) {
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN)
            imageLoader.clearMemoryCache(application)
    }

    override fun onTerminate(application: Application) {

    }

    private fun initSmartRefresh() {
        SmartRefreshLayout.setDefaultRefreshInitializer { context, layout ->
            layout.setEnableLoadMoreWhenContentNotFull(false)
            layout.setEnableScrollContentWhenRefreshed(false)
        }
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            ClassicsHeader(
                context
            )
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter(
                context
            )
        }
        SmartRefreshHorizontal.setDefaultRefreshInitializer { context, layout ->
            layout.setEnableLoadMoreWhenContentNotFull(false)
            layout.setEnableScrollContentWhenRefreshed(false)
        }
        SmartRefreshHorizontal.setDefaultRefreshHeaderCreator { context, layout ->
            ClassicsHeader(
                context
            )
        }
        SmartRefreshHorizontal.setDefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter(
                context
            )
        }
    }
}