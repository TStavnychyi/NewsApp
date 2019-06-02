package com.tstv.newsapp.ui.news.view_holders

import android.annotation.SuppressLint
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.tstv.newsapp.R
import com.tstv.newsapp.data.db.converters.LocalDateConverter
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.ui.base.BaseViewHolder
import com.tstv.newsapp.ui.news.dialogs.OptionsBottomSheetDialog
import com.tstv.newsapp.ui.news.fragments.NewsFragment
import com.tstv.newsapp.ui.news.fragments.NewsPageFragmentDirections

class NewsArticlesViewHolder(
    val view: View,
    private val adapterDataListSize: Int,
    private val newsFragment: NewsFragment)
    : BaseViewHolder<Article>(view) {

    companion object{
        fun create(parent: ViewGroup, adapterDataListSize: Int, newsFragment: NewsFragment) : NewsArticlesViewHolder{
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.adapter_item_news_article, parent, false)
            return NewsArticlesViewHolder(view, adapterDataListSize, newsFragment)
        }
    }

    private val tvArticleTitle = view.findViewById<TextView>(R.id.tv_article_title)
    private val ivArticleImage = view.findViewById<ImageView>(R.id.iv_article_image)
    private val tvArticlePublisher = view.findViewById<TextView>(R.id.tv_article_publisher_name)
    private val tvArticlePublishDate = view.findViewById<TextView>(R.id.tv_article_publish_date)
    private val ivArticleOptions = view.findViewById<ImageView>(R.id.iv_article_options_button)
    private val tvArticleContent = view.findViewById<TextView>(R.id.tv_article_content)
    private val cardView = view.findViewById<CardView>(R.id.cardView)
    private val dividerView = view.findViewById<View>(R.id.divider_line)

    private lateinit var articleItem: Article

     override fun bind(item: Article, adapterItemPosition: Int) {
        resetViewsParams()

        articleItem = item

        with(item){
            tvArticleTitle.text = title
            tvArticlePublisher.text = source?.name ?: author
            parseAndSetDateToView(publishedAt)

            if(urlToImage.isNullOrEmpty()){
                handleImageAbsence(content)
            }
            else {
                setupGlide(urlToImage!!)
            }
        }

        if ((adapterDataListSize - 1) == adapterItemPosition)
            dividerView.visibility = View.INVISIBLE
        else
            dividerView.visibility = View.VISIBLE

        fun openOptionsBottomSheetDialog(){
            var sourceName: String? = null
            if(!articleItem.source?.sourceID.isNullOrEmpty()){
                sourceName = articleItem.source?.name
            }
            val bottomSheetDialog = OptionsBottomSheetDialog.newInstance(sourceName, adapterItemPosition)
            bottomSheetDialog.show(newsFragment.childFragmentManager, OptionsBottomSheetDialog.TAG)
        }

        fun bindViewsClickListeners(){
            ivArticleOptions.setOnClickListener {
                openOptionsBottomSheetDialog() }

            view.setOnClickListener {
                val actionWithArgument = NewsPageFragmentDirections.actionFromNewsToNewsDetail(articleItem.id!!)
                findNavController(newsFragment).navigate(actionWithArgument)
            }
        }

        bindViewsClickListeners()
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
    private fun handleImageAbsence(contentText: String?){
        if(contentText != null) {
            cardView.visibility = View.GONE
            tvArticleContent.visibility = View.VISIBLE
            tvArticleTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.context.resources.getDimension(R.dimen.article_adapter_item_large_text_size) )
            tvArticleContent.text = contentText
        }
    }

    private fun resetViewsParams(){
        cardView.visibility = View.VISIBLE
        tvArticleContent.visibility = View.GONE
        tvArticleTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.context.resources.getDimension(R.dimen.article_adapter_item_normal_text_size))
    }
}