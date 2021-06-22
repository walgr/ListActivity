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
import com.base.listactivity.adapter.BaseMultiListAdapter
import com.base.listactivity.listener.*
import com.base.listactivity.util.DensityUtil
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnItemLongClickListener

/**
 * Created by 王朋飞 on 2021/6/21.
 * 列表基类View
 * 支持：单类型、多类型、无刷新、有刷新、默认单排、多排
 */
open class BaseListView<T>
@JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
    private val includeEmpty: Boolean = true,
    private val includeHeader: Boolean = true,
    private val includeLoadMore: Boolean = true,
    private var headerView: SwipeRefreshHeaderLayout? = null,
    private var loadMoreView: SwipeRefreshHeaderLayout? = null,
    private var spanCount: Int = 0,
    var preInitAdapterListener: PreInitAdapterListener<T>? = null,
    var sendRefreshListener: SendRefreshListener? = null,
    var onRefreshListener: OnRefreshListener? = null,
    var onLoadMoreListener: OnLoadMoreListener? = null,
    var onItemClickListener: OnItemClickListener? = null,
    var onItemChildClickListener: OnItemChildClickListener? = null,
    var onItemLongClickListener: OnItemLongClickListener? = null,
    var onItemChildLongClickListener: OnItemChildLongClickListener? = null
) : RelativeLayout(context, attrs, defStyleAttr),
    BaseList<T>,
    RefreshView {

    override var mContext: Context? = context
    override var mEmptyLayout: EmptyLayout? = null
    override var mSwipeToLoadLayout: SwipeToLoadLayout? = null
    override var mRecyclerView: RecyclerView? = null
    override var mLayoutManager: RecyclerView.LayoutManager? = null
    override var mAdapter: BaseAdapter<*>? = null
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

    open fun getLayoutView(): Int {
        return R.layout.layout_recyclerview_with_refresh
    }

    open fun initViews() {
        View.inflate(mContext, getLayoutView(), this)
        mSwipeToLoadLayout = findViewById(R.id.swipeToLoadLayout)
        initSwipeToLoadLayout()
        mRecyclerView = findViewById(R.id.swipe_target)
        mEmptyLayout = findViewById(R.id.emptyLayout)
        if (!includeEmpty) {
            mEmptyLayout?.visibility = View.GONE
            mEmptyLayout = null
        }
        val preAdapter = preInitAdapterListener?.preInitMultiAdapter()
        var multiAdapter: BaseMultiListAdapter? = null
        if (preAdapter != null) {
            multiAdapter = BaseMultiListAdapter(preAdapter, null)
        }
        initViews(
            mRecyclerView,
            mBaseAdapter = preInitAdapterListener?.preInitAdapter(),
            mBaseMultiAdapter = multiAdapter,
            spanCount = spanCount,
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

    override fun getRefreshHeaderView(): View {
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

    override fun getRefreshLoadMoreView(): View {
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