package com.tstv.newsapp.ui.news_compilation.view_holders

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.tstv.newsapp.R
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.ui.base.BaseViewHolder

class CompilationArticlesViewHolder(
    private val view: View
): BaseViewHolder<Article>(view) {

    companion object{
        fun create(parent: ViewGroup): CompilationArticlesViewHolder{
            val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item_news_article, parent, false)
            return CompilationArticlesViewHolder(view)
        }
    }

    private val ivArticleImage = view.findViewById<ImageView>(R.id.iv_article_image)
    private val tvArticlePublisher = view.findViewById<TextView>(R.id.tv_article_publisher_name)
    private val tvArticleTitle  = view.findViewById<TextView>(R.id.tv_article_title)
    private val tvArticlePublishTime  = view.findViewById<TextView>(R.id.tv_article_publish_date)
    private val ivArticleOptionsButton  = view.findViewById<ImageView>(R.id.iv_article_options_button)


    override fun bind(item: Article, adapterItemPosition: Int) {
        with(item){
            tvArticleTitle.text = title
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