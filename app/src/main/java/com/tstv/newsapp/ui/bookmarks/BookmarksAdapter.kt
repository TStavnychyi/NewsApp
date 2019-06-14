package com.tstv.newsapp.ui.bookmarks

import android.content.Context
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.internal.listeners.RecyclerViewItemClickListener
import com.tstv.newsapp.ui.base.BaseViewHolder
import com.tstv.newsapp.ui.bookmarks.view_holders.ArticleBookmarkViewHolder
import com.tstv.newsapp.ui.bookmarks.view_holders.BookmarkViewHolder

class BookmarksAdapter(
    private val dataList: MutableList<Article>,
    private val adapterType: AdapterType,
    private val rvItemClickListener: RecyclerViewItemClickListener
): RecyclerView.Adapter<BaseViewHolder<Article>>() {

    private lateinit var context: Context

    companion object {
        enum class AdapterType{
            ADAPTER_TYPE_BOOKMARK_ARTICLE,
            ADAPTER_TYPE_OTHER
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Article> {
        context = parent.context

        return if(adapterType == Companion.AdapterType.ADAPTER_TYPE_BOOKMARK_ARTICLE)
            ArticleBookmarkViewHolder.create(parent, rvItemClickListener, dataList.size)
        else
            BookmarkViewHolder(parent)
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: BaseViewHolder<Article>, position: Int) {
        val dataElement = dataList[position]
        if(holder is ArticleBookmarkViewHolder || holder is BookmarkViewHolder){
            holder.bind(dataElement, position)
        }
    }
}