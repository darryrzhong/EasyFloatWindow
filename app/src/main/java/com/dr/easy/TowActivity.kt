package com.dr.easy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.dr.easyfloatwindow.EasyFloatWindow

class TowActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tow)

        findViewById<Button>(R.id.back).setOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.bt_remove).setOnClickListener {
            EasyFloatWindow.instance.dismiss(this)
        }

    }
}