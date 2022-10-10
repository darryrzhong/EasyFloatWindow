package com.dr.easy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.dr.easyfloatwindow.EasyFloatWindow
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.bt_add).setOnClickListener {
//            EasyFloatWindow.instance.setContentView(this, R.layout.layout_float_view1)
//                .dragEnable(true).setAutoMoveToEdge(true).show(this)
            EasyFloatWindow.instance.setContentView(FloatCustomView(this))
                .dragEnable(true).setAutoMoveToEdge(true).show(this)
        }


        findViewById<Button>(R.id.bt_remove).setOnClickListener {
            EasyFloatWindow.instance.dismiss(this)
        }

        findViewById<Button>(R.id.next).setOnClickListener {
            startActivity(Intent(this, OneActivity::class.java))
        }


    }

}