package com.tstv.newsapp.ui.bookmarks.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tstv.newsapp.R
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.internal.listeners.OnRecyclerViewItemClickListener
import com.tstv.newsapp.ui.base.BaseViewHolder

class ArticleBookmarkViewHolder(
    private val view: View,
    private val rvItemClickListenerOn: OnRecyclerViewItemClickListener,
    private val adapterDataListSize: Int
): BaseViewHolder<Article>(view), View.OnClickListener {

    init {
        view.setOnClickListener(this)
    }

    companion object{
        fun create(parent: ViewGroup, rvItemClickListenerOn: OnRecyclerViewItemClickListener, adapterDataListSize: Int) : ArticleBookmarkViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_item_popular_articles, parent, false)
            return ArticleBookmarkViewHolder(
                view,
                rvItemClickListenerOn,
                adapterDataListSize
            )
        }
    }

    override fun onClick(v: View?) = rvItemClickListenerOn.onClick(v!!, adapterPosition)


    private val tvArticleTitle = view.findViewById<TextView>(R.id.tv_article_title)
    private val ivArticleImage = view.findViewById<ImageView>(R.id.iv_article_image)
    private val tvArticlePublisher = view.findViewById<TextView>(R.id.tv_article_publisher_name)
    private val tvArticlePublishDate = view.findViewById<TextView>(R.id.tv_article_publish_date)
    private val ivArticleOptions = view.findViewById<ImageView>(R.id.iv_article_options_button)
    private val dividerView = view.findViewById<View>(R.id.divider_line)

    private lateinit var articleItem: Article

    override fun bind(item: Article, adapterItemPosition: Int) {
        articleItem = item

        with(item){
            tvArticleTitle.text = title
            tvArticlePublisher.text = source?.name ?: author
            tvArticlePublishDate.text = parseAndSetDateToView(publishedAt)

            if(!urlToImage.isNullOrEmpty()){
                setupGlide(urlToImage, ivArticleImage)
            }
        }

        if ((adapterDataListSize - 1) == adapterItemPosition)
            dividerView.visibility = View.INVISIBLE
        else
            dividerView.visibility = View.VISIBLE
    }

}