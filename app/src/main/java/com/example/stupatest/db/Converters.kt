package com.example.stupatest.db

import androidx.room.TypeConverter
import com.example.stupatest.models.StarData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Converters {

    @TypeConverter
    fun fromStringToList(value: String?): List<StarData>? {
        val type: Type = object : TypeToken<List<StarData>?>() {}.type
        return Gson().fromJson(value, type)
    }

    @TypeConverter
    fun fromListToString(list: List<StarData>?): String? {
        val gson = Gson()
        return gson.toJson(list)
    }


}