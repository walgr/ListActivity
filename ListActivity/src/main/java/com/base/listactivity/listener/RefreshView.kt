package com.base.listactivity.listener

import android.view.View

/**
 * Created by 王朋飞 on 2021/6/21.
 *
 */
interface RefreshView {
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