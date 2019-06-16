package com.tstv.newsapp.internal.listeners

import android.view.View

interface OnRecyclerViewItemClickListener {
    fun onClick(view: View, position: Int)
}