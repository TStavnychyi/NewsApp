package com.tstv.newsapp.ui.news.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.ui.news.fragments.NewsFragment
import com.tstv.newsapp.ui.news.view_holders.NewsArticlesViewHolder

class NewsAdapter(
    private val newsFragment: NewsFragment,
    private var dataList: MutableList<Article>
): PagedListAdapter<Article, NewsArticlesViewHolder>(ARTICLE_COMPARATOR){

    private lateinit var context: Context

    companion object{
        val ARTICLE_COMPARATOR = object : DiffUtil.ItemCallback<Article>(){
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
                oldItem.id == newItem.id
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticlesViewHolder {
        context = parent.context
        return NewsArticlesViewHolder.create(parent, dataList, newsFragment)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: NewsArticlesViewHolder, position: Int) {
        val dataElement = dataList[position]
        holder.bind(dataElement, position)
    }

    fun removeNewsArticlesOfHiddenSource(sourceID: String){
        val sortedList = dataList.filter {
            it.source?.sourceID != sourceID
        }.toMutableList()
        dataList = sortedList
    }

    fun setAdapterDataList(list: MutableList<Article>) {
        dataList = list
        notifyDataSetChanged()
    }
}