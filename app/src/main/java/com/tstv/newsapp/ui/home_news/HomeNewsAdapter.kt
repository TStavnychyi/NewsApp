package com.tstv.newsapp.ui.home_news

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
import com.tstv.newsapp.data.db.entity.Article
import com.tstv.newsapp.internal.glide.GlideApp

class HomeNewsAdapter(
    val dataList: List<*>
): RecyclerView.Adapter<HomeNewsAdapter.BaseViewHolder<*>>() {

    companion object{
        private val TYPE_VIEW_PAGER = 0
        private val TYPE_ARTICLE_VIEW = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val context = parent.context
        return when(viewType){
            TYPE_ARTICLE_VIEW -> {
                val view = LayoutInflater.from(context).inflate(R.layout.home_news_adapter_normal_view_item, parent, false)
                ArticleViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        val dataElement = dataList[position]
        when(holder){
            is ArticleViewPagerViewHolder -> holder.bind(dataElement as List<Article>)
            is ArticleViewHolder -> holder.bind(dataElement as Article)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(dataList[position]){
            is List<*> -> TYPE_VIEW_PAGER
            is Article -> TYPE_ARTICLE_VIEW
            else -> throw IllegalArgumentException("Invalid type of data in $position position")
        }
    }

    abstract class BaseViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView){
        abstract fun bind(item: T)
    }

    class ArticleViewPagerViewHolder(
        val view: View,
        val data: List<List<*>>
    ): BaseViewHolder<List<Article>>(view){


        override fun bind(item: List<Article>) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    }

    class ArticleViewHolder(
        val view: View
    ): BaseViewHolder<Article>(view){

        private val tvArticleCategory = view.findViewById<TextView>(R.id.tv_article_category)
        private val tvArticleTitle = view.findViewById<TextView>(R.id.tv_article_title)
        private val ivArticleImage = view.findViewById<ImageView>(R.id.iv_article_image)
        private val tvArticlePublisher = view.findViewById<TextView>(R.id.tv_article_publisher_name)
        private val tvArticlePublishDate = view.findViewById<TextView>(R.id.tv_article_publish_date)
        private val ivSaveArticleToBookmark = view.findViewById<ImageView>(R.id.iv_article_save_bookmark)
        private val ivArticleOptions = view.findViewById<ImageView>(R.id.iv_article_options_button)

        override fun bind(item: Article) {
            with(item){
                tvArticleTitle.text = title
                tvArticlePublisher.text = author
                parseAndSetDateToView(publishedAt)
                    if(urlToImage.isEmpty())
                    ivArticleImage.visibility = View.GONE
                else
                    setupGlide(urlToImage)
            }
            bindViewsClickListeners()
        }

        private fun setupGlide(imageUrlToDownload: String){
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .override(80, 80)
                .centerCrop()
                .placeholder(R.drawable.ic_check)

            GlideApp.with(view.context)
                .load(imageUrlToDownload)
                .apply(requestOptions)
                .listener(object: RequestListener<Drawable>{
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

        private fun bindViewsClickListeners(){
            ivSaveArticleToBookmark.setOnClickListener { //TODO
            }
            ivArticleOptions.setOnClickListener { //TODO
            }

            view.setOnClickListener { //TODO
            }
        }

    }

}