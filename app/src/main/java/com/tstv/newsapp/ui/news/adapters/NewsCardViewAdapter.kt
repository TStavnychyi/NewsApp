package com.tstv.newsapp.ui.news.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.tstv.newsapp.R
import com.tstv.newsapp.data.db.LocalDateConverter
import com.tstv.newsapp.data.db.entity.ArticleEntry
import com.tstv.newsapp.internal.glide.GlideApp

class NewsCardViewAdapter(
    private val articleList: List<ArticleEntry>
): RecyclerView.Adapter<NewsCardViewAdapter.CardView_ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardView_ViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.adapter_item_top_news_feed, parent, false)
        return CardView_ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    override fun onBindViewHolder(holder: CardView_ViewHolder, position: Int) {
        val articleItem = articleList[position]
        holder.bind(articleItem)
    }


    class CardView_ViewHolder(
        private val view: View
    ): RecyclerView.ViewHolder(view){

        private val ivArticleImage = view.findViewById<ImageView>(R.id.iv_article_image)
        private val tvArticleTitle = view.findViewById<TextView>(R.id.tv_article_title)
        private val tvArticlePublisher = view.findViewById<TextView>(R.id.tv_article_publisher_name)
        private val tvArticlePublishDate = view.findViewById<TextView>(R.id.tv_article_publish_date)

        fun bind(article: ArticleEntry){
            with(article){
                tvArticlePublisher.text = author
                tvArticleTitle.text = title
                parseAndSetDateToView(article.publishedAt)
                if(urlToImage != null && urlToImage.isEmpty())
                    ivArticleImage.visibility = View.GONE
                else
                    setupGlide(urlToImage!!)
            }
        }

        private fun setupGlide(imageUrlToDownload: String){
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()

            GlideApp.with(view.context)
                .load(imageUrlToDownload)
                .apply(requestOptions)
                .listener(object: RequestListener<Drawable> {
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean
                    ): Boolean {
                        ivArticleImage.setImageDrawable(resource)
                        return true
                    }

                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean
                    ): Boolean {
                        ivArticleImage.visibility = View.GONE
                        return true
                    }

                }).into(ivArticleImage)
        }


        @SuppressLint("SetTextI18n")
        private fun parseAndSetDateToView(publishedAt: String){
            val localDate = LocalDateConverter.stringToDate(publishedAt)!!
            tvArticlePublishDate.text = "${localDate.dayOfMonth}-${localDate.month}-${localDate.year}"
        }

    }
}