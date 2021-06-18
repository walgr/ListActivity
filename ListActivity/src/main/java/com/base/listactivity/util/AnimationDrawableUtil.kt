package com.base.listactivity.util

import android.graphics.drawable.AnimationDrawable
import java.lang.reflect.Field
import java.lang.reflect.InvocationTargetException

object AnimationDrawableUtil {
    fun setCurFrameIndex(animationDrawable: AnimationDrawable?, index: Int) {
        val clazz: Class<*> = AnimationDrawable::class.java
        var declaredField: Field? = null
        try {
            declaredField = clazz.getDeclaredField("mCurFrame")
            declaredField.isAccessible = true
            declaredField[animationDrawable] = index
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    fun startAtIndex(animationDrawable: AnimationDrawable?, index: Int) {
        val clazz: Class<*> = AnimationDrawable::class.java
        try {
            val method = clazz.getDeclaredMethod(
                "setFrame",
                Int::class.java,
                Boolean::class.java,
                Boolean::class.java
            ) //可以调用类中的所有方法（不包括父类中继承的方法）
            method.isAccessible = true
            method.invoke(animationDrawable, index, false, true)
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }
    }
}