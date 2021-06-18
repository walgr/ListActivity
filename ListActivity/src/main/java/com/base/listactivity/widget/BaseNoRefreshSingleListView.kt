package com.base.listactivity.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.RelativeLayout
import androidx.recyclerview.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.base.listactivity.listener.NoRefreshListLayout
import com.base.listactivity.R
import com.base.listactivity.listener.BaseAdapter
import com.base.listactivity.listener.PreInitAdapterListener
import com.base.listactivity.listener.SendRefreshListener
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnItemLongClickListener

/**
 * Created by 王朋飞 on 2021/6/17.
 * 单类型的列表基类View
 */
open class BaseNoRefreshSingleListView<T> @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
    var preInitAdapterListener: PreInitAdapterListener<T>? = null,
    var sendRefreshListener: SendRefreshListener? = null,
    var onItemClickListener: OnItemClickListener? = null,
    var onItemChildClickListener: OnItemChildClickListener? = null,
    var onItemLongClickListener: OnItemLongClickListener? = null,
    var onItemChildLongClickListener: OnItemChildLongClickListener? = null,
) : RelativeLayout(context, attrs, defStyleAttr),
    NoRefreshListLayout<T> {

    override var mContext: Context? = context
    override var mEmptyLayout: EmptyLayout? = null
    override var mSwipeToLoadLayout: SwipeToLoadLayout? = null
    override var mRecyclerView: RecyclerView? = null
    override var mLayoutManager: RecyclerView.LayoutManager? = null
    override var mBaseAdapter: BaseAdapter<T>? = null
    override var REFRESH_REQUEST_CODE: Int = 0
    override var netErrorMsg: String = ""
    override var serverErrorMsg: String = ""
    override var retryListener: EmptyLayout.OnRetryListener? = null

    init {
        initView()
    }

    open fun initView() {
        View.inflate(mContext, getLayoutView(), this)
        mRecyclerView = findViewById(R.id.swipe_target)
        initViews(
            mRecyclerView, preInitAdapterListener?.preInitAdapter(),
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