package com.swivelgroup.newsticker.view.home.headlinenews

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.swivelgroup.newsticker.R
import com.swivelgroup.newsticker.databinding.HeadLineFragmentBinding
import com.swivelgroup.newsticker.model.NewsItem
import com.swivelgroup.newsticker.utils.Constants
import com.swivelgroup.newsticker.utils.isConnected
import com.swivelgroup.newsticker.utils.showAlertDialog
import com.swivelgroup.newsticker.view.base.BaseFragment
import com.swivelgroup.newsticker.view.home.NewsListRecyclerViewAdapter
import com.swivelgroup.newsticker.view.newsdetails.NewsDetailsActivity
import kotlinx.android.synthetic.main.head_line_fragment.*
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

class HeadLineFragment : BaseFragment(), NewsListRecyclerViewAdapter.OnClickListener {

    companion object {
        fun newInstance() = HeadLineFragment()
    }

    lateinit var containerView: View
    private lateinit var viewModel: HeadLineViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(HeadLineViewModel::class.java)
        val layoutBinding = HeadLineFragmentBinding.inflate(
            inflater, container, false)
        layoutBinding.lifecycleOwner = this
        layoutBinding.viewModel = viewModel

        containerView = layoutBinding.container
        return layoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        loadNewsList()

        swipeToRefreshView.setOnRefreshListener {
            if (swipeToRefreshView.isRefreshing) {
                loadNewsList()
            }
        }
    }

    //trigger the APi call through ViewModel
    private fun loadNewsList(){
        if (isConnected(activity!!)) {
            swipeToRefreshView.isRefreshing = true
            viewModel.getNewsList(Constants.api_key, Constants.url_news_api_top_headlines,"", "", "business")
        }else{
            handleError(null, false)
        }
    }

    //setup observers to listen to the respond
    private fun setObservers(){
        viewModel.liveNewsBaseResponse.observe(this, Observer {
            newsBaseResponse ->

            if (newsBaseResponse != null){
                val newsResponse = newsBaseResponse.newsResponse
                if (newsResponse != null ){
                    if(newsResponse.status == Constants.response_ok){

                        if (newsResponse.articles!!.isNotEmpty()) {
                            showNewsList(newsResponse.articles as ArrayList<NewsItem>)
                        } else {
                            viewModel.liveNoNews.value = View.VISIBLE
                        }
                    }else if (newsResponse.status == Constants.response_error){
                        handleErrors(newsResponse.code)
                    }
                }else if (newsBaseResponse.throwable != null){
                    handleError(newsBaseResponse.throwable!!, true)
                }
            }
            swipeToRefreshView.isRefreshing = false
        })
    }

    private fun handleError(throwable: Throwable?, isConnected: Boolean){

        var errorMessage = getString(R.string.error_common)

        if (!isConnected) {
            errorMessage = activity!!.getString(R.string.error_no_connection)
        } else {
            when (throwable) {
                is ConnectException -> {
                    errorMessage = activity!!.getString(R.string.error_failed_to_connect_to_server)
                }
                is TimeoutException -> {
                    errorMessage = activity!!.getString(R.string.error_timeout)
                }
                is SocketTimeoutException -> {
                    errorMessage = activity!!.getString(R.string.error_timeout)
                }
            }
        }
        swipeToRefreshView.isRefreshing = false

        val snackBar =
            Snackbar.make(containerView, errorMessage, Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction("Retry") {
            loadNewsList()
        }
        snackBar.show()
    }

    private fun showNewsList(newsList: ArrayList<NewsItem>){
        val newsAdapter = NewsListRecyclerViewAdapter(newsList, this)
        recyclerViewHeadLine.setHasFixedSize(true)
        recyclerViewHeadLine.layoutManager = LinearLayoutManager(activity)
        recyclerViewHeadLine.adapter = newsAdapter
    }

    override fun onClickItem(newsItem: NewsItem) {
        openNewsDetailsActivity(newsItem)
    }

    private fun openNewsDetailsActivity(newsItem: NewsItem){
        val intent = Intent(activity, NewsDetailsActivity::class.java)
        intent.putExtra(Constants.extra_news_item, newsItem)
        startActivity(intent)
    }
}
