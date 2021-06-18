package com.base.listactivity.listener

import com.base.listactivity.adapter.BaseMultiItemAdapter
import com.base.listactivity.entity.BaseMixEntity

/**
 * Created by 王朋飞 on 2021/6/17.
 *
 */
interface PreInitMultiAdapterListener {

    fun preInitAdapter(): Array<BaseMultiItemAdapter<out BaseMixEntity>>
}