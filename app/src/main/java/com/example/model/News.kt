package com.example.librarybookrecommendation.model

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.librarybookrecommendation.database.BaseDao
import java.io.Serializable


@Entity
data class News(
    @PrimaryKey val id: Int,
    val by: String,
    val descendants: Int,
    val kids: List<String>,
    val score: Int,
    val time: Long,
    val title: String,
    val type: String,
    val url: String
) : Serializable


@Dao
abstract class NewsDao : BaseDao<News>() {

    @Query("SELECT * FROM News")
    abstract fun getNewsLiveData(): LiveData<List<News>>

    @Query("SELECT COUNT(*) FROM News")
    abstract fun getCount(): Int

    @Query("SELECT COUNT(*) FROM News")
    abstract fun getCountLiveData(): LiveData<Int>

    @Query("SELECT * FROM News ORDER BY RANDOM() LIMIT :num")
    abstract fun getBooks(num: Int): List<News>
}

