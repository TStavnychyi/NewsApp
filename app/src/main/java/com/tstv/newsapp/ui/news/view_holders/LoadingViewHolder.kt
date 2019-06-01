package com.tstv.newsapp.ui.news.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import com.tstv.newsapp.R
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.ui.base.BaseViewHolder

class LoadingViewHolder(private val view: View
) : BaseViewHolder<Article>(view) {


    override fun bind(item: Article, adapterItemPosition: Int) {}

    companion object{
        fun create(parent: ViewGroup): LoadingViewHolder{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.footer_loading_view, parent, false)
            return LoadingViewHolder(view)
        }
    }

    val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

    fun showProgressBar(){
        progressBar.visibility = View.VISIBLE
    }

    fun hideView(){
        view.visibility = View.GONE
    }
}