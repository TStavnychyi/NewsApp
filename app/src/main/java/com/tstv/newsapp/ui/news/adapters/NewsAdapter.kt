package com.tstv.newsapp.ui.news.adapters

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.ui.base.BaseViewHolder
import com.tstv.newsapp.ui.news.fragments.NewsFragment
import com.tstv.newsapp.ui.news.view_holders.LoadingViewHolder
import com.tstv.newsapp.ui.news.view_holders.NewsArticlesViewHolder

class NewsAdapter(
    private val newsFragment: NewsFragment,
    private var dataList: MutableList<Article>
): RecyclerView.Adapter<BaseViewHolder<Article>>(){

    private lateinit var context: Context

    companion object{
        const val VIEW_TYPE_ITEM = 0
        const val VIEW_TYPE_LOADING = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Article> {
        context = parent.context

        return if(viewType == VIEW_TYPE_ITEM)
            NewsArticlesViewHolder.create(parent, itemCount, newsFragment)
        else
            LoadingViewHolder.create(parent)

    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BaseViewHolder<Article>, position: Int) {
        if(holder is NewsArticlesViewHolder){
            val dataElement = dataList[position]
            holder.bind(dataElement, position)
        }else if(holder is LoadingViewHolder){
            holder.showProgressBar()
        }

    }

    override fun getItemCount(): Int {
        return dataList.size  + if(isAllDataFetched) 0 else 1
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == itemCount - 1 && !isAllDataFetched){
            VIEW_TYPE_LOADING
        }else{
            VIEW_TYPE_ITEM
        }
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

    fun loadMoreData(list: List<Article>){
        val lastItemPosition = dataList.size - 1
        dataList = list.toMutableList()
        notifyItemRangeChanged(lastItemPosition, itemCount)
    }

    private var isAllDataFetched = false
    fun removeLoadingView(){
        isAllDataFetched = true
        notifyItemChanged(dataList.size)
    }
}