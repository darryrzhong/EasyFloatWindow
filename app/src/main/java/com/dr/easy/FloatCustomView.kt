package com.dr.easy

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import android.widget.Toast
import com.dr.easy.databinding.LayoutFloatCustomBinding
import com.dr.easyfloatwindow.EasyFloatWindow

/**
 * <pre>
 *     类描述  : 自定义浮窗内容
 *
 *
 *     @author : darryrzhong
 *     @since   : 2022/10/10
 * </pre>
 */
class FloatCustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private lateinit var viewBinding: LayoutFloatCustomBinding

    init {
        viewBinding = LayoutFloatCustomBinding.inflate(LayoutInflater.from(context), this, true)
        initView()
    }

    private fun initView() {
        viewBinding.close.setOnClickListener {
            ActivityStack.instance.getCurrentActivity()?.let {
                EasyFloatWindow.instance.dismiss(it)
            }
            Toast.makeText(context, "关闭浮窗", Toast.LENGTH_LONG).show()
        }
    }
}