package com.hujz.base.widget.statusview

import android.view.View
import com.billy.android.loading.Gloading

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/13
 *     desc   : easy
 * </pre>
 */
class SimpleGloadingAdapter : Gloading.Adapter {

    override fun getView(holder: Gloading.Holder, convertView: View?, status: Int): View {
        return when (status) {
            Gloading.STATUS_EMPTY_DATA -> GloadingEmptyView(
                holder.context
            )
            Gloading.STATUS_LOADING -> GloadingLoadingView(
                holder.context
            )
            Gloading.STATUS_LOAD_FAILED -> GloadingFailedView(
                holder.context
            )
            Gloading.STATUS_LOAD_SUCCESS -> GloadingSuccessView(
                holder.context
            )
            else -> GloadingSuccessView(holder.context)
        }
    }

}