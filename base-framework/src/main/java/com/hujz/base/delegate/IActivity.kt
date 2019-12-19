package com.hujz.base.delegate

import android.os.Bundle

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/12
 *     desc   :
 * </pre>
 */
interface IActivity : IView {

    fun initData(savedInstanceState: Bundle?)

    fun initView(savedInstanceState: Bundle?)

    fun initImmersionBar()
}

