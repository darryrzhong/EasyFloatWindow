package com.dr.easy.test

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.ViewStub
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.dr.easy.R
import com.dr.easy.TowActivity
import com.dr.library_base.fragment.BaseLazyPageFragmentX
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * <pre>
 *     类描述  :
 *
 *
 *     @author : darryrzhong
 *     @since   : 2022/10/12
 * </pre>
 */
class ViewPager2Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_page)
        val viewPage = findViewById<ViewPager2>(R.id.v_page).apply {
            orientation = ViewPager2.ORIENTATION_HORIZONTAL
            offscreenPageLimit = 1
            (getChildAt(0) as RecyclerView).setItemViewCacheSize(3)
            isUserInputEnabled = false
        }
        val tabLayout = findViewById<TabLayout>(R.id.tl_tab)

        findViewById<Button>(R.id.next).setOnClickListener {
            startActivity(Intent(this, TowActivity::class.java))
        }
        val fragments = mutableListOf<BaseLazyPageFragmentX>()
        val titles = mutableListOf<String>()
        for (i in 1..5) {
            fragments.add(PageFragment(i))
            titles.add("page$i")
        }
        val fragmentAdapter = Page2FragmentAdapter(this, fragments)
        viewPage.adapter = fragmentAdapter
        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            //  为Tab设置Text
            tab.text = titles[position]
        }.attach()

    }
}

