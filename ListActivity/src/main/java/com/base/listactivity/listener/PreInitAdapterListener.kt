package com.base.listactivity.listener

/**
 * Created by 王朋飞 on 2021/6/17.
 *
 */
interface PreInitAdapterListener<T> {

    fun preInitAdapter(): BaseAdapter<T>?
}