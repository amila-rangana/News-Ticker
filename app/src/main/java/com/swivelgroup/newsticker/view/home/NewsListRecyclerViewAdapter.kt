package com.swivelgroup.newsticker.view.home

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.swivelgroup.newsticker.R
import com.swivelgroup.newsticker.databinding.ItemNewsListBinding
import com.swivelgroup.newsticker.model.NewsItem

class NewsListRecyclerViewAdapter(private val mNewsList: ArrayList<NewsItem>,
                                  private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<NewsListRecyclerViewAdapter.NewsItemViewHolder>() {

    interface OnClickListener{
        fun onClickItem(newsItem: NewsItem)
    }

    lateinit var resources: Resources
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        val context = parent.context

        val inflater = LayoutInflater.from(context)
        val contactView = ItemNewsListBinding.inflate(
            inflater, parent, false)


        return NewsItemViewHolder(contactView)
    }

    override fun getItemCount(): Int {
        return mNewsList.size
    }

    override fun onBindViewHolder(viewHolder: NewsItemViewHolder, position: Int) {

        if (mNewsList.size > 0) {
            val itemView = viewHolder.itemNewsListBinding
            val newsItem = mNewsList[position]

            Glide.with(itemView.root).load(newsItem.urlToImage).placeholder(R.drawable.loader)
                .error(R.drawable.error_image).into(itemView.imgNewsItem)
            itemView.txtNewsTitle.text = newsItem.title

            viewHolder.itemView.setOnClickListener {
                onClickListener.onClickItem(newsItem)
            }
        }
    }

    inner class NewsItemViewHolder(val itemNewsListBinding: ItemNewsListBinding) :
        RecyclerView.ViewHolder(itemNewsListBinding.root)
}