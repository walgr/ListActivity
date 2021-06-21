package com.base.listactivity.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.base.listactivity.entity.BaseMixEntity
import com.base.listactivity.listener.BaseAdapter
import com.base.listactivity.listener.BaseList
import com.base.listactivity.listener.PreInitMultiAdapterListener
import com.base.listactivity.widget.BaseNoRefreshMultiListView
import com.base.listactivity.widget.EmptyLayout

/**
 * Created by 王朋飞 on 2021/6/17.
 * 单类型的列表Activity基类
 */
abstract class BaseNoRefreshMultiListFragment
    : BaseFragment<BaseMixEntity>(),
    PreInitMultiAdapterListener,
    BaseList<BaseMixEntity> {

    override var mContext: Context? = this.context
    override var mEmptyLayout: EmptyLayout? = null
    override var mSwipeToLoadLayout: SwipeToLoadLayout? = null
    override var mRecyclerView: RecyclerView? = null
    override var mLayoutManager: RecyclerView.LayoutManager? = null
    override var mBaseAdapter: BaseAdapter<BaseMixEntity>? = null
    override var REFRESH_REQUEST_CODE: Int = 0
    override var netErrorMsg: String = ""
    override var serverErrorMsg: String = ""
    override var retryListener: EmptyLayout.OnRetryListener? = null

    open var baseNoRefreshListView: BaseNoRefreshMultiListView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (mContext == null) mContext = container?.context
        baseNoRefreshListView = initBaseView()
        return baseNoRefreshListView
    }

    override fun loadData(
        data: MutableList<BaseMixEntity>?,
        isRefresh: Boolean,
        hasMore: Boolean,
        emptyMessage: String?
    ) {
        baseNoRefreshListView?.loadData(data, isRefresh, hasMore, emptyMessage)
    }

    open fun initBaseView(): BaseNoRefreshMultiListView {
        return BaseNoRefreshMultiListView(
            mContext!!,
            preInitAdapterListener = this,
            sendRefreshListener = this,
            onItemClickListener = this,
            onItemLongClickListener = this,
            onItemChildClickListener = this,
            onItemChildLongClickListener = this
        ).also {
            it.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        baseNoRefreshListView?.onActivityResultToRefresh(requestCode, resultCode, data)
    }
}