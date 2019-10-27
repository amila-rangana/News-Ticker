package com.swivelgroup.newsticker.view.newsdetails


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.swivelgroup.newsticker.R
import com.swivelgroup.newsticker.databinding.FragmentNewsLinkBinding
import com.swivelgroup.newsticker.utils.Constants
import kotlinx.android.synthetic.main.fragment_news_link.*


/**
 * A simple [Fragment] subclass.
 */
class NewsLinkFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this

        val layoutBinding = FragmentNewsLinkBinding.inflate(
            inflater, container, false)
        return layoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (arguments!=null){
            val newsUrl = arguments?.getString(Constants.extra_news_url, "")
            webViewNews.loadUrl(newsUrl)
            webViewNews.settings.javaScriptEnabled = true
        }
    }
}
