package com.base.listactivity.listener

import com.base.listactivity.R

/**
 * Created by 王朋飞 on 2021/6/17.
 * 带默认一个RecyclerView
 */
interface NoRefreshListLayout<T>
    : BaseList<T> {

    fun getLayoutView(): Int {
        return R.layout.layout_recyclerview
    }
}