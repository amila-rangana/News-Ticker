package com.swivelgroup.newsticker.view.newsdetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsDetailsViewModel : ViewModel() {

    val liveNewsDetails = MutableLiveData<String>()
    val livePublishedTime = MutableLiveData<String>()
    val liveFullUrl = MutableLiveData<String>()
    val liveImage = MutableLiveData<String>()
    val liveTitle = MutableLiveData<String>()

}
