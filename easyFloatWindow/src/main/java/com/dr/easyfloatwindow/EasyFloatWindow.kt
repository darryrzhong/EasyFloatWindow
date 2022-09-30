package com.dr.easyfloatwindow

import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.annotation.LayoutRes
import com.dr.easyfloatwindow.easyfloatview.EasyFloatView
import com.dr.easyfloatwindow.easyfloatview.FloatController

/**
 * <pre>
 *     类描述  : 一个不需要任何权限的简单悬浮窗
 *
 *
 *     @author : darryrzhong
 *     @since   : 2022/09/30
 * </pre>
 */
class EasyFloatWindow private constructor() {
    //需要屏蔽的页面
    private val blackList = mutableListOf<Class<*>>()
    private val mFloatController: FloatController by lazy { FloatController.newInstance() }
    private var mLayoutParams: ViewGroup.LayoutParams = generateLayoutParams()
    private var dragEnable = true
    private var autoMoveToEdge = true

    companion object {
        //全局单例使用这个
        val instance: EasyFloatWindow by lazy { EasyFloatWindow() }

        //有多个View同时浮窗时用这个
        fun createEasyFloat(): EasyFloatWindow = EasyFloatWindow()
    }


    private fun generateLayoutParams(): ViewGroup.LayoutParams {
        val params = FrameLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.BOTTOM or Gravity.START
        params.setMargins(13, params.topMargin, params.rightMargin, 500)
        return params
    }

    fun layoutParams(layoutParams: FrameLayout.LayoutParams): EasyFloatWindow {
        mLayoutParams = layoutParams
        return this
    }

    fun blackList(blackList: MutableList<Class<*>>): EasyFloatWindow {
        blackList.addAll(blackList)
        return this
    }

    fun setContentView(context: Context, @LayoutRes resId: Int): EasyFloatWindow {
        mFloatController.customView(EasyFloatView(context, resId))
        return this
    }

    fun setContentView(view: View): EasyFloatWindow {
        mFloatController.customView(EasyFloatView(view.context, view))
        return this
    }




}