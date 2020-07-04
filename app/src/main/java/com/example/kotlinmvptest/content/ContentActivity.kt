package com.example.kotlinmvptest.content

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar
import com.example.kotlinmvptest.R

import kotlinx.android.synthetic.main.activity_content.*

class ContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_content)
        setSupportActionBar(toolbar as Toolbar?)

        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
        }
    }

}
