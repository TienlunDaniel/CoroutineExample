package com.com.example.newsreader.service

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


object NewsUtil {

    val readerApiService: ReaderApiService by lazy {
        ApiService.getReaderApiService()
    }

    suspend fun getStoriesFlow(): Flow<String> {
        val newsResponse = readerApiService.moreInformation.await()
        val storyNumberIterator = "\\d+".toRegex().findAll(newsResponse).iterator()

        return flow<String> {
            storyNumberIterator.forEach {
                val number = it.groupValues[0]
                emit(readerApiService.getStoryInformation(number).await())
            }
        }
    }
}