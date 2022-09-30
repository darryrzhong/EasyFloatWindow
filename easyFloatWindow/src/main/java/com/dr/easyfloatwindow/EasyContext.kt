package com.dr.easyfloatwindow.easyfloatview

import android.app.Application

/**
 * <pre>
 *     类描述  : 用反射来获取全局的application
 *
 *
 *     @author : darryrzhong
 *     @since   : 2022/09/30
 * </pre>
 */
object EasyContext {
    var instance: Application? = null

    fun get(): Application? {
        return instance
    }

    init {
        var app: Application? = null
        try {
            app = Class.forName("android.app.AppGlobals").getMethod("getInitialApplication")
                .invoke(null) as Application
            checkNotNull(app) { "Static initialization of Applications must be on main thread." }
        } catch (e: Exception) {
            e.printStackTrace()
            try {
                app = Class.forName("android.app.ActivityThread").getMethod("currentApplication")
                    .invoke(null) as Application
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } finally {
            instance = app
        }
    }
}