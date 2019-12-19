package com.hujz.base.delegate

import androidx.recyclerview.widget.RecyclerView

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/18
 *     desc   :
 * </pre>
 */
interface IRefreshMoreView<AD, T> :
    IRefreshView<T> {

    fun getAdapter(data: T?): AD

    fun getLayoutManager(): RecyclerView.LayoutManager

    fun isEnableLoadMore(): Boolean
}