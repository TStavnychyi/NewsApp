package com.tstv.newsapp.ui.news_compilation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.internal.listeners.OnRecyclerViewItemClickListener
import com.tstv.newsapp.ui.news_compilation.view_holders.NewsCompilationViewHolder
import com.tstv.newsapp.ui.news_compilation.view_holders.NewsCompilationViewHolder.Companion.TopArticlesViewHolderType.*

class NewsCompilationAdapter(
    private val dataList: List<Article>,
    private val rvItemClickListenerOn: OnRecyclerViewItemClickListener
): RecyclerView.Adapter<NewsCompilationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsCompilationViewHolder {
        return if(viewType == MAIN_TOP_NEWS_ARTICLE.id)
            NewsCompilationViewHolder.create(parent, MAIN_TOP_NEWS_ARTICLE, rvItemClickListenerOn)
        else if (viewType == OTHER_TOP_NEWS_ARTICLE.id)
            NewsCompilationViewHolder.create(parent, OTHER_TOP_NEWS_ARTICLE, rvItemClickListenerOn)
        else
            NewsCompilationViewHolder.create(parent, NEWS_COMPILATION, rvItemClickListenerOn)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: NewsCompilationViewHolder, position: Int) = holder.bind(dataList[position], position)

    override fun getItemViewType(position: Int): Int {
        return if(position == 0){
            MAIN_TOP_NEWS_ARTICLE.id
        }else if (position < 5){
            OTHER_TOP_NEWS_ARTICLE.id
        }else{
            NEWS_COMPILATION.id
        }
    }
}