package com.base.listactivity.listener

import android.content.Intent
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener

/**
 * Created by 王朋飞 on 2021/6/21.
 * 中转的都要实现
 */
interface Transit<T> : OnRefreshListener, OnLoadMoreListener, SendRefreshListener {

    fun loadData(
        data: MutableList<T>?,
        isRefresh: Boolean,
        hasMore: Boolean,
        emptyMessage: String?
    )

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
}