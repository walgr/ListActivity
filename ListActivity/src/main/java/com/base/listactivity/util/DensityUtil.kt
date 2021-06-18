package com.base.listactivity.util

import android.content.Context

/**
 * Created by 王朋飞 on 2021/6/17.
 *
 */
object DensityUtil {

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    fun dip2px(context: Context?, dpValue: Float): Int {
        if (context == null) {
            return 0
        }
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }
}