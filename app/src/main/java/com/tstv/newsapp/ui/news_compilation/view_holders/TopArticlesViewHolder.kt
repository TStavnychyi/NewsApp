package com.tstv.newsapp.ui.news_compilation.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tstv.newsapp.R
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.ui.base.BaseViewHolder

class TopArticlesViewHolder(
    private val view: View
): BaseViewHolder<Article>(view) {

    companion object{

        enum class TopArticlesViewHolderType{
            MAIN_TOP_NEWS_ARTICLE,
            OTHER_TOP_NEWS_ARTICLE
        }

        fun create(parent: ViewGroup, viewHolderType: TopArticlesViewHolderType): TopArticlesViewHolder{
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
            }
            return TopArticlesViewHolder(view)
        }
    }

    private val ivArticleImage = view.findViewById<ImageView>(R.id.iv_article_image)
    private val tvArticleNumber = view.findViewById<TextView>(R.id.tv_article_number)
    private val tvArticlePublisher = view.findViewById<TextView>(R.id.tvArticlePublisher)
    private val tvArticleTitle  = view.findViewById<TextView>(R.id.tvArticleTitle)
    private val tvArticlePublishTime  = view.findViewById<TextView>(R.id.tvArticlePublishTime)
    private val ivArticleOptionsButton  = view.findViewById<ImageView>(R.id.iv_article_options_button)

    override fun bind(item: Article, adapterItemPosition: Int) {
        with(item){
            tvArticleTitle.text = title
            tvArticleNumber.text = (adapterItemPosition + 1).toString()
            tvArticlePublisher.text = source?.name ?: author
            tvArticlePublishTime.text = parseAndSetDateToView(publishedAt)
            if(!urlToImage.isNullOrEmpty()){
                setupGlide(urlToImage, ivArticleImage)
            }
        }
        bindViewsClickListeners()
    }

    fun bindViewsClickListeners(){
        ivArticleOptionsButton.setOnClickListener {// TODO
        }

        view.setOnClickListener {
            //TODO
        }
    }
}