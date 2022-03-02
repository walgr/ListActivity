package com.base.listactivity.adapter

import com.base.listactivity.entity.BaseMixEntity
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * Created by 王朋飞 on 2021/6/18.
 *
 */
abstract class BaseMultiItemAdapter<T : BaseMixEntity>(val itemType: Int, val layout: Int)
    : BaseQuickAdapter<T, BaseViewHolder>(layout) {

    public override fun convert(holder: BaseViewHolder, item: T) {

    }

    fun destroy() {}

    /**
     * 实现此方法发送消息刷新列表
     */
    open fun sendRefreshEvent() {

    }
}