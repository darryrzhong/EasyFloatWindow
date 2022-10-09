package com.dr.easyfloatwindow.easyfloatview

import android.content.Context
import android.content.res.Configuration
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewConfiguration
import android.view.ViewGroup
import android.widget.FrameLayout

/**
 * <pre>
 *     类描述  : 悬浮窗行为理器中心
 *
 *
 *     @author : darryrzhong
 *     @since   : 2022/09/30
 * </pre>
 */
 open class FloatViewManager @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    companion object {
        const val MARGIN_EDGE = 13F
        const val TOUCH_TIME_THRESHOLD = 150
    }

    private var mOriginalRawX: Float = 0f
    private var mOriginalRawY: Float = 0f
    private var mOriginalX: Float = 0f
    private var mOriginalY: Float = 0f
    private var mPortraitY = 0f
    private var touchDownX = 0f
    private var mLastTouchDownTime: Long = 0L
    private var mScreenHeight = 0
    private var mScreenWidth = 0
    private var mStatusBarHeight = 0
    private var isNearestLeft = true
    private var dragEnable = true
    private var autoMoveToEdge = true


    init {
        mStatusBarHeight = getStatusBarHeight()
        isClickable = true
    }

    /**
     * @param dragEnable 是否可拖动
     */
    fun setDragState(dragEnable: Boolean) {
        this.dragEnable = dragEnable
    }

    /**
     * @param autoMoveToEdge 是否自动吸附到边缘
     */
    fun setAutoMoveToEdge(autoMoveToEdge: Boolean) {
        this.autoMoveToEdge = autoMoveToEdge
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        event?.let {
            when (it.action) {
                MotionEvent.ACTION_MOVE -> {
                    updateViewPosition(event)
                }
                MotionEvent.ACTION_UP -> {
                    clearPortraitY()
                    //自动吸附
                    if (autoMoveToEdge) {
                        moveToEdge()
                    }
                    if (isOnClickEvent()) {
                        dealClickEvent()
                    }
                }
            }
            return true
        } ?: return false
    }

    private fun dealClickEvent() {

    }

    private fun moveToEdge() {
        //dragEnable
        if (!dragEnable) return
        moveToEdge(isNearestLeft(), false)
    }


    private fun moveToEdge(isLeft: Boolean, isLandscape: Boolean) {
        val moveDistance: Float =
            if (isLeft) MARGIN_EDGE else mScreenWidth - MARGIN_EDGE
        var crY = y
        if (!isLandscape && mPortraitY != 0f) {
            crY = mPortraitY
            clearPortraitY()
        }
        moveAnimator.start(
            moveDistance,
            Math.min(Math.max(0f, crY), (mScreenHeight - height).toFloat())
        )
    }

    private fun isNearestLeft(): Boolean {
        val middle = mScreenWidth / 2
        isNearestLeft = x < middle
        return isNearestLeft
    }

    private fun isOnClickEvent(): Boolean {
        return System.currentTimeMillis() - mLastTouchDownTime < TOUCH_TIME_THRESHOLD
    }


    private fun clearPortraitY() {
        mPortraitY = 0f
    }

    /**
     * 拖拽更新位置
     * */
    private fun updateViewPosition(event: MotionEvent) {
        if (!dragEnable) {
            return
        }
        //限制不可超出屏幕宽度
        var desX = mOriginalX + event.rawX - mOriginalRawX
        if (layoutParams.width == FrameLayout.LayoutParams.WRAP_CONTENT) {
            if (desX < 0) {
                desX = MARGIN_EDGE
            }
            if (desX > mScreenWidth) {
                desX = mScreenWidth - MARGIN_EDGE
            }
            x = desX
        }
        // 限制不可超出屏幕高度
        var desY = mOriginalY + event.rawY - mOriginalRawY
        if (layoutParams.height == LayoutParams.WRAP_CONTENT) {
            if (desY < mStatusBarHeight) {
                desY = mStatusBarHeight.toFloat()
            }
            if (desY > mScreenHeight - height) {
                desY = (mScreenHeight - height).toFloat()
            }
            y = desY
        }
    }

    private fun changeOriginalTouchParams(event: MotionEvent) {
        mOriginalX = x
        mOriginalY = y
        mOriginalRawX = event.rawX
        mOriginalRawY = event.rawY
        mLastTouchDownTime = System.currentTimeMillis()
    }

    protected open fun updateSize() {
        val viewGroup = parent as ViewGroup
        mScreenWidth = viewGroup.width - width
        mScreenHeight = viewGroup.height
    }


    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        if (parent != null) {
            val isLandscape = newConfig!!.orientation == Configuration.ORIENTATION_LANDSCAPE
            markPortraitY(isLandscape)
            (parent as ViewGroup).post {
                updateSize()
                moveToEdge(isNearestLeft, isLandscape)
            }
        }
    }

    private fun markPortraitY(isLandscape: Boolean) {
        if (isLandscape) {
            mPortraitY = y
        }
    }

    private fun initTouchDown(ev: MotionEvent) {
        changeOriginalTouchParams(ev)
        updateSize()
        moveAnimator.stop()
    }


    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var intercepted = false
        ev?.let {
            when (it.action) {
                MotionEvent.ACTION_DOWN -> {
                    intercepted = false
                    touchDownX = ev.x
                    initTouchDown(ev)
                }
                MotionEvent.ACTION_MOVE -> {
                    intercepted =
                        Math.abs(touchDownX - ev.x) >= ViewConfiguration.get(context).scaledTouchSlop

                }
                MotionEvent.ACTION_UP -> {
                    intercepted = false
                }
            }
            return intercepted
        } ?: return false

    }


    private val moveAnimator = object : Runnable {
        private val handler = android.os.Handler(Looper.getMainLooper())
        private var destinationX = 0f
        private var destinationY = 0f
        private var startingTime: Long = 0

        fun start(x: Float, y: Float) {
            destinationX = x
            destinationY = y
            startingTime = System.currentTimeMillis()
            handler.post(this)
        }

        override fun run() {
            if (rootView == null || rootView.parent == null) {
                return
            }
            val progress = Math.min(1f, (System.currentTimeMillis() - startingTime) / 400f)
            val deltaX = (destinationX - x) * progress
            val deltaY = (destinationY - y) * progress
            move(deltaX, deltaY)
            if (progress < 1) {
                handler.post(this)
            }
        }

        fun stop() {
            handler.removeCallbacks(this)
        }
    }

    private fun move(deltaX: Float, deltaY: Float) {
        x += deltaX
        y += deltaY
    }


}