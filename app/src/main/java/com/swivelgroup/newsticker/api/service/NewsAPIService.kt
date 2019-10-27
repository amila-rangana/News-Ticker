package com.swivelgroup.newsticker.api.service

import com.swivelgroup.newsticker.api.response.NewsResponse
import com.swivelgroup.newsticker.utils.Constants
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.HeaderMap
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsAPIService {

    @GET(Constants.url_news_api_everything)
    fun getNewsEverything(@HeaderMap hashMap: HashMap<String, String>, @Query("q") query: String): Call<NewsResponse>

    @GET("{path}")
    fun getNewsList(@HeaderMap hashMap: HashMap<String, String>,
                    @Path("path") path: String,
                    @Query("q") query: String,
                    @Query("country") country: String,
                    @Query("category") category: String): Call<NewsResponse>

}