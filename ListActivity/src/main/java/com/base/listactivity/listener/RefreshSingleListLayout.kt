package com.base.listactivity.listener

import android.view.View
import com.base.listactivity.R

/**
 * Created by 王朋飞 on 2021/6/17.
 * 带默认一个RecyclerView和RefreshView
 */
interface RefreshSingleListLayout<T>
    : BaseList<T> {

    fun getLayoutView(): Int {
        return R.layout.layout_recyclerview_with_refresh
    }

    /**
     * 刷新HeaderView
     */
    fun getRefreshHeaderView(): View? {
        return null
    }

    /**
     * 上拉加载View
     */
    fun getRefreshLoadMoreView(): View? {
        return null
    }
}