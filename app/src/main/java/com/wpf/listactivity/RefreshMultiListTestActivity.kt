package com.wpf.listactivity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.base.listactivity.BaseRefreshMultiListActivity
import com.base.listactivity.adapter.BaseMultiItemAdapter
import com.base.listactivity.entity.BaseMixEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.wpf.listactivity.adapter.TestAdapterMulti1
import com.wpf.listactivity.adapter.TestAdapterMulti2
import com.wpf.listactivity.entity.MultiEntity

class RefreshMultiListTestActivity : BaseRefreshMultiListActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onRefresh()
    }

    override fun preInitAdapter(): Array<BaseMultiItemAdapter<out BaseMixEntity>> {
        return arrayOf(TestAdapterMulti1(), TestAdapterMulti2())
    }

    override fun onRefresh() {
        loadData(
            mutableListOf(
                MultiEntity(1, "1"),
                MultiEntity(2, "2"),
                MultiEntity(1, "3"),
                MultiEntity(2, "4"),
                MultiEntity(1, "5"),
                MultiEntity(2, "6"),
                MultiEntity(1, "7"),
                MultiEntity(2, "8"),
                MultiEntity(1, "9"),
                MultiEntity(2, "10"),
                MultiEntity(1, "11"),
                MultiEntity(2, "12"),
                MultiEntity(1, "13"),
                MultiEntity(2, "14"),
                MultiEntity(1, "15"),
                MultiEntity(2, "16"),
                MultiEntity(1, "17"),
                MultiEntity(2, "18"),
                MultiEntity(1, "19"),
                MultiEntity(2, "20"),
                MultiEntity(1, "21"),
                MultiEntity(2, "22"),
                MultiEntity(1, "23"),
                MultiEntity(2, "24"),
                MultiEntity(2, "25"),
                MultiEntity(2, "26"),
                MultiEntity(2, "27"),
                MultiEntity(2, "28"),
                MultiEntity(2, "29"),
                MultiEntity(2, "30"),
                MultiEntity(2, "31")
            ), true, hasMore = true
        )
    }

    override fun onLoadMore() {
        loadData(
            mutableListOf(
                MultiEntity(1, "11"),
                MultiEntity(2, "22"),
                MultiEntity(1, "11"),
                MultiEntity(2, "22"),
                MultiEntity(1, "11"),
                MultiEntity(2, "22"),
                MultiEntity(1, "11"),
                MultiEntity(2, "22"),
                MultiEntity(1, "11"),
                MultiEntity(2, "22")
            ), isRefresh = false, hasMore = true
        )
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