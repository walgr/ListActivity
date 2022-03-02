package com.wpf.listactivity.adapter

import android.widget.TextView
import androidx.core.content.ContextCompat
import com.base.listactivity.adapter.BaseListAdapter
import com.base.listactivity.adapter.BaseMultiItemAdapter
import com.base.listactivity.entity.BaseMixEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.wpf.listactivity.R
import com.wpf.listactivity.entity.MultiEntity

/**
 * Created by 王朋飞 on 2021/6/17.
 *
 */
class TestAdapterMulti2 : BaseMultiItemAdapter<MultiEntity>(2, R.layout.item_test_layout) {

//    init {
//        addChildClickViewIds(R.id.item)
//    }

    override fun convert(holder: BaseViewHolder, item: MultiEntity) {
        holder.getView<TextView>(R.id.lable).text = "标签2"
        holder.getView<TextView>(R.id.item).text = item.name
    }

    override fun sendRefreshEvent() {
        //需要实现发送消息
    }
}