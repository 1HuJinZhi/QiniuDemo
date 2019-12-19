package com.hujz.base

import android.os.Bundle
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hujz.network.R
import com.hujz.base.delegate.IRefreshMoreView
import com.hujz.base.network.rxhttp.parser.DefaultPageList
import com.scwang.smartrefresh.layout.constant.RefreshState
import kotlinx.android.synthetic.main.common_refresh_list.*

/**
 * <pre>
 *     @author : 18000
 *     time   : 2019/12/19
 *     desc   :
 * </pre>
 */
abstract class BasePageListActivity<AD : BaseQuickAdapter<ENTITY, BaseViewHolder>, ENTITY> :
    BaseRefreshActivity<DefaultPageList<ENTITY>>(),
    IRefreshMoreView<AD, DefaultPageList<ENTITY>> {

    var defaultPageNumber: Int = DEFAULT_PAGE_NUMBER
    var defaultPageSize: Int = DEFAULT_PAGE_SIZE
    var pageNumber: Int = defaultPageNumber
    var pageSize: Int = defaultPageSize
    var noMoreData: Boolean = false
    var mListAdapter: AD? = null
    var pageList: DefaultPageList<ENTITY>? = null


    override fun bindLayout(): Int {
        return R.layout.common_refresh_list
    }

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mSrlCommonRefresh?.apply {
            setEnableLoadMore(isEnableLoadMore())
            setOnRefreshListener {
                loadingType = LoadingType.NONE
                pageNumber = defaultPageNumber
                setEnableLoadMore(false)
                doBusiness(loadingType)
            }
            setOnLoadMoreListener {
                loadingType = LoadingType.NONE
                pageNumber++
                doBusiness(loadingType)
            }
        }
    }

    override fun accept(t: DefaultPageList<ENTITY>) {
        super.accept(t)
        pageList = t
        val itemCount = pageSize * pageNumber + (t.list?.size ?: 0)
        noMoreData = itemCount >= t.count
    }

    override fun run() {
        if (mListAdapter == null) {
            mRvCommonRefreshList.layoutManager = getLayoutManager()
            mListAdapter = getAdapter(pageList)
            mListAdapter?.bindToRecyclerView(mRvCommonRefreshList)
        } else {
            mSrlCommonRefresh?.apply {
                when (state) {
                    RefreshState.Refreshing -> {
                        mListAdapter?.setNewData(pageList?.list)
                    }
                    RefreshState.Loading -> {
                        mListAdapter?.addData(pageList?.list ?: listOf())
                    }
                    else -> {
                    }
                }
            }
        }
        businessComplete(true, noMoreData)
    }

    override fun isEnableLoadMore(): Boolean {
        return true
    }

}