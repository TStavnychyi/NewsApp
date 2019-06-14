package com.tstv.newsapp.ui.bookmarks.view_holders

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.tstv.newsapp.R
import com.tstv.newsapp.data.db.converters.LocalDateConverter
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.internal.listeners.RecyclerViewItemClickListener
import com.tstv.newsapp.ui.base.BaseViewHolder

class ArticleBookmarkViewHolder(
    private val view: View,
    private val rvItemClickListener: RecyclerViewItemClickListener,
    private val adapterDataListSize: Int
): BaseViewHolder<Article>(view), View.OnClickListener {

    init {
        view.setOnClickListener(this)
    }

    companion object{
        fun create(parent: ViewGroup, rvItemClickListener: RecyclerViewItemClickListener, adapterDataListSize: Int) : ArticleBookmarkViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_item_popular_articles, parent, false)
            return ArticleBookmarkViewHolder(
                view,
                rvItemClickListener,
                adapterDataListSize
            )
        }
    }

    override fun onClick(v: View?) = rvItemClickListener.onClick(v!!, adapterPosition)


    private val tvArticleTitle = view.findViewById<TextView>(R.id.tv_article_title)
    private val ivArticleImage = view.findViewById<ImageView>(R.id.iv_article_image)
    private val tvArticlePublisher = view.findViewById<TextView>(R.id.tv_article_publisher_name)
    private val tvArticlePublishDate = view.findViewById<TextView>(R.id.tv_article_publish_date)
    private val ivArticleOptions = view.findViewById<ImageView>(R.id.iv_article_options_button)
    private val dividerView = view.findViewById<View>(R.id.divider_line)

    private lateinit var articleItem: Article

     override fun bind(item: Article, adapterItemPosition: Int) {
        resetViewsParams()

        articleItem = item

        with(item){
            tvArticleTitle.text = title
            tvArticlePublisher.text = source?.name ?: author
            parseAndSetDateToView(publishedAt)

            if(!urlToImage.isNullOrEmpty()){
                setupGlide(urlToImage!!)
            }
        }

        if ((adapterDataListSize - 1) == adapterItemPosition)
            dividerView.visibility = View.INVISIBLE
        else
            dividerView.visibility = View.VISIBLE
    }

    private fun setupGlide(imageUrlToDownload: String){
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .override(ivArticleImage.width, ivArticleImage.height)
            .placeholder(R.drawable.image_placeholder)


        Glide.with(view.context)
            .load(imageUrlToDownload)
            .apply(requestOptions)
            .into(ivArticleImage)
    }

    @SuppressLint("SetTextI18n")
    private fun parseAndSetDateToView(publishedAt: String){
        val localDate = LocalDateConverter.stringToDate(publishedAt)!!
        tvArticlePublishDate.text = "${localDate.dayOfMonth}-${localDate.month}-${localDate.year}"
    }

    private fun resetViewsParams(){
        tvArticleTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.context.resources.getDimension(R.dimen.article_adapter_item_normal_text_size))
    }

}