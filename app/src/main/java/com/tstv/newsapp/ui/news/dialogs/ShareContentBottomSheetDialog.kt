package com.tstv.newsapp.ui.news.dialogs

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.tstv.newsapp.R
import com.tstv.newsapp.ui.news.view_models.NewsViewModel
import com.tstv.newsapp.ui.news.view_models.NewsViewModelFactory
import kotlinx.android.synthetic.main.share_article_content_bottom_sheet_dialog_layout.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.closestKodein
import org.kodein.di.generic.instance

class ShareContentBottomSheetDialog : BottomSheetDialogFragment(), KodeinAware {

    override val kodein by closestKodein()

    private val vieWModelFactoryInstanceFactory: NewsViewModelFactory by instance()

    private lateinit var viewModel: NewsViewModel

    override fun getTheme(): Int = R.style.BottomSheetDialogTheme

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog = BottomSheetDialog(requireContext(), theme)

    companion object{
        const val TAG = "share_content_bottom_sheet_dialog"
        const val ARTICLE_ID_ARG = "article_id_arg"

        fun newInstance(selectedPosition: Int): OptionsBottomSheetDialog {
            return OptionsBottomSheetDialog().apply {
                arguments = bundleOf(
                    ARTICLE_ID_ARG to selectedPosition
                )
            }
        }
    }

    private var articleID: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getSerializable(ARTICLE_ID_ARG)?.let {
            articleID = it as Int
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.share_article_content_bottom_sheet_dialog_layout, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this, vieWModelFactoryInstanceFactory).get(NewsViewModel::class.java)

        bindUI()
    }

    private fun bindUI() {
        GlobalScope.launch(Dispatchers.Main) {
            val article = viewModel.getNewsArticleByIdAsync(articleID)
            article.observe(this@ShareContentBottomSheetDialog, Observer {
                with(article.value!!){
                    tv_article_title.text = title
                }
            })
        }
    }
}