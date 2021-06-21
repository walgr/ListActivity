package com.wpf.listactivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.base.listactivity.BaseRefreshSingleListActivity
import com.base.listactivity.adapter.BaseListAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wpf.listactivity.adapter.TestAdapterSingle

class RefreshSingleListTestActivity : BaseRefreshSingleListActivity<String>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onRefresh()
    }

    override fun preInitAdapter(): BaseListAdapter<String> {
        return TestAdapterSingle()
    }

    override fun onRefresh() {
        loadData(mutableListOf("1","2","3","4","5"), true, hasMore = true)
    }

    override fun onLoadMore() {
        loadData(mutableListOf("1","2","3","4"), isRefresh = false, hasMore = true)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        super.onItemClick(adapter, view, position)
        Toast.makeText(this, "点击了${position+1}行", Toast.LENGTH_SHORT).show()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        super.onItemChildClick(adapter, view, position)
        Toast.makeText(this, "点击了${position+1}行-View:${view}", Toast.LENGTH_SHORT).show()
    }

    override fun sendRefreshEvent() {
        //需要实现发送消息
    }
}