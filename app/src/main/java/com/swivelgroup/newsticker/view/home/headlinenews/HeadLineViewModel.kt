package com.swivelgroup.newsticker.view.home.headlinenews

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.swivelgroup.newsticker.api.repository.RepositoryProvider
import com.swivelgroup.newsticker.api.response.NewsBaseResponse

class HeadLineViewModel : ViewModel() {

    private val newsRepository = RepositoryProvider.provideNewsRepository()

    var dialogStatus = MutableLiveData<Int>()
    var liveNewsBaseResponse = MediatorLiveData<NewsBaseResponse>()
    val liveNoNews = MutableLiveData<Int>()

    init {
        dialogStatus.value = View.GONE
        liveNoNews.value = View.GONE
    }

    fun getNewsList(
        token: String,
        path: String,
        query: String,
        country: String,
        category: String,
        page: Int): LiveData<NewsBaseResponse>{

        val dataSource = newsRepository.getHeadLineNewsList(
            token, path, query, country, category, page)

        liveNewsBaseResponse.addSource(dataSource) { notificationData ->
            if (liveNewsBaseResponse.hasActiveObservers()) {
                liveNewsBaseResponse.removeSource(dataSource)
            }
            this.liveNewsBaseResponse.setValue(notificationData)
        }
        return liveNewsBaseResponse
    }
}
