package com.dr.easyfloatwindow.easyfloatview

import android.content.Context
import android.view.View
import androidx.annotation.LayoutRes

/**
 * <pre>
 *     类描述  : 悬浮窗
 *
 *
 *     @author : darryrzhong
 *     @since   : 2022/09/30
 * </pre>
 */
class EasyFloatView : FloatViewManager {


    constructor(context: Context, contentView: View) : super(context, null) {
        addView(contentView)
    }

    constructor(context: Context, @LayoutRes layoutId: Int) : super(context, null) {
        inflate(context, layoutId, this)
    }
}