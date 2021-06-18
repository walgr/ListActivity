package com.wpf.listactivity.adapter

import android.widget.TextView
import com.base.listactivity.adapter.BaseListAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wpf.listactivity.R

/**
 * Created by 王朋飞 on 2021/6/17.
 *
 */
class TestAdapterSingle : BaseListAdapter<String>(
    R.layout.item_test_layout
) {

    init {
        addChildClickViewIds(R.id.item)
    }

    override fun convert(holder: BaseViewHolder, item: String) {
        holder.getView<TextView>(R.id.item).text = item
    }

    override fun sendRefreshEvent() {
        //需要实现发送消息
    }
}