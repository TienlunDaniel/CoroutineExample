package com

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.librarybookrecommendation.database.AppDatabase

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        ctx = this
        bookDatabase =
            Room.databaseBuilder(applicationContext, AppDatabase::class.java, "NewsDatabase")
                .fallbackToDestructiveMigration().build()
    }

    companion object {
        var ctx: Context? = null
        var bookDatabase: AppDatabase? = null
    }
}