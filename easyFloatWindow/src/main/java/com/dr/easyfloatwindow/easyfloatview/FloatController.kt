package com.dr.easyfloatwindow.easyfloatview

import android.R
import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import androidx.core.view.ViewCompat
import java.lang.ref.WeakReference

/**
 * <pre>
 *     类描述  : 悬浮窗控制器
 *
 *
 *     @author : darryrzhong
 *     @since   : 2022/09/30
 * </pre>
 */
class FloatController private constructor() : IFloatController {
    private var mFloatView: FloatViewManager? = null
    private var mContainer: WeakReference<FrameLayout>? = null
    private var mLayoutParams: FrameLayout.LayoutParams = generateLayoutParams()


    companion object {
        fun newInstance(): FloatController = FloatController()
    }

    private fun generateLayoutParams(): FrameLayout.LayoutParams {
        val params = FrameLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.BOTTOM or Gravity.END
        params.setMargins(params.marginStart, params.topMargin, 15, 200)
        return params
    }

    override fun remove() {
        mFloatView?.let {
            if (ViewCompat.isAttachedToWindow(it) && getContainer() != null) {
                getContainer()!!.removeView(it)
            }
            mFloatView = null
        }
    }

    private fun getContainer(): FrameLayout? {
        return mContainer?.get()
    }


    override fun attach(activity: Activity?) {
        attach(getActivityRoot(activity))
    }

    private fun getActivityRoot(activity: Activity?): FrameLayout? {
        activity?.let {
            try {
                return activity.window.decorView.findViewById<View>(R.id.content) as FrameLayout
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } ?: return null
        return null
    }

    override fun attach(container: FrameLayout?) {
        if (container == null || mFloatView == null) {
            return
        }
        if (mFloatView!!.parent == container) {
            return
        }
        if (mFloatView!!.parent != null) {
            val viewGroup = mFloatView!!.parent as ViewGroup
            viewGroup.removeView(mFloatView)
        }
        mContainer = WeakReference(container)
        container.addView(mFloatView)
    }

    override fun detach(activity: Activity?) {
        detach(getActivityRoot(activity))
    }

    override fun detach(container: FrameLayout?) {
        if (mFloatView != null && container != null && ViewCompat.isAttachedToWindow(mFloatView!!)) {
            container.removeView(mFloatView)
        }
        if (getContainer() == container) {
            mContainer = null
        }
    }

    override fun getView(): FloatViewManager? = mFloatView

    override fun customView(view: FloatViewManager) {
        mFloatView = view
    }

    override fun layoutParams(params: FrameLayout.LayoutParams) {
        mLayoutParams = params
        mFloatView?.layoutParams = mLayoutParams
    }

    override fun dragEnable(dragEnable: Boolean) {
        mFloatView?.setDragState(dragEnable)
    }

    override fun setAutoMoveToEdge(autoMoveToEdge: Boolean) {
        mFloatView?.setAutoMoveToEdge(autoMoveToEdge)
    }
}