package com.tstv.newsapp.ui.news_compilation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.ui.news_compilation.view_holders.CompilationArticlesViewHolder

class CompilationArticlesAdapter(
    private val dataList: List<Article>
): RecyclerView.Adapter<CompilationArticlesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CompilationArticlesViewHolder =
        CompilationArticlesViewHolder.create(parent)

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: CompilationArticlesViewHolder, position: Int) =
        holder.bind(dataList[position], position)

}