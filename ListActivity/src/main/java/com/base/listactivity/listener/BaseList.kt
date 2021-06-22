package com.base.listactivity.listener

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.base.listactivity.entity.BaseMixEntity
import com.base.listactivity.widget.EmptyLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnItemLongClickListener

/**
 * Created by 王朋飞 on 2021/6/17.
 * 列表基类
 */
interface BaseList<T> : OnRefreshListener, OnLoadMoreListener,
    SendRefreshListener,
    OnItemClickListener, OnItemChildClickListener,
    OnItemLongClickListener, OnItemChildLongClickListener {

    var mContext: Context?
    var mEmptyLayout: EmptyLayout?
    var mSwipeToLoadLayout: SwipeToLoadLayout?

    var mRecyclerView: RecyclerView?
    var mLayoutManager: RecyclerView.LayoutManager?
    var mAdapter: BaseAdapter<*>?

    // 返回需刷新界面的requestCode
    var REFRESH_REQUEST_CODE: Int

    var netErrorMsg: String
    var serverErrorMsg: String

    /**
     * 网络错误时点击重试
     */
    var retryListener: EmptyLayout.OnRetryListener?

    /**
     * 初始化Views
     * @param spanCount 列数 如果>0 RecyclerView的LayoutManager为GridLayoutManager
     */
    fun initViews(
        mRecyclerView: RecyclerView?,
        mBaseAdapter: BaseAdapter<T>? = null,
        mBaseMultiAdapter: BaseAdapter<BaseMixEntity>? = null,
        mSwipeToLoadLayout: SwipeToLoadLayout? = null,
        mEmptyLayout: EmptyLayout? = null,
        mLayoutManager: RecyclerView.LayoutManager? = null,
        spanCount: Int = 0,
        onItemClickListener: OnItemClickListener? = null,
        onItemChildClickListener: OnItemChildClickListener? = null,
        onItemLongClickListener: OnItemLongClickListener? = null,
        onItemChildLongClickListener: OnItemChildLongClickListener? = null
    ) {
        this.mContext = mRecyclerView?.context
        this.mEmptyLayout = mEmptyLayout
        this.mRecyclerView = mRecyclerView
        if (mBaseAdapter != null) {
            this.mAdapter = mBaseAdapter
        }
        if (mBaseMultiAdapter != null) {
            this.mAdapter = mBaseMultiAdapter
        }
        this.mLayoutManager = mLayoutManager
        this.mSwipeToLoadLayout = mSwipeToLoadLayout

        setView(
            spanCount,
            onItemClickListener,
            onItemChildClickListener,
            onItemLongClickListener,
            onItemChildLongClickListener
        )
    }

    /**
     * 请求返回后，显示数据
     */
    fun loadData(
        data: MutableList<T>?,
        isRefresh: Boolean,
        hasMore: Boolean = false,
        emptyMessage: String? = null
    ) {
        if (isRefresh) {
            mSwipeToLoadLayout?.isRefreshing = false
            mAdapter?.clear()
            if (data == null || data.size == 0) {
                emptyMessage?.let {
                    mEmptyLayout?.emptyMessageStr = emptyMessage
                }
                mEmptyLayout?.emptyStatus = EmptyLayout.STATUS_NO_DATA
                mEmptyLayout?.setRetryListener(null)
            } else {
                mEmptyLayout?.hide()
            }
        } else {
            //TODO 上拉加载动画
            mSwipeToLoadLayout?.isLoadingMore = false
        }
        mSwipeToLoadLayout?.isLoadMoreEnabled = hasMore
        data?.let {
            mAdapter?.appendAnyToList(data)
        }
        mAdapter?.notifyDataSetChanged()
    }

    /**
     * 请求网络异常
     */
    fun netError(isRefresh: Boolean) {
        if (isRefresh) {
            mSwipeToLoadLayout?.isRefreshing = false
        } else {
            mSwipeToLoadLayout?.isLoadingMore = false
        }
        if (mAdapter?.isEmpty() == true) {
            mEmptyLayout?.emptyStatus = EmptyLayout.STATUS_NO_NET
            mEmptyLayout?.setRetryListener(retryListener)
        } else {
            showToast(netErrorMsg)
        }
    }

    /**
     * 服务器异常
     */
    fun serviceError(isRefresh: Boolean) {
        if (isRefresh) {
            mSwipeToLoadLayout?.isRefreshing = false
        } else {
            mSwipeToLoadLayout?.isLoadingMore = false
        }
        if (mAdapter?.isEmpty() == true) {
            mEmptyLayout?.emptyStatus = EmptyLayout.STATUS_SERVER_ERROR
            mEmptyLayout?.setRetryListener(null)
        } else {
            showToast(serverErrorMsg)
        }
    }

    /**
     * 刷新界面
     */
    fun refreshView() {
        if (mAdapter?.isEmpty() == true) {
            mEmptyLayout?.emptyStatus = EmptyLayout.STATUS_LOADING
            onRefresh()
        } else {
            mSwipeToLoadLayout?.isRefreshing = true
        }
    }

    fun onActivityResultToRefresh(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REFRESH_REQUEST_CODE && resultCode == RESULT_OK) {
            sendRefreshEvent()
            refreshView()
        }
    }

    fun setView(
        spanCount: Int = 0,
        onItemClickListener: OnItemClickListener? = null,
        onItemChildClickListener: OnItemChildClickListener? = null,
        onItemLongClickListener: OnItemLongClickListener? = null,
        onItemChildLongClickListener: OnItemChildLongClickListener? = null,
    ) {
        setRecyclerViewLayoutManager(spanCount)
        mAdapter?.setOnItemChildClickListener(onItemChildClickListener ?: this)
        mAdapter?.setOnItemChildLongClickListener(onItemChildLongClickListener ?: this)
        mAdapter?.setOnItemClickListener(onItemClickListener ?: this)
        mAdapter?.setOnItemLongClickListener(onItemLongClickListener ?: this)
        this.mRecyclerView?.adapter = this.mAdapter?.getAdapter()
        this.mSwipeToLoadLayout?.setOnRefreshListener(this)
        this.mSwipeToLoadLayout?.setOnLoadMoreListener(this)

        retryListener = EmptyLayout.OnRetryListener {
            if (mEmptyLayout != null) mEmptyLayout?.emptyStatus = EmptyLayout.STATUS_LOADING
            onRefresh()
        }
    }

    /**
     * 设置列表LayoutManager
     */
    fun setRecyclerViewLayoutManager(spanCount: Int = 0) {
        if (mLayoutManager == null) {
            if (spanCount < 1) {
                //默认竖向
                this.mRecyclerView?.layoutManager = LinearLayoutManager(mContext)
            } else {
                this.mRecyclerView?.layoutManager = GridLayoutManager(mContext, spanCount)
            }
        } else {
            this.mRecyclerView?.layoutManager = mLayoutManager
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }

    override fun onItemLongClick(
        adapter: BaseQuickAdapter<*, *>,
        view: View,
        position: Int
    ): Boolean {
        return true
    }

    override fun onItemChildLongClick(
        adapter: BaseQuickAdapter<*, *>,
        view: View,
        position: Int
    ): Boolean {
        return true
    }

    /**
     * 下拉菜单关闭后隐藏
     */
    fun hideExpandView() {}

    fun showToast(msg: String?) {}
}