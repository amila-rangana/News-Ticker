package com.swivelgroup.newsticker.api.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.swivelgroup.newsticker.BuildConfig
import com.swivelgroup.newsticker.api.response.NewsBaseResponse
import com.swivelgroup.newsticker.api.response.NewsResponse
import com.swivelgroup.newsticker.api.service.NewsAPIService
import com.swivelgroup.newsticker.utils.getRequestHeaders
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi




class NewsRepositoryImpl: NewsRepository {

    private var apiService: NewsAPIService
    private var retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create())
        .baseUrl(BuildConfig.BASE_URL)
        .build()

    init {
        apiService = retrofit.create(NewsAPIService::class.java)
    }

    override fun getHeadLineNewsList(
        token: String,
        path:String,
        query: String,
        country: String,
        category: String
    ): LiveData<NewsBaseResponse> {

        val newsLiveData = MutableLiveData<NewsBaseResponse>()
        val call = apiService.getNewsList(
            getRequestHeaders(), path, query, country, category)

        val baseResponse = NewsBaseResponse()
        call.enqueue(object : Callback<NewsResponse> {
            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                baseResponse.throwable = t
                newsLiveData.value = baseResponse
            }

            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    baseResponse.newsResponse = response.body()
                    newsLiveData.value = baseResponse
                }else{

                    baseResponse.newsResponse = convertErrorToObject(response.errorBody()?.string()!!)
                    newsLiveData.value = baseResponse
                }
            }
        })
        return newsLiveData
    }

    private fun convertErrorToObject(json:String) :NewsResponse?{
        val moshi = Moshi.Builder().build()
        val jsonAdapter = moshi.adapter<NewsResponse>(NewsResponse::class.java)

        return jsonAdapter.fromJson(json)
    }
}