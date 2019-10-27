package com.swivelgroup.newsticker.view.newsdetails

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swivelgroup.newsticker.R
import com.swivelgroup.newsticker.databinding.NewsDetailsFragmentBinding
import com.swivelgroup.newsticker.model.NewsItem
import com.swivelgroup.newsticker.utils.Constants
import kotlinx.android.synthetic.main.news_details_fragment.*

class NewsDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = NewsDetailsFragment()
    }

    private lateinit var viewModel: NewsDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(NewsDetailsViewModel::class.java)
        val layoutBinding = NewsDetailsFragmentBinding.inflate(
            inflater, container, false)
        layoutBinding.lifecycleOwner = this
        layoutBinding.viewModel = viewModel

        return layoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val newsItem = arguments?.getParcelable<NewsItem>(Constants.extra_news_item)
        showNewsDetails(newsItem as NewsItem)

        txtNewsUrl.setOnClickListener {
            if (newsItem.url != null) {
                openFullNews(newsItem.url)
            }
        }
    }

    private fun openFullNews(url:String){
        val fragment = NewsLinkFragment()
        val bundle = Bundle()
        bundle.putString(Constants.extra_news_url, url)
        fragment.arguments = bundle
        activity!!.supportFragmentManager.beginTransaction().replace(R.id.containerView, fragment)
            .addToBackStack("NewsLinkFragment").commit()
    }

    private fun showNewsDetails(newsItem: NewsItem){
        viewModel.liveNewsDetails.value = newsItem.description
        viewModel.livePublishedTime.value = newsItem.publishedAt
        viewModel.liveFullUrl.value = getString(R.string.text_open_full_news)
        viewModel.liveTitle.value = newsItem.title
        viewModel.liveImage.value = newsItem.urlToImage
    }
}
