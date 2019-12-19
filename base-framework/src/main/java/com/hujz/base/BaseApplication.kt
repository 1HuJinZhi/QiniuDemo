package com.hujz.base

import android.app.Application
import android.content.Context
import com.hujz.base.delegate.AppDelegate

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/19
 *     desc   :
 * </pre>
 */
open class BaseApplication : Application() {

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        AppDelegate.instance.attachBaseContext(base)
    }

    override fun onCreate() {
        super.onCreate()
        AppDelegate.instance.onCreate(this)
    }

    override fun onLowMemory() {
        super.onLowMemory()
        AppDelegate.instance.onLowMemory(this)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        AppDelegate.instance.onTrimMemory(this, level)
    }

    override fun onTerminate() {
        super.onTerminate()
        AppDelegate.instance.onTerminate(this)
    }

}