package com.dr.easyfloatwindow.easyfloatview

import android.app.Activity
import android.content.Context
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes

/**
 * <pre>
 *     类描述  : 悬浮窗行为接口
 *
 *
 *     @author : darryrzhong
 *     @since   : 2022/09/30
 * </pre>
 */
interface IFloatController {

    fun remove()

    fun attach(activity: Activity?)

    fun attach(container: FrameLayout?)

    fun detach(activity: Activity?)

    fun detach(container: FrameLayout?)

    fun getView(): FloatViewManager?

    fun customView(view: FloatViewManager)


    fun layoutParams(params: ViewGroup.LayoutParams)

    fun dragEnable(dragEnable: Boolean)

    fun setAutoMoveToEdge(autoMoveToEdge: Boolean)

}