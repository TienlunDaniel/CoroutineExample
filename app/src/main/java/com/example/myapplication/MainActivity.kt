package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import com.com.example.newsreader.service.ApiService
import com.com.example.newsreader.service.NewsUtil
import com.com.example.newsreader.service.ReaderApiService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.lazy as lazy

class MainActivity : AppCompatActivity() {

    val readerApiService : ReaderApiService by lazy {
        ApiService.getReaderApiService()
    }

    val myScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext
            get() = Job() + Dispatchers.Main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myScope.launch {
            launch {
                NewsUtil.getStoriesFlow().collectLatest {
                    Log.d("News", it)
                }
            }

            launch {
                NewsUtil.getStoriesFlow().collectLatest {
                    Log.d("News", it)
                }
            }
        }

        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
    }
}