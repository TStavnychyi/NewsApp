package com.tstv.newsapp.ui.news_article_details


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.tstv.newsapp.R
import com.tstv.newsapp.data.vo.Article
import com.tstv.newsapp.data.vo.BookmarksArticle
import com.tstv.newsapp.ui.base.ScopedFragment
import kotlinx.android.synthetic.main.fragment_news_article_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ArticleDetailFragment : ScopedFragment(), KodeinAware {

    private val args: ArticleDetailFragmentArgs by navArgs()

    override val kodein by closestKodein()

    private val viewModelFactory: ArticleDetailsViewModelFactory by instance()

    private lateinit var viewModel: ArticleDetailViewModel

    private var articleId = -1

    private lateinit var article: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news_article_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this@ArticleDetailFragment, viewModelFactory).get(ArticleDetailViewModel::class.java)

        articleId = args.articleId

        bindUI()
    }

    private fun bindUI() = launch(Dispatchers.Main) {
        initToolbar()

        val webClient = ArticleDetailWebClient(news_group_loading_bar, web_view_content)

        val articleItem = viewModel.getNewsArticleById(articleId)

        articleItem.observe(this@ArticleDetailFragment, Observer {
            web_view_content.webViewClient = webClient
            web_view_content.loadUrl(it.url)

            article = it
        })
    }

    private fun initToolbar(){
        toolbar.inflateMenu(R.menu.article_detail_no_options_menu)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.share_article -> {
                    shareContent(article)
                    true
                }
                R.id.save_article -> {
                    launch(Dispatchers.IO) {
                        val articleBookmark = BookmarksArticle(article)
                        viewModel.saveArticleBookmark(articleBookmark)
                        showToast("Article was successfully saved")
                    }
                    true
                }
                else -> {
                    super.onOptionsItemSelected(it)
                }
            }
        }
        toolbar.setNavigationOnClickListener {
            val navController = findNavController()
            navController.navigateUp()
        }
        toolbar.navigationIcon = context?.getDrawable(R.drawable.ic_arrow_back)
        toolbar.overflowIcon = context?.getDrawable(R.drawable.ic_more_vert_white)
    }

    private fun shareContent(article: Article){
        val myIntent = Intent(Intent.ACTION_SEND)
        myIntent.type = "type/palin"
        val shareText = StringBuilder()
        with(article) {
            if(!author.isNullOrEmpty())
                shareText.append("$author: ")

            if (title.isNotEmpty()) {
                shareText.append("$title.")
                shareText.append("\n")
            }

            if(!url.isNullOrEmpty())
                shareText.append(url)
        }
        myIntent.putExtra(Intent.EXTRA_TEXT, shareText.toString())
        startActivity(Intent.createChooser(myIntent, "Sharing news"))
    }

    private fun showToast(message: String) {
        launch(Dispatchers.Main) {
            Toast.makeText(context!!, message, Toast.LENGTH_LONG).show()
        }
    }

}
