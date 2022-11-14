package com.dr.easy.test

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dr.library_base.fragment.BaseLazyPageFragmentX

/**
 * <pre>
 *     类描述  :
 *
 *
 *     @author : darryrzhong
 *     @since   : 2022/10/12
 * </pre>
 */
class Page2FragmentAdapter2(
    fragment: Fragment,
    private val fragments: List<BaseLazyPageFragmentX>
) :
    FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]
}