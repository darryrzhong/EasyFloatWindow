package com.dr.easy.test

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.dr.easy.R
import com.dr.library_base.fragment.BaseLazyPageFragmentX

/**
 * <pre>
 *     类描述  : 一级fragment
 *
 *
 *     @author : darryrzhong
 *     @since   : 2022/10/13
 * </pre>
 */
class PageChildFragment(var index: Int) : BaseLazyPageFragmentX() {

    override fun getViewDelayMillis(): Long = 0

    override fun getLayoutRootView(): View {
        return LayoutInflater.from(context).inflate(R.layout.fragment_layout, null, false)
    }

    override fun initRootView(view: View) {
        view.findViewById<TextView>(R.id.page_name).text = "page_child_$index"
        val color = when (index) {
            21 -> {
                resources.getColor(R.color.purple_200)
            }
            22 -> {
                resources.getColor(R.color.purple_500)
            }
            23 -> {
                resources.getColor(R.color.purple_700)
            }

            else -> {
                resources.getColor(R.color.teal_800)
            }
        }
        view.findViewById<ImageView>(R.id.iv_bg).setBackgroundColor(color)
    }

    private val TAG  = "lazyFragmentTag"
    override fun onFragmentFirstResume() {
        super.onFragmentFirstResume()
        Log.d(TAG,"onFragmentFirstResume_page$index")
    }

    override fun onFragmentResume() {
        super.onFragmentResume()
        Log.d(TAG,"onFragmentResume_page$index")
    }

    override fun onFragmentPause() {
        super.onFragmentPause()
        Log.d(TAG,"onFragmentPause_page$index")
    }

}