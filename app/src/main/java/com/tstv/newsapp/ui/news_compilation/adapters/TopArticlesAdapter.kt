package com.tstv.newsapp.ui.news_compilation.adapters

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.ui.news_compilation.view_holders.TopArticlesViewHolder
import com.tstv.newsapp.ui.news_compilation.view_holders.TopArticlesViewHolder.Companion.TopArticlesViewHolderType.MAIN_TOP_NEWS_ARTICLE
import com.tstv.newsapp.ui.news_compilation.view_holders.TopArticlesViewHolder.Companion.TopArticlesViewHolderType.OTHER_TOP_NEWS_ARTICLE

class TopArticlesAdapter(
    private val dataList: List<Article>
): RecyclerView.Adapter<TopArticlesViewHolder>() {

    private val mainTopNewsArticleId = 0
    private val otherTopNewsArticleId = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopArticlesViewHolder {
        return if(viewType == mainTopNewsArticleId)
            TopArticlesViewHolder.create(parent, MAIN_TOP_NEWS_ARTICLE)
        else
            TopArticlesViewHolder.create(parent, OTHER_TOP_NEWS_ARTICLE)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: TopArticlesViewHolder, position: Int) = holder.bind(dataList[position], position)

    override fun getItemViewType(position: Int): Int {
        return if(position == 0){
            mainTopNewsArticleId
        }else{
            otherTopNewsArticleId
        }
    }
}