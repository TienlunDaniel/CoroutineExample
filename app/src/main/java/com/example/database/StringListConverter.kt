package com.example.librarybookrecommendation.database

import androidx.room.TypeConverter

class StringListConverter {
    @TypeConverter
    fun fromStringList(strList: List<String>?): String {
        if (strList == null)
            return ""

        return strList.joinToString(separator = ",")
    }

    @TypeConverter
    fun toStringList(plainString: String?): List<String> {
        if (plainString == null)
            return listOf()

        return plainString.split(",").toList()
    }
}