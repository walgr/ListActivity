package com.base.listactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeRefreshHeaderLayout
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.base.listactivity.listener.BaseAdapter
import com.base.listactivity.listener.PreInitAdapterListener
import com.base.listactivity.listener.RefreshSingleListLayout
import com.base.listactivity.widget.*

/**
 * Created by 王朋飞 on 2021/6/17.
 * 单类型的列表Activity基类
 * @param includeEmpty 是否处理空页面
 * @param includeHeader 是否添加刷新Header
 * @param includeLoadMore 是否添加上拉加载
 * @param headerView        添加自定义刷新Header
 * @param loadMoreView      添加自定义上拉加载
 */
abstract class BaseRefreshSingleListActivity<T>(
    private val includeEmpty: Boolean = true,
    private val includeHeader: Boolean = true,
    private val includeLoadMore: Boolean = true,
    private var headerView: SwipeRefreshHeaderLayout? = null,
    private var loadMoreView: SwipeRefreshHeaderLayout? = null
) : AppCompatActivity(),
    PreInitAdapterListener<T>,
    RefreshSingleListLayout<T> {

    override var mContext: Context? = this.baseContext
    override var mEmptyLayout: EmptyLayout? = null
    override var mSwipeToLoadLayout: SwipeToLoadLayout? = null
    override var mRecyclerView: RecyclerView? = null
    override var mLayoutManager: RecyclerView.LayoutManager? = null
    override var mBaseAdapter: BaseAdapter<T>? = null
    override var REFRESH_REQUEST_CODE: Int = 0
    override var netErrorMsg: String = ""
    override var serverErrorMsg: String = ""
    override var retryListener: EmptyLayout.OnRetryListener? = null

    open lateinit var baseRefreshListView: BaseRefreshSingleListView<T>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseRefreshListView = initBaseView()
        setContentView(baseRefreshListView)
    }

    override fun loadData(
        data: MutableList<T>?,
        isRefresh: Boolean,
        hasMore: Boolean,
        emptyMessage: String?
    ) {
        baseRefreshListView.loadData(data, isRefresh, hasMore, emptyMessage)
    }

    open fun initBaseView(): BaseRefreshSingleListView<T> {
        return BaseRefreshSingleListView(
            this,
            headerView = headerView,
            loadMoreView = loadMoreView,
            preInitAdapterListener = this,
            sendRefreshListener = this,
            onRefreshListener = this,
            onLoadMoreListener = this,
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
        baseRefreshListView.onActivityResultToRefresh(requestCode, resultCode, data)
    }
}