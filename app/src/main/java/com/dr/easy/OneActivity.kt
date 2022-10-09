package com.dr.easy

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.dr.easyfloatwindow.EasyFloatWindow

class OneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one)

        findViewById<Button>(R.id.next).setOnClickListener {
            startActivity(Intent(this, TowActivity::class.java))
        }
        findViewById<Button>(R.id.back).setOnClickListener {
            finish()
        }
    }
}