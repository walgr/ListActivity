package com.base.listactivity.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout
import com.base.listactivity.R
import com.base.listactivity.adapter.BaseListAdapter
import com.base.listactivity.listener.BaseAdapter
import com.base.listactivity.listener.NoRefreshListLayout
import com.base.listactivity.listener.PreInitAdapterListener
import com.base.listactivity.listener.Transit
import com.base.listactivity.widget.EmptyLayout

/**
 * Created by 王朋飞 on 2021/6/21.
 *
 */
abstract class LoadFragmentActivity<T> :
    AppCompatActivity(),
    PreInitAdapterListener<T>,
    NoRefreshListLayout<T>,
    Transit<T> {

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

    open var fragment: BaseFragment<T>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)
        fragment = getLoadFragment()
        fragment?.let {
            addFragment(R.id.container, it)
        }
    }

    abstract fun getLoadFragment(): BaseFragment<T>?

    /**
     * 添加 Fragment
     *
     * @param containerViewId
     * @param fragment
     */
    fun addFragment(containerViewId: Int, fragment: Fragment, tag: String? = null) {
        val fragmentTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        if (TextUtils.isEmpty(tag)) {
            fragmentTransaction.add(containerViewId, fragment)
        } else {
            // 设置tag，不然下面 findFragmentByTag(tag)找不到
            fragmentTransaction.add(containerViewId, fragment, tag)
            fragmentTransaction.addToBackStack(tag)
        }
        fragmentTransaction.commit()
    }

    override fun loadData(
        data: MutableList<T>?,
        isRefresh: Boolean,
        hasMore: Boolean,
        emptyMessage: String?
    ) {
        fragment?.loadData(data, isRefresh, hasMore, emptyMessage)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        fragment?.onActivityResult(requestCode, resultCode, data)
    }

    override fun preInitAdapter(): BaseListAdapter<T>? {
        return null
    }

    override fun onRefresh() {
        fragment?.onRefresh()
    }

    override fun onLoadMore() {
        fragment?.onLoadMore()
    }

    override fun sendRefreshEvent() {
        //需要实现发送消息
        fragment?.sendRefreshEvent()
    }
}