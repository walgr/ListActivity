package com.wpf.listactivity

import android.os.Bundle
import android.util.Log
import com.base.listactivity.BaseListActivity
import com.base.listactivity.adapter.BaseListAdapter
import com.wpf.listactivity.adapter.TestAdapterSingle

class NoRefreshSingleListTestActivity : BaseListActivity<String>(
    includeEmpty = false, includeHeader = false, includeLoadMore = false
) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onRefresh()
    }

    override fun preInitAdapter(): BaseListAdapter<String> {
        return TestAdapterSingle()
    }

    override fun onRefresh() {
        loadData(mutableListOf("1", "2", "3", "4"), true)
    }

    override fun onLoadMore() {
        loadData(mutableListOf("1", "2", "3", "4"), isRefresh = false, hasMore = true)
    }

    override fun sendRefreshEvent() {
        //需要实现发送消息
        Log.e("TAG", "发送刷新事件")
    }
}