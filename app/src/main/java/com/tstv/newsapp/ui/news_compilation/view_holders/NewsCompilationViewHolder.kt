package com.tstv.newsapp.ui.news_compilation.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tstv.newsapp.R
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.internal.listeners.OnRecyclerViewItemClickListener
import com.tstv.newsapp.ui.base.BaseViewHolder

class NewsCompilationViewHolder(
    private val view: View,
    private val viewHolderType: TopArticlesViewHolderType,
    private val rvItemClickListenerOn: OnRecyclerViewItemClickListener
    ): BaseViewHolder<Article>(view) {

    companion object{

        enum class TopArticlesViewHolderType(val id: Int){
            MAIN_TOP_NEWS_ARTICLE (0),
            OTHER_TOP_NEWS_ARTICLE (1),
            NEWS_COMPILATION (2)
        }

        fun create(parent: ViewGroup, viewHolderType: TopArticlesViewHolderType, rvItemClickListenerOn: OnRecyclerViewItemClickListener): NewsCompilationViewHolder{
            val view = when(viewHolderType) {
                Companion.TopArticlesViewHolderType.MAIN_TOP_NEWS_ARTICLE ->
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.adapter_item_news_compilation_main_top_article,
                        parent,
                        false
                    )
                Companion.TopArticlesViewHolderType.OTHER_TOP_NEWS_ARTICLE ->
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.adapter_item_news_compilation_top_articles,
                        parent,
                        false
                    )
                Companion.TopArticlesViewHolderType.NEWS_COMPILATION ->
                    LayoutInflater.from(parent.context).inflate(
                        R.layout.adapter_item_news_article,
                        parent,
                        false
                    )
            }
            return NewsCompilationViewHolder(view, viewHolderType, rvItemClickListenerOn)
        }
    }

    private val ivArticleImage = view.findViewById<ImageView>(R.id.iv_article_image)
    private val tvArticleNumber = view.findViewById<TextView>(R.id.tv_article_number)
    private val tvArticlePublisher = view.findViewById<TextView>(R.id.tv_article_publisher_name)
    private val tvArticleTitle  = view.findViewById<TextView>(R.id.tv_article_title)
    private val tvArticlePublishTime  = view.findViewById<TextView>(R.id.tv_article_publish_date)
    private val ivArticleOptionsButton  = view.findViewById<ImageView>(R.id.iv_article_options_button)


    override fun bind(item: Article, adapterItemPosition: Int) {
        with(item){
            tvArticleTitle.text = title
            if(viewHolderType != Companion.TopArticlesViewHolderType.NEWS_COMPILATION)
                tvArticleNumber.text = (adapterItemPosition + 1).toString()
            tvArticlePublisher.text = source?.name ?: author
            tvArticlePublishTime.text = parseAndSetDateToView(publishedAt)
            if(!urlToImage.isNullOrEmpty()){
                setupGlide(urlToImage, ivArticleImage)
            }
        }
        bindViewsClickListeners()
    }

    private fun bindViewsClickListeners(){
        ivArticleOptionsButton.setOnClickListener {// TODO
        }

        view.setOnClickListener {
            rvItemClickListenerOn.onClick(view, adapterPosition)
        }


    }
}