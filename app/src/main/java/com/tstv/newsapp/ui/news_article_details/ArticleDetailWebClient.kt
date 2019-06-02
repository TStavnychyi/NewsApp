package com.tstv.newsapp.ui.news_article_details

import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.constraintlayout.widget.Group

class ArticleDetailWebClient(
    private val progressBarGroup: Group,
    private val webView: WebView
) : WebViewClient() {

    init {
        progressBarGroup.visibility = View.VISIBLE
        webView.visibility = View.INVISIBLE
    }

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        view?.loadUrl(request?.url.toString())
        return true
    }

    override fun onPageFinished(view: WebView?, url: String?) {
        super.onPageFinished(view, url)
        progressBarGroup.visibility = View.GONE
        webView.visibility = View.VISIBLE
    }
}