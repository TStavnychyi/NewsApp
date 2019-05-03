package com.tstv.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.tstv.newsapp.R

const val SHARED_PREF_KEY_IS_NEWS_TOPICS_SELECTED = "IS_NEWS_TOPICS_ALREADY_SELECTED"

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }


}
