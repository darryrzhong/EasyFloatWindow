package com.dr.easyfloatwindow.easyfloatview

import android.content.Context
import android.view.View

/**
 * <pre>
 *     类描述  :
 *
 *
 *     @author : darryrzhong
 *     @since   : 2022/09/30
 * </pre>
 */
 fun View.getStatusBarHeight(): Int {
    var height = 0
    val resourceId =
        context.getResources().getIdentifier("status_bar_height", "dimen", "android")
    if (resourceId > 0) {
        height = context.getResources().getDimensionPixelSize(resourceId);
    }
    return height
}

 fun View.getScreenWidth(context: Context): Int {
    var screenWith = -1
    try {
        screenWith = context.resources.displayMetrics.widthPixels
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return screenWith
}
