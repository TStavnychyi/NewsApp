package com.tstv.newsapp.ui.news.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
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
import com.tstv.newsapp.data.db.entity.ArticleEntry
import com.tstv.newsapp.internal.glide.GlideApp
import com.tstv.newsapp.ui.news.NewsFragment
import com.tstv.newsapp.ui.news.OptionsBottomSheetDialogFragment

class NewsAdapter(
    private val newsFragment: NewsFragment,
    private val dataList: MutableList<ArticleEntry>
): RecyclerView.Adapter<NewsAdapter.BaseViewHolder<*>>() {

    private var currentViewID: Int = 0

    private lateinit var context: Context

    companion object{
        private const val TYPE_INNER_RECYCLER_VIEW = 1
        private const val TYPE_ARTICLE_VIEW = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        context = parent.context

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
        (holder as ArticleViewHolder).bind(dataElement, (dataList.size - 1) == position)
//        when(holder){
//            is ArticleCardViewsViewHolder -> holder.bind(dataElement as List<ArticleEntry>)
//            is ArticleViewHolder -> holder.bind(dataElement as ArticleEntry)
//            else -> throw IllegalArgumentException()
//        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(dataList[position]){
//            is List<*> -> {
//                currentViewID = TYPE_INNER_RECYCLER_VIEW
//                TYPE_INNER_RECYCLER_VIEW
//            }
            is ArticleEntry -> {
                currentViewID = TYPE_ARTICLE_VIEW
                TYPE_ARTICLE_VIEW
            }
            else -> {
                throw IllegalArgumentException("Invalid type of data in $position position")
            }
        }
    }

//    private fun checkIfArticleContentValid(contentText: String?, position: Int): Boolean{
//        return if(contentText == null
//            || contentText.contains("[[getSimpleString(data.title)]]")
//            || contentText.contains("[[getSimpleString(data.text)]]")
//            || contentText == "null") {
//            removeAdapterItem(position)
//            false
//        }else
//            true
//    }

    abstract inner class BaseViewHolder<T>(itemView: View): RecyclerView.ViewHolder(itemView){
        abstract fun bind(item: T, isLastItem: Boolean)
    }

    inner class ArticleCardViewsViewHolder(
        private val view: View
    ): BaseViewHolder<List<ArticleEntry>>(view){

        private val cardViewsRecyclerView = view.findViewById<RecyclerView>(R.id.home_news_adapter_inner_recycler_view_item)

        override fun bind(item: List<ArticleEntry>, isLastItem: Boolean) {
            bindRecyclerView(item)
        }

        private fun bindRecyclerView(articlesList: List<ArticleEntry>){
            cardViewsRecyclerView.apply {
                layoutManager = LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
                val cardViewAdapter = NewsCardViewAdapter(articlesList)
                adapter = cardViewAdapter
            }
        }
    }

    inner class ArticleViewHolder(
        val view: View
    ): BaseViewHolder<ArticleEntry>(view){

        private val tvArticleTitle = view.findViewById<TextView>(R.id.tv_article_title)
        private val ivArticleImage = view.findViewById<ImageView>(R.id.iv_article_image)
        private val tvArticlePublisher = view.findViewById<TextView>(R.id.tv_article_publisher_name)
        private val tvArticlePublishDate = view.findViewById<TextView>(R.id.tv_article_publish_date)
        private val ivSaveArticleToBookmark = view.findViewById<ImageView>(R.id.iv_article_save_bookmark)
        private val ivArticleOptions = view.findViewById<ImageView>(R.id.iv_article_options_button)
        private val tvArticleContent = view.findViewById<TextView>(R.id.tv_article_content)
        private val cardView = view.findViewById<CardView>(R.id.cardView)
        private val dividerView = view.findViewById<View>(R.id.divider_line)

        private lateinit var articleItem: ArticleEntry

        override fun bind(item: ArticleEntry, isLastItem: Boolean) {
            resetViewsParams()

            articleItem = item

            with(item){
                tvArticleTitle.text = title
                tvArticlePublisher.text = author
                parseAndSetDateToView(publishedAt)

                if(urlToImage != null && item.urlToImage.isEmpty()) {
                    handleImageAbsence(content)
                }
                else
                    setupGlide(urlToImage)
            }

            if (isLastItem)
                dividerView.visibility = View.INVISIBLE
            else
                dividerView.visibility = View.VISIBLE

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
                        handleImageAbsence(articleItem.content)
                        return true
                    }

                }).into(ivArticleImage)
        }

        @SuppressLint("SetTextI18n")
        private fun parseAndSetDateToView(publishedAt: String){
            val localDate = LocalDateConverter.stringToDate(publishedAt)!!
            tvArticlePublishDate.text = "${localDate.dayOfMonth}-${localDate.month}-${localDate.year}"
        }
        private fun handleImageAbsence(contentText: String){
            cardView.visibility = View.GONE
            tvArticleContent.visibility = View.VISIBLE
            tvArticleTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.context.resources.getDimension(R.dimen.article_adapter_item_large_text_size))
            tvArticleContent.text = contentText
        }

        private fun resetViewsParams(){
            cardView.visibility = View.VISIBLE
            tvArticleContent.visibility = View.GONE
            tvArticleTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, view.context.resources.getDimension(R.dimen.article_adapter_item_normal_text_size))
        }

        private fun bindViewsClickListeners(){
            ivSaveArticleToBookmark.setOnClickListener { //TODO
            }
            ivArticleOptions.setOnClickListener { openOptionsBottomSheetDialog() }

            view.setOnClickListener { //TODO
            }
        }

        private fun openOptionsBottomSheetDialog(){
            val bottomSheetDialog = OptionsBottomSheetDialogFragment()
            bottomSheetDialog.show(newsFragment.childFragmentManager, OptionsBottomSheetDialogFragment.TAG)
        }

    }
}