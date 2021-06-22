package com.base.listactivity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeRefreshHeaderLayout
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.base.listactivity.adapter.BaseMultiItemAdapter
import com.base.listactivity.entity.BaseMixEntity
import com.base.listactivity.listener.BaseAdapter
import com.base.listactivity.listener.PreInitAdapterListener
import com.base.listactivity.listener.RefreshListLayout
import com.base.listactivity.widget.BaseListView
import com.base.listactivity.widget.EmptyLayout

/**
 * Created by 王朋飞 on 2021/6/22.
 *  列表Activity基类
 *  实现了刷新、无刷新功能的单、多列表
 *  单类型 需要重写preInitAdapter方法
 *  多类型 需要重写preInitMultiAdapter方法
 */
abstract class BaseListActivity<T>
@JvmOverloads constructor(
    private val includeEmpty: Boolean = true,
    private val includeHeader: Boolean = true,
    private val includeLoadMore: Boolean = true,
    private var headerView: SwipeRefreshHeaderLayout? = null,
    private var loadMoreView: SwipeRefreshHeaderLayout? = null
) : AppCompatActivity(),
    PreInitAdapterListener<T>,
    RefreshListLayout<T> {

    override var mContext: Context? = this.baseContext
    override var mEmptyLayout: EmptyLayout? = null
    override var mSwipeToLoadLayout: SwipeToLoadLayout? = null
    override var mRecyclerView: RecyclerView? = null
    override var mLayoutManager: RecyclerView.LayoutManager? = null
    override var mAdapter: BaseAdapter<*>? = null
    override var REFRESH_REQUEST_CODE: Int = 0
    override var netErrorMsg: String = ""
    override var serverErrorMsg: String = ""
    override var retryListener: EmptyLayout.OnRetryListener? = null

    open var baseListView: BaseListView<T>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseListView = initBaseView()
        setContentView(baseListView)
    }

    override fun loadData(
        data: MutableList<T>?,
        isRefresh: Boolean,
        hasMore: Boolean,
        emptyMessage: String?
    ) {
        baseListView?.loadData(data, isRefresh, hasMore, emptyMessage)
    }

    open fun initBaseView(): BaseListView<T> {
        return BaseListView(
            this,
            includeEmpty = includeEmpty,
            includeHeader = includeHeader,
            includeLoadMore = includeLoadMore,
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
        baseListView?.onActivityResultToRefresh(requestCode, resultCode, data)
    }

    override fun preInitAdapter(): BaseAdapter<T>? {
        return null
    }

    override fun preInitMultiAdapter(): Array<out BaseMultiItemAdapter<out BaseMixEntity>>? {
        return null
    }

    override fun sendRefreshEvent() {

    }
}