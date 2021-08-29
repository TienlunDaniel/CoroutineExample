package com.example.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.com.example.newsreader.service.NewsUtil
import com.example.librarybookrecommendation.model.News
import com.example.myapplication.R
import com.example.viewModel.NewViewModel
import com.google.gson.Gson
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NewsMainFragment : Fragment(R.layout.fragment_main) {

    val myScope = object : CoroutineScope {
        override val coroutineContext: CoroutineContext
            get() = Job() + Dispatchers.Main
    }

    val newViewModel: NewViewModel by activityViewModels()

    val gson = Gson()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (savedInstanceState == null)
            newsDialog()

        val listView = view.findViewById<ListView>(R.id.newsLV)

        newViewModel.newsLiveData.observe(viewLifecycleOwner) {

            val arrayAdapter =
                ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, it.map { it.title })
            listView.adapter = arrayAdapter

            listView.onItemClickListener =
                AdapterView.OnItemClickListener { adapterView, view, position, l ->
                    findNavController().navigate(
                        R.id.action_newsMainFragment3_to_newsDetailFragment
                    )
                }
            Log.d("News", it.toString())
        }
    }

    fun newsDialog() {
        myScope.launch {
            if (alertDialog("News Reader", "Do you want to retrieve news ?"))
                launch {
                    val scope = this
                    var newsList = mutableListOf<String>()
                    NewsUtil.getStoriesFlow().onCompletion {
                        scope.launch {
                            newsDialog()
                        }
                    }.collectLatest {
                        newsList.add(it)
                        val newsList : List<News> = newsList.map { gson.fromJson(it, News::class.java) }

                        GlobalScope.launch {
                            newViewModel.setNewsLiveData(newsList)
                        }
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
            val alert = AlertDialog.Builder(requireContext()).create()

            val job = scope.launch {
                delay(10000)
                Toast.makeText(
                    requireContext(),
                    "You are taking too long to respond!!",
                    Toast.LENGTH_SHORT
                ).show()
                it.resume(false)
                alert.dismiss()
            }

            alert.apply {
                setButton(
                    AlertDialog.BUTTON_POSITIVE,
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