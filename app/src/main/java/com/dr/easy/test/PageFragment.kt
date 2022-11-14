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
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * <pre>
 *     类描述  : 一级fragment
 *
 *
 *     @author : darryrzhong
 *     @since   : 2022/10/13
 * </pre>
 */
class PageFragment(var index: Int) : BaseLazyPageFragmentX() {

    override fun getViewDelayMillis(): Long = 0

    override fun getLayoutRootView(): View {
        return LayoutInflater.from(context).inflate(
            if (index == 3) R.layout.fragment_child_layout else R.layout.fragment_layout,
            null,
            false
        )
    }

    override fun initRootView(view: View) {
        if (index == 3) {
            val viewPage = view.findViewById<ViewPager2>(R.id.child_page).apply {
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                offscreenPageLimit = 1
            }
            val tabLayout = view.findViewById<TabLayout>(R.id.tl_tab)

            val fragments = mutableListOf<BaseLazyPageFragmentX>()
            val titles = mutableListOf<String>()
            for (i in 21..23) {
                fragments.add(PageChildFragment(i))
                titles.add("page$i")
            }
            val fragmentAdapter = activity?.let { Page2FragmentAdapter2(this, fragments) }
            viewPage.adapter = fragmentAdapter
            TabLayoutMediator(tabLayout, viewPage) { tab, position ->
                //  为Tab设置Text
                tab.text = titles[position]
            }.attach()
            return
        }
        view.findViewById<TextView>(R.id.page_name).text = "page_$index"
        val color = when (index) {
            1 -> {
                resources.getColor(R.color.purple_200)
            }
            2 -> {
                resources.getColor(R.color.purple_500)
            }
            3 -> {
                resources.getColor(R.color.purple_700)
            }
            4 -> {
                resources.getColor(R.color.teal_200)
            }
            5 -> {
                resources.getColor(R.color.teal_800)
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