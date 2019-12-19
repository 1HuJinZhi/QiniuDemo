package com.hujz.base.widget.statusview

import android.content.Context
import android.widget.FrameLayout
import com.hujz.network.R

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/13
 *     desc   :
 * </pre>
 */
class GloadingNetworkErrorView(context: Context) : FrameLayout(context) {

    init {
        inflate(context, R.layout.view_network_error_page, this)
    }

}
