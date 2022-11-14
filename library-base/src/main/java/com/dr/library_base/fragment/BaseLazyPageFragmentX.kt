package com.dr.library_base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes

/**
 * <pre>
 *     类描述  : 延迟加载 or 预加载 新方案
 *      针对于AndroidX下搭配ViewPager2实现的懒加载fragment（支持嵌套ViewPager2的情况，仅考虑两层嵌套的情况）
 *      针对与可见性相关业务 只需要关注  onFragmentFirstResume（） ->  onFragmentResume() -> onFragmentPause() 即可
 *
 *     @author : darryrzhong
 *     @since   : 2022/10/13
 * </pre>
 */
abstract class BaseLazyPageFragmentX : AbstractBaseFragment() {
    //是否第一次可见
    private var isFirstLoad = true

    //当前的可见状态
    protected var currentVisibleState = false

    //根布局
    protected var rootContainer: FrameLayout? = null

    /**
     * 是否懒加载布局，用ViewPager2的情况下一般都会设置offscreenPageLimit用来开启离屏加载，
     * 开启离屏加载会提前创建布局，如果布局过于复杂的话可考虑延迟加载布局
     * */
    abstract fun getViewDelayMillis(): Long

    abstract fun getLayoutRootView(): View

    abstract fun initRootView(view: View)

    /**
     * 第一次可见，可以进行一些全局的初始化操作
     * */
    protected open fun onFragmentFirstResume() {

    }

    /**
     * Fragment真正的Resume,开始处理网络加载等耗时操作
     */
    protected open fun onFragmentResume() {

    }

    /**
     * Fragment真正的Pause,暂停一切网络耗时操作
     */
    protected open fun onFragmentPause() {

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        rootContainer = FrameLayout(requireContext()).apply {
            layoutParams =
                FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
        }
        if (getViewDelayMillis() == 0L) {
            rootContainer?.addView(getLayoutRootView())
        }
        return rootContainer
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //延迟加载布局
        if (getViewDelayMillis() > 0L) {
            view.postDelayed({
                createView()
            }, getViewDelayMillis())
            return
        }
        initRootView(view)
    }


    override fun onResume() {
        super.onResume()
        //处理一下嵌套情况下的可见性分发
        dispatchUserResumeState()
    }

    override fun onPause() {
        super.onPause()
        currentVisibleState = false
        onFragmentPause()
        //处理一下嵌套情况下的可见性分发
        dispatchChildPauseState()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        rootContainer = null
        isFirstLoad = true
        currentVisibleState = false
    }


    private fun createView() {
        val view = getLayoutRootView()
        rootContainer?.let {
            it.addView(view)
            initRootView(view)
        }
    }

    private fun dispatchUserResumeState() {
        //首先考虑一下fragment嵌套fragment的情况(只考虑2层嵌套),父Fragment此时不可见,直接return不做处理
        if (!isParentResume()) {
            return
        }
        currentVisibleState = true
        if (isFirstLoad) {
            isFirstLoad = false
            onFragmentFirstResume()
            return
        }
        onFragmentResume()

    }

    /**
     * 在双重ViewPager2嵌套的情况下,进行子fragment不可见事件分发
     * */
    private fun dispatchChildPauseState() {
        //首先考虑一下fragment嵌套fragment的情况(只考虑2层嵌套),父Fragment此时不可见,那就直接不可见了
        val fragmentManager = parentFragmentManager
        val childFragments = fragmentManager.fragments
        if (childFragments.size > 0) {
            for (child in childFragments) {
                if (child is BaseLazyPageFragmentX) {
                    val childFragment = child as BaseLazyPageFragmentX
                    if (childFragment.currentVisibleState) {
                        childFragment.onFragmentPause()
                    }
                }
            }
        }
    }

    /**
     * 判断是否有父fragment or 父fragment是否可见
     * */
    private fun isParentResume(): Boolean {
        parentFragment?.let {
            if (it is BaseLazyPageFragmentX) {
                val fragmentPrent = it as BaseLazyPageFragmentX
                return fragmentPrent.currentVisibleState
            }
        }
        return true
    }

}