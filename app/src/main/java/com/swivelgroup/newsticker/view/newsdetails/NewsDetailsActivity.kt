package com.swivelgroup.newsticker.view.newsdetails

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.swivelgroup.newsticker.R
import com.swivelgroup.newsticker.model.NewsItem
import com.swivelgroup.newsticker.utils.Constants

class NewsDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_details)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "News Detail"

        if (intent !=null) {
            val newsItem = intent.getParcelableExtra<NewsItem>(Constants.extra_news_item)
            if (newsItem != null) {
                loadNewsDetailsFragment(newsItem as NewsItem)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun loadNewsDetailsFragment(newsItem: NewsItem){
        val fragment = NewsDetailsFragment()
        val bundle = Bundle()
        bundle.putParcelable(Constants.extra_news_item, newsItem)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.containerView, fragment)
            .disallowAddToBackStack().commit()
    }
}
