package com.dr.easy

import android.app.Activity
import java.lang.ref.WeakReference
import java.util.*

/**
 * <pre>
 *     类描述  : activity管理
 *
 *
 *     @author : darryrzhong
 *     @since   : 2022/10/10
 * </pre>
 */
class ActivityStack private constructor() {

    companion object {
        val instance: ActivityStack by lazy { ActivityStack() }
    }

    private val activityStack: Stack<WeakReference<Activity>> = Stack()


    fun getCurrentActivity(): Activity? {
        return activityStack.lastElement()?.get()
    }

    fun pushActivity(activity: WeakReference<Activity>) {
        activityStack.add(activity)
    }


    fun popActivity(activity: WeakReference<Activity>) {
        activityStack.remove(activity)
    }


}