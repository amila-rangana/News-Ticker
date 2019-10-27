package com.swivelgroup.newsticker.view.home.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    val liveUsername = MutableLiveData<String>()
    val liveNewPreference = MutableLiveData<String>()
    val liveAddBtnStatus = MutableLiveData<Boolean>()

    init {
        liveAddBtnStatus.value = false
    }
}
