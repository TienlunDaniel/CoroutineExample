package com.example.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewViewModel : ViewModel() {

    val newsLiveData: LiveData<List<String>> = MutableLiveData<List<String>>();

    fun setNewsLiveData(news: List<String>) {
        ((newsLiveData as MutableLiveData<List<String>>).value) = news
    }

}