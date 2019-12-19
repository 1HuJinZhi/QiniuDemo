package com.hujz.base.delegate

import android.app.Application
import android.content.Context

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/19
 *     desc   :
 * </pre>
 */
interface AppLifecycle {
    fun attachBaseContext(base: Context)

    fun onCreate(application: Application)

    fun onLowMemory(application: Application)

    fun onTrimMemory(application: Application, level: Int)

    fun onTerminate(application: Application)
}