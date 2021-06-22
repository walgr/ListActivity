package com.base.listactivity.adapter

import android.view.View
import com.base.listactivity.entity.BaseMixEntity
import com.base.listactivity.entity.BaseSelectEntity
import com.base.listactivity.listener.BaseAdapter
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * Created by 王朋飞 on 2021/6/18.
 *
 */
open class BaseMultiListAdapter(
    private val layoutAdapterList: Array<out BaseMultiItemAdapter<out BaseMixEntity>>,
    dataList: MutableList<BaseMixEntity>?
) : BaseMultiItemQuickAdapter<BaseMixEntity, BaseViewHolder>(dataList), BaseAdapter<BaseMixEntity> {

    init {
        initLayoutList()
    }

    //TODO 子View如果有相同id的点击、长按事件有问题
    override fun setOnItemChildClick(v: View, position: Int) {
        layoutAdapterList.find {
            it.itemType == getItemViewType(position)
                    && it.getChildClickViewIds().contains(v.id)
        }?.let {
            getOnItemChildClickListener()?.onItemChildClick(it, v, position)
        }
    }

    private fun initLayoutList() {
        layoutAdapterList.forEach {
            addItemType(it.itemType, it.layout)
            it.getChildClickViewIds().forEach { id ->
                addChildClickViewIds(id)
            }
            it.getChildLongClickViewIds().forEach { id ->
                addChildLongClickViewIds(id)
            }
        }
    }

    override fun convert(holder: BaseViewHolder, item: BaseMixEntity) {
        layoutAdapterList.find { it.itemType == item.itemType }?.convert(holder, item)
    }

    //多选时用来获取选中的ID
    var selectedDataList: MutableList<BaseMixEntity>? = null

    //单选时被选中的对象
    var selectData: BaseMixEntity? = null

    // 备用数据
    var obj: Any? = null

    // 备用数据
    var obj2: Any? = null

    // 备用整型
    var what = 0

    // 备用boolean类型
    var flag = false

    open fun addAllSelectItem(list: MutableList<BaseMixEntity>?) {
        if (selectedDataList == null) {
            selectedDataList = mutableListOf()
        }
        list?.let {
            selectedDataList?.addAll(list)
        }
    }

    open fun addSelectItem(t: BaseMixEntity) {
        if (selectedDataList == null) {
            selectedDataList = mutableListOf()
        }
        selectedDataList?.add(t)
    }

    open fun removeSelectItem(t: BaseMixEntity) {
        if (selectedDataList == null) {
            selectedDataList = mutableListOf()
        }
        selectedDataList?.remove(t)
    }

    open fun checkSelectItem(t: BaseMixEntity): Boolean {
        if (selectedDataList == null) {
            selectedDataList = mutableListOf()
        }
        return selectedDataList?.contains(t) ?: false
    }

    open fun getSelectSize(): Int {
        return selectedDataList?.size ?: 0
    }

    open fun clearAllSelect() {
        if (selectedDataList != null) {
            selectedDataList?.clear()
        }
    }

    open fun selectAll() {
        if (selectedDataList != null) {
            selectedDataList = mutableListOf()
        }
        selectedDataList?.addAll(data)
    }

    override fun appendToList(data: MutableList<BaseMixEntity>?) {
        if (data == null || data.isEmpty()) {
            return
        }
        this.data.addAll(data)
    }

    override fun appendAnyToList(data: MutableList<*>?) {
        if (data == null || data.isEmpty()) {
            return
        }
        for (d in data) {
            if (d is BaseMixEntity) {
                this.data.add(d)
            }
        }
    }

    open fun appendToList(list: MutableList<BaseMixEntity>?, position: Int) {
        if (list == null || list.size == 0) {
            return
        }
        data.addAll(position, list)
    }

    open fun appendToList(t: BaseMixEntity, position: Int) {
        data.add(position, t)
    }

    open fun addToList(t: BaseMixEntity) {
        data.add(t)
    }

    open fun updateData(t: BaseMixEntity, position: Int) {
        data[position] = t
    }

    open fun resumeRemoveItemNotify(position: Int) {
        sendRefreshEvent()
        data.removeAt(position)
        notifyDataSetChanged()
    }

    open fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemChanged(position)
    }

    open fun removeItemAllNotify(position: Int) {
        data.removeAt(position)
        notifyDataSetChanged()
    }

    open fun removeItemNoIty(position: Int) {
        data.removeAt(position)
    }

    open fun removeItems(position: Int, count: Int) {
        var countTmp = count
        while (count > 0) {
            data.removeAt(position)
            countTmp--
        }
    }

    open fun removeItemNoNotify(position: Int) {
        data.removeAt(position)
    }

    override fun isEmpty(): Boolean {
        return data.size == 0
    }

    override fun getItem(position: Int): BaseMixEntity {
        return data[position]
    }

    open fun getSelectList(): MutableList<BaseMixEntity>? {
        selectedDataList = mutableListOf()
        for (i in data.indices) {
            val t: BaseMixEntity = data[i]
            if ((t as BaseSelectEntity).select) {
                selectedDataList?.add(data[i])
            }
        }
        return selectedDataList
    }

    open fun getSelectListPosition(): MutableList<Int>? {
        val selectPosition: MutableList<Int> = ArrayList()
        selectedDataList = mutableListOf()
        for (i in data.indices) {
            val t: BaseMixEntity = data[i]
            if ((t as BaseSelectEntity).select) {
                selectedDataList?.add(data[i])
                selectPosition.add(i)
            }
        }
        return selectPosition
    }

    open fun showOrHideSelect(isShow: Boolean) {
        for (i in data.indices) {
            val t: BaseMixEntity = data[i]
            (t as BaseSelectEntity).isShowSelect = isShow
        }
        notifyDataSetChanged()
    }

    //得到单选时被选中的对象
    open fun getSelectedItem(): BaseMixEntity? {
        return if (selectData == null) {
            null
        } else selectData
    }

    //设置单选时被选中的对象
    open fun setSelectedItem(position: Int) {
        if (selectData != null) {
            (selectData as BaseSelectEntity).select = false
        }
        selectData = data[position]
        (selectData as BaseSelectEntity).select = true
        notifyDataSetChanged()
    }

    override fun clear() {
        data.clear()
    }

    // 带动画效果的
    /**
     * 从position开始删除，删除
     *
     * @param position
     * @param itemCount 删除的数目
     */
    open fun removeAll(position: Int, itemCount: Int) {
        for (i in 0 until itemCount) {
            data.removeAt(position)
        }
        notifyItemRangeRemoved(position, itemCount)
    }

    open fun add(t: BaseMixEntity, position: Int) {
        data.add(position, t)
        notifyItemInserted(position)
    }

    open fun addAll(list: MutableList<BaseMixEntity>?, position: Int) {
        list?.let {
            data.addAll(position, list)
            notifyItemRangeInserted(position, list.size)
        }
    }

    // 销毁adapter
    open fun destroy() {
        layoutAdapterList.forEach {
            it.destroy()
        }
    }

    /**
     * 自己发送刷新消息
     */
    fun sendRefreshEvent() {
        layoutAdapterList.forEach {
            it.sendRefreshEvent()
        }
    }

    override fun getAdapter(): BaseQuickAdapter<BaseMixEntity, BaseViewHolder> {
        return this
    }
}
