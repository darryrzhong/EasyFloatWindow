package com.dr.library_base.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dr.library_base.BuildConfig

/**
 * <pre>
 *     类描述  : 只用来进行生命周期的相关打印,便于排除问题
 *
 *
 *
 *     @author : darryrzhong
 *     @since   : 2022/10/13
 * </pre>
 */

/**
 * Fragment生命周期 onAttach -> onCreate -> onCreatedView -> onViewCreated
 * -> onStart -> onResume -> onPause -> onStop -> onDestroyView -> onDestroy
 * -> onDetach 对于 ViewPager + Fragment 的实现我们需要关注的几个生命周期有： onCreatedView +
 * onViewCreated + onResume + onPause + onDestroyView
 */
abstract class AbstractBaseFragment : Fragment() {

    protected val mFragmentTag: String = this.javaClass.simpleName


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (BuildConfig.DEBUG) {
            Log.i(mFragmentTag, "onAttach")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            Log.i(mFragmentTag, "onCreate")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (BuildConfig.DEBUG) {
            Log.i(mFragmentTag, "onCreateView")
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (BuildConfig.DEBUG) {
            Log.i(mFragmentTag, "onViewCreated")
        }
    }


    override fun onResume() {
        super.onResume()
        if (BuildConfig.DEBUG) {
            Log.i(mFragmentTag, "onResume")
        }
    }

    override fun onPause() {
        super.onPause()
        if (BuildConfig.DEBUG) {
            Log.i(mFragmentTag, "onPause")
        }
    }

    override fun onStop() {
        super.onStop()
        if (BuildConfig.DEBUG) {
            Log.i(mFragmentTag, "onStop")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        if (BuildConfig.DEBUG) {
            Log.i(mFragmentTag, "onDestroyView")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (BuildConfig.DEBUG) {
            Log.i(mFragmentTag, "onDestroy")
        }
    }

}