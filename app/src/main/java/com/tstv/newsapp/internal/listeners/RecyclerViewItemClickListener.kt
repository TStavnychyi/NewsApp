package com.tstv.newsapp.internal.listeners

import android.view.View

interface RecyclerViewItemClickListener {
    fun onClick(view: View, position: Int)
}