package com.swivelgroup.newsticker.view.home.customnews

import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.swivelgroup.newsticker.R
import com.swivelgroup.newsticker.databinding.CustomNewsFragmentBinding
import com.swivelgroup.newsticker.model.NewsItem
import com.swivelgroup.newsticker.utils.Constants
import com.swivelgroup.newsticker.utils.PreferenceManager
import com.swivelgroup.newsticker.utils.isConnected
import com.swivelgroup.newsticker.utils.showAlertDialog
import com.swivelgroup.newsticker.view.base.BaseFragment
import com.swivelgroup.newsticker.view.home.NewsFragmentListener
import com.swivelgroup.newsticker.view.home.NewsListRecyclerViewAdapter
import com.swivelgroup.newsticker.view.home.NewsPagerAdapter
import com.swivelgroup.newsticker.view.newsdetails.NewsDetailsActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.custom_news_fragment.*
import kotlinx.android.synthetic.main.custom_news_fragment.swipeToRefreshView
import kotlinx.android.synthetic.main.head_line_fragment.*
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException

class CustomNewsFragment : BaseFragment(), NewsListRecyclerViewAdapter.OnClickListener, NewsFragmentListener {

    companion object {
        fun newInstance() = CustomNewsFragment()
    }

    private lateinit var viewModel: CustomNewsViewModel
    lateinit var preferenceManager: PreferenceManager

    var page = 1
    private var isLoading = false
    private var isFinished = false
    private var newsFullList = ArrayList<NewsItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        preferenceManager = PreferenceManager(activity!!)
        viewModel = ViewModelProviders.of(this).get(CustomNewsViewModel::class.java)
        val layoutBinding = CustomNewsFragmentBinding.inflate(
            inflater, container, false)
        layoutBinding.lifecycleOwner = this
        layoutBinding.viewModel = viewModel

        return layoutBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setObservers()
        initAdapter()
        initScrolling()

        swipeToRefreshView.setOnRefreshListener {
            if (swipeToRefreshView.isRefreshing) {
//                showUserPrefs()
                page = 1
                newsFullList.clear()
                if (preferenceManager.getPref().size > 0) {
                    loadNewsList(spinUserPrefs.selectedItem.toString())
                }else{
                    loadNewsList("")
                }
            }
        }
    }

    private fun initAdapter(){
        val newsAdapter = NewsListRecyclerViewAdapter(newsFullList, this)
        recyclerViewEverything.setHasFixedSize(true)
        recyclerViewEverything.layoutManager = LinearLayoutManager(activity)
        recyclerViewEverything.adapter = newsAdapter
    }

    private fun initScrolling(){
        recyclerViewEverything.addOnScrollListener(object : RecyclerView.OnScrollListener(){

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

                if (!isLoading && !isFinished) {

                    //start loading new item set 3 items before the last
                    if (linearLayoutManager.findLastCompletelyVisibleItemPosition()
                        == newsFullList.size - 3) {

                        loadNewsList(spinUserPrefs.selectedItem.toString())
                        isLoading = true
                    }
                }
            }
        })
    }

    private fun loadNewsList(keyword: String){
        if (isConnected(activity!!)) {
            isFinished = false
            swipeToRefreshView.isRefreshing = true

            viewModel.getNewsList(Constants.api_key, Constants.url_news_api_everything,
                keyword, "", "", page)
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

                            //increment the page by 1 after loading content and check whether all the pages are loaded
                            if (newsResponse.totalResults != null && newsResponse.totalResults/20 + 1 > page) {
                                page++
                            }else{
                                isFinished = true
                            }
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

    private fun showNewsList(newsList: ArrayList<NewsItem>){
        newsFullList.addAll(newsList)
        recyclerViewEverything.adapter?.notifyDataSetChanged()
        isLoading = false
    }

    private fun showUserPrefs(){
        val userPrefs =  preferenceManager.getPref()
        val arrayAdapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1,
            userPrefs.toTypedArray())
        spinUserPrefs.adapter = arrayAdapter
        spinUserPrefs.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                page = 1
                newsFullList.clear()
                loadNewsList(spinUserPrefs.selectedItem.toString())
            }
        }
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
        snackBar.setAction(getString(R.string.text_retry)) {
            if (spinUserPrefs.isSelected) {
                loadNewsList(spinUserPrefs.selectedItem.toString())
            }
        }
        snackBar.show()
    }

    override fun onClickItem(newsItem: NewsItem) {
        openNewsDetailsActivity(newsItem)
    }

    private fun openNewsDetailsActivity(newsItem: NewsItem){
        val intent = Intent(activity, NewsDetailsActivity::class.java)
        intent.putExtra(Constants.extra_news_item, newsItem)
        startActivity(intent)
    }

    override fun updateData() {
        if (preferenceManager.getPref().size > 0) {
            showUserPrefs()
//            loadNewsList(spinUserPrefs.selectedItem.toString())
        }else{
            showAlertDialog(activity!!, "Info", getString(R.string.error_no_user_prefs))
        }
    }
}
