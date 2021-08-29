package com.example.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.NewsApplication
import com.example.librarybookrecommendation.model.News

class NewViewModel : ViewModel() {

    val newsDao by lazy {
        NewsApplication.bookDatabase?.newsDao()!!
    }

    val newsLiveData: LiveData<List<News>> by lazy {
        newsDao.getNewsLiveData()
    }

    val newsDetailLiveData: LiveData<String> = MutableLiveData<String>()

    fun setNewsDetailLiveData(newsDetail: String) {
        ((newsDetailLiveData as MutableLiveData<String>).value) = newsDetail
    }

    suspend fun setNewsLiveData(news: List<News>) {
        newsDao.upsert(news)
    }

}