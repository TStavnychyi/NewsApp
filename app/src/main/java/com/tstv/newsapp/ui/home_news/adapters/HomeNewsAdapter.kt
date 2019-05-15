package com.tstv.newsapp.ui.home_news.adapters

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
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
    val dataList: List<Any>
): RecyclerView.Adapter<HomeNewsAdapter.BaseViewHolder<*>>() {

    private var currentViewID: Int = 0

    companion object{
        private const val TYPE_INNER_RECYCLER_VIEW = 1
        private const val TYPE_ARTICLE_VIEW = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val context = parent.context
        return when(viewType){
            TYPE_ARTICLE_VIEW -> {
                val view = LayoutInflater.from(context).inflate(R.layout.adapter_item_news_article, parent, false)
                ArticleViewHolder(view)
            }
            TYPE_INNER_RECYCLER_VIEW -> {
                val view = LayoutInflater.from(context).inflate(R.layout.home_news_adapter_inner_recycler_view_item, parent, false)
                ArticleCardViewsViewHolder(view)
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
            is ArticleCardViewsViewHolder -> holder.bind(dataElement as List<Article>)
            is ArticleViewHolder -> holder.bind(dataElement as Article)
            else -> throw IllegalArgumentException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(dataList[position]){
            is List<*> -> {
                currentViewID = TYPE_INNER_RECYCLER_VIEW
                TYPE_INNER_RECYCLER_VIEW
            }
            is Article -> {
                currentViewID = TYPE_ARTICLE_VIEW
                TYPE_ARTICLE_VIEW
            }
            else -> {
                throw IllegalArgumentException("Invalid type of data in $position position")
            }
        }
    }

    abstract class BaseViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView){
        abstract fun bind(item: T)
    }

    class ArticleCardViewsViewHolder(
        private val view: View
        ): BaseViewHolder<List<Article>>(view){

        private val title = view.findViewById<TextView>(R.id.tv_section_title)
        private val cardViewsRecyclerView = view.findViewById<RecyclerView>(R.id.home_news_adapter_inner_recycler_view_item)

        override fun bind(item: List<Article>) {
            bindRecyclerView(item)
            title.text = "Popular here"
        }

        private fun bindRecyclerView(articlesList: List<Article>){

            cardViewsRecyclerView.layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
            val cardViewAdapter = HomeNewsCardViewAdapter(articlesList)
            cardViewsRecyclerView.adapter = cardViewAdapter
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

                if(urlToImage != null && item.urlToImage.isEmpty())
                    ivArticleImage.visibility = View.GONE
                else
                    setupGlide(urlToImage)
            }
            bindViewsClickListeners()
        }

        private fun setupGlide(imageUrlToDownload: String){
            val requestOptions = RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
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