package com.wpf.listactivity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children

/**
 * Created by 王朋飞 on 2021/6/17.
 *
 */
class MainActivity: AppCompatActivity() {

    private lateinit var view: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        view = View.inflate(this, R.layout.activity_main, null)
        setContentView(view)
        findAllView()
        setAllViewClick()
    }

    private fun findAllView() : Sequence<View> {
        return view.findViewById<LinearLayout>(R.id.allView).children
    }

    private fun setAllViewClick() {
        for (it in findAllView()) {
            it.setOnClickListener {
                intentActivity(it.tag as String)
            }
        }
    }

    private fun intentActivity(intentActivity: String) {
        startActivity(Intent(this, Class.forName(intentActivity)))
    }
}