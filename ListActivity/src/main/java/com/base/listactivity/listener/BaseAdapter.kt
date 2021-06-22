package com.base.listactivity.listener

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.BaseListenerImp
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * Created by 王朋飞 on 2021/6/18.
 *
 */
interface BaseAdapter<T> : BaseListenerImp {

    /**
     * 获取Adapter
     */
    fun getAdapter(): BaseQuickAdapter<T, BaseViewHolder>

    /**
     * 清空所有数据
     */
    fun clear()

    /**
     * 添加列表数据
     */
    fun appendToList(data: MutableList<T>?)

    fun appendAnyToList(data: MutableList<*>?)

    fun notifyDataSetChanged()

    fun isEmpty(): Boolean
}