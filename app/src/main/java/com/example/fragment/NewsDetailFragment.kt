package com.example.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.com.example.newsreader.service.NewsUtil
import com.example.myapplication.R
import com.example.viewModel.NewViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onCompletion
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class NewsDetailFragment : Fragment(R.layout.fragment_detail) {

    companion object {
        const val NEWS_DETAIL = "NEWS_DETAIL"

        fun getBundle(newsDetail: String): Bundle =
            Bundle().apply {
                putString(
                    NEWS_DETAIL,
                    newsDetail
                )
            }
    }

    val newViewModel: NewViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newViewModel.newsDetailLiveData.observe(viewLifecycleOwner){
            view.findViewById<TextView>(R.id.detailText).text = it
        }
    }
}