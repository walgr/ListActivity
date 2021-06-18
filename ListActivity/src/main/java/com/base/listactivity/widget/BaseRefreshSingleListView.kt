package com.base.listactivity.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aspsine.swipetoloadlayout.OnLoadMoreListener
import com.aspsine.swipetoloadlayout.OnRefreshListener
import com.aspsine.swipetoloadlayout.SwipeRefreshHeaderLayout
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.base.listactivity.R
import com.base.listactivity.listener.RefreshSingleListLayout
import com.base.listactivity.listener.BaseAdapter
import com.base.listactivity.listener.PreInitAdapterListener
import com.base.listactivity.listener.SendRefreshListener
import com.base.listactivity.util.DensityUtil
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnItemLongClickListener

/**
 * Created by 王朋飞 on 2021/6/17.
 * 单类型刷新列表基类View
 * @param includeEmpty      是否处理空页面
 * @param includeHeader     是否添加刷新Header
 * @param includeLoadMore   是否添加上拉加载
 * @param headerView        添加自定义刷新Header
 * @param loadMoreView      添加自定义上拉加载
 */
open class BaseRefreshSingleListView<T>
@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
    private val includeEmpty: Boolean = true,
    private val includeHeader: Boolean = true,
    private val includeLoadMore: Boolean = true,
    private var headerView: SwipeRefreshHeaderLayout? = null,
    private var loadMoreView: SwipeRefreshHeaderLayout? = null,
    var preInitAdapterListener: PreInitAdapterListener<T>? = null,
    var sendRefreshListener: SendRefreshListener? = null,
    var onRefreshListener: OnRefreshListener? = null,
    var onLoadMoreListener: OnLoadMoreListener? = null,
    var onItemClickListener: OnItemClickListener? = null,
    var onItemChildClickListener: OnItemChildClickListener? = null,
    var onItemLongClickListener: OnItemLongClickListener? = null,
    var onItemChildLongClickListener: OnItemChildLongClickListener? = null,
) : RelativeLayout(context, attrs, defStyleAttr),
    RefreshSingleListLayout<T> {

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

    private fun initView() {
        initViews()
    }

    open fun initViews() {
        View.inflate(mContext, getLayoutView(), this)
        mSwipeToLoadLayout = findViewById(R.id.swipeToLoadLayout)
        initSwipeToLoadLayout()
        mRecyclerView = findViewById(R.id.swipe_target)
        if (includeEmpty) {
            mEmptyLayout = findViewById(R.id.emptyLayout)
        }
        initViews(
            mRecyclerView,
            preInitAdapterListener?.preInitAdapter(),
            mSwipeToLoadLayout = mSwipeToLoadLayout,
            mEmptyLayout = mEmptyLayout,
            onItemClickListener = onItemClickListener,
            onItemChildClickListener = onItemChildClickListener,
            onItemLongClickListener = onItemLongClickListener,
            onItemChildLongClickListener = onItemChildLongClickListener
        )
    }

    override fun onRefresh() {
        onRefreshListener?.onRefresh()
    }

    override fun onLoadMore() {
        onLoadMoreListener?.onLoadMore()
    }

    open fun initSwipeToLoadLayout() {
        if (includeHeader) {
            mSwipeToLoadLayout?.setRefreshHeaderView(headerView ?: getRefreshHeaderView())
        }
        if (includeLoadMore) {
            mSwipeToLoadLayout?.setLoadMoreFooterView(loadMoreView ?: getRefreshLoadMoreView())
        }
    }

    override fun getRefreshHeaderView(): View? {
        val dp32 = DensityUtil.dip2px(mContext, 32f)
        return CustomRefreshHeadView(mContext).also {
            it.id = R.id.swipe_refresh_header
            it.layoutParams = MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                DensityUtil.dip2px(mContext, 100f)
            )
            it.setPadding(dp32, dp32, dp32, dp32)
            it.setBackgroundColor(ContextCompat.getColor(mContext!!, android.R.color.transparent))
            it.scaleType = ImageView.ScaleType.FIT_CENTER
            it.setImageDrawable(it.mAnimationDrawable)
            it.startAnim()
        }
    }

    override fun getRefreshLoadMoreView(): View? {
        val dp10 = DensityUtil.dip2px(mContext, 10f)
        return CustomLoadMoreFooterView(mContext).also {
            it.id = R.id.swipe_load_more_footer
            it.layoutParams = MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                DensityUtil.dip2px(mContext, 56f)
            )
            it.setPadding(dp10, dp10, dp10, dp10)
            it.setBackgroundColor(ContextCompat.getColor(mContext!!, android.R.color.transparent))
            it.scaleType = ImageView.ScaleType.FIT_CENTER
            it.setImageDrawable(it.mAnimationDrawable)
            it.startAnim()
        }
    }

    override fun sendRefreshEvent() {
        sendRefreshListener?.sendRefreshEvent()
    }
}