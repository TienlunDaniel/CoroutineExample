package com.example.myapplication

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.com.example.newsreader.service.ApiService
import com.com.example.newsreader.service.NewsUtil
import com.com.example.newsreader.service.ReaderApiService
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine
import kotlin.lazy as lazy

class MainActivity : AppCompatActivity() {

    val myScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext
            get() = Job() + Dispatchers.Main
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsDialog()

        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this, TestActivity::class.java))
        }
    }

    fun newsDialog() {
        myScope.launch {
            if (alertDialog("News Reader", "Do you want to retrieve news ?"))
                launch {
                    val scope = this
                    NewsUtil.getStoriesFlow().onCompletion {
                        scope.launch {
                            newsDialog()
                        }
                    }.collectLatest {
                        Log.d("News", it)
                    }
                }
        }
    }

    suspend fun alertDialog(
        titleText: String,
        contentText: String,
        positiveText: String = "OK",
        negativeText: String = "NO"
    ): Boolean {

        val scope = myScope

        return suspendCoroutine<Boolean> {
            val alert = AlertDialog.Builder(this).create()

            val job = scope.launch {
                delay(10000)
                Toast.makeText(
                    this@MainActivity.baseContext,
                    "You are taking too long to respond!!",
                    Toast.LENGTH_SHORT
                ).show()
                it.resume(false)
                alert.dismiss()
            }

            alert.apply {
                setButton(AlertDialog.BUTTON_POSITIVE,
                    positiveText,
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        job.cancel()
                        it.resume(true)
                    })
                setButton(
                    AlertDialog.BUTTON_NEGATIVE,
                    negativeText,
                    DialogInterface.OnClickListener { dialogInterface, i ->
                        job.cancel()
                        it.resume(false)
                    })
                setTitle(titleText)
                setMessage(contentText)
            }

            alert.show()
        }
    }
}