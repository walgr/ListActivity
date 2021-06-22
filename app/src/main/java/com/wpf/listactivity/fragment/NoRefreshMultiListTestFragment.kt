package com.wpf.listactivity.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.base.listactivity.BaseListActivity
import com.base.listactivity.adapter.BaseMultiItemAdapter
import com.base.listactivity.entity.BaseMixEntity
import com.base.listactivity.fragment.BaseListFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wpf.listactivity.adapter.TestAdapterMulti1
import com.wpf.listactivity.adapter.TestAdapterMulti2
import com.wpf.listactivity.entity.MultiEntity

/**
 * Created by 王朋飞 on 2021/6/21.
 *
 */
class NoRefreshMultiListTestFragment: BaseListFragment<BaseMixEntity>(
    includeEmpty = false,
    includeHeader = false,
    includeLoadMore = false
) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onRefresh()
    }

    override fun preInitMultiAdapter(): Array<out BaseMultiItemAdapter<out BaseMixEntity>> {
        return arrayOf(TestAdapterMulti1(), TestAdapterMulti2())
    }

    override fun onRefresh() {
        loadData(
            mutableListOf(
                MultiEntity(1, "1"),
                MultiEntity(2, "2")
            ), true, hasMore = true
        )
    }

    override fun onLoadMore() {
        loadData(
            mutableListOf(
                MultiEntity(1, "11"),
                MultiEntity(2, "22")
            ), isRefresh = false, hasMore = true
        )
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        super.onItemClick(adapter, view, position)
        Toast.makeText(this.mContext, "点击了${position+1}行", Toast.LENGTH_SHORT).show()
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        super.onItemChildClick(adapter, view, position)
        Toast.makeText(this.mContext, "点击了${position+1}行-View:${view}", Toast.LENGTH_SHORT).show()
    }

    override fun sendRefreshEvent() {
        //需要实现发送消息

    }
}