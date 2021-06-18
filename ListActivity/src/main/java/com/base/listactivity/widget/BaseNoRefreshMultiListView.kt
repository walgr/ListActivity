package com.base.listactivity.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.base.listactivity.R
import com.base.listactivity.adapter.BaseMultiListAdapter
import com.base.listactivity.entity.BaseMixEntity
import com.base.listactivity.listener.*
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnItemLongClickListener

/**
 * Created by 王朋飞 on 2021/6/17.
 * 多类型的列表基类View
 */
open class BaseNoRefreshMultiListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
    var preInitAdapterListener: PreInitMultiAdapterListener? = null,
    var sendRefreshListener: SendRefreshListener? = null,
    private var onItemClickListener: OnItemClickListener? = null,
    private var onItemChildClickListener: OnItemChildClickListener? = null,
    private var onItemLongClickListener: OnItemLongClickListener? = null,
    private var onItemChildLongClickListener: OnItemChildLongClickListener? = null,
) : RelativeLayout(context, attrs, defStyleAttr),
    NoRefreshListLayout<BaseMixEntity> {

    override var mContext: Context? = context
    override var mEmptyLayout: EmptyLayout? = null
    override var mSwipeToLoadLayout: SwipeToLoadLayout? = null
    override var mRecyclerView: RecyclerView? = null
    override var mLayoutManager: RecyclerView.LayoutManager? = null
    override var mBaseAdapter: BaseAdapter<BaseMixEntity>? = null
    override var REFRESH_REQUEST_CODE: Int = 0
    override var netErrorMsg: String = ""
    override var serverErrorMsg: String = ""
    override var retryListener: EmptyLayout.OnRetryListener? = null

    init {
        initView()
    }

    open fun initView() {
        if (preInitAdapterListener == null) return
        View.inflate(mContext, getLayoutView(), this)
        mRecyclerView = findViewById(R.id.swipe_target)
        initViews(
            mRecyclerView, BaseMultiListAdapter(preInitAdapterListener!!.preInitAdapter(), null),
            onItemClickListener = onItemClickListener,
            onItemChildClickListener = onItemChildClickListener,
            onItemLongClickListener = onItemLongClickListener,
            onItemChildLongClickListener = onItemChildLongClickListener
        )
    }

    override fun onRefresh() {

    }

    override fun onLoadMore() {

    }

    override fun sendRefreshEvent() {
        sendRefreshListener?.sendRefreshEvent()
    }
}