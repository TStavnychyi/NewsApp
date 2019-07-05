package com.tstv.newsapp.ui.base

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.tstv.newsapp.R
import com.tstv.newsapp.data.db.converters.LocalDateConverter

abstract class BaseViewHolder <T> (view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bind(item: T, adapterItemPosition: Int)

    fun setupGlide(imageUrlToDownload: String, ivArticleImage: ImageView){
        val requestOptions = RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop()
            .override(ivArticleImage.width, ivArticleImage.height)
            .placeholder(R.drawable.image_placeholder)


        Glide.with(ivArticleImage.context)
            .load(imageUrlToDownload)
            .apply(requestOptions)
            .into(ivArticleImage)
    }

    fun parseAndSetDateToView(publishedAt: String): String {
        val localDate = LocalDateConverter.stringToDate(publishedAt)!!
        return "${localDate.dayOfMonth}-${localDate.month}-${localDate.year}"
    }
}