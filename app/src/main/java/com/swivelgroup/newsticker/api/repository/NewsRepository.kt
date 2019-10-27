package com.swivelgroup.newsticker.api.repository

import androidx.lifecycle.LiveData
import com.swivelgroup.newsticker.api.response.NewsBaseResponse

interface NewsRepository {

    fun getHeadLineNewsList(token: String, path: String, query: String, country:String,
                            category:String, page:Int):
            LiveData<NewsBaseResponse>

}