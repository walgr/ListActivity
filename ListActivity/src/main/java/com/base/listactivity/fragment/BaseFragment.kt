package com.base.listactivity.fragment

import androidx.fragment.app.Fragment
import com.base.listactivity.listener.Transit

/**
 * Created by 王朋飞 on 2021/6/21.
 *
 */
abstract class BaseFragment<T> : Fragment(), Transit<T>