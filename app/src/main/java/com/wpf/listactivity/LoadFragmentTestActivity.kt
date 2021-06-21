package com.wpf.listactivity

import com.base.listactivity.entity.BaseMixEntity
import com.base.listactivity.fragment.BaseFragment
import com.base.listactivity.fragment.LoadFragmentActivity
import com.wpf.listactivity.fragment.RefreshMultiListTestFragment

/**
 * Created by 王朋飞 on 2021/6/21.
 *
 */
class LoadFragmentTestActivity: LoadFragmentActivity<BaseMixEntity>() {

    override fun getLoadFragment(): BaseFragment<BaseMixEntity> {
        return RefreshMultiListTestFragment()
    }
}