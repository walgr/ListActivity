package com.wpf.listactivity.fragment

import android.os.Bundle
import android.view.View
import com.base.listactivity.fragment.BaseNoRefreshSingleListFragment
import com.base.listactivity.listener.BaseAdapter
import com.wpf.listactivity.adapter.TestAdapterSingle

/**
 * Created by 王朋飞 on 2021/6/21.
 *
 */
class NoRefreshSingleListTestFragment: BaseNoRefreshSingleListFragment<String>() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onRefresh()
    }

    override fun preInitAdapter(): BaseAdapter<String> {
        return TestAdapterSingle()
    }

    override fun onRefresh() {
        loadData(mutableListOf("1","2","3","4"), true)
    }

    override fun onLoadMore() {
        loadData(mutableListOf("1","2","3","4"), isRefresh = false, hasMore = true)
    }

    override fun sendRefreshEvent() {
        //需要实现发送消息
    }
}