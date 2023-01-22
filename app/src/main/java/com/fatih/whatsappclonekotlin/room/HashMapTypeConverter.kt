package com.fatih.whatsappclonekotlin.room

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HashMapTypeConverter {
    @TypeConverter
    fun fromHashMap(value: HashMap<String,String>?): String {
        return value.toString()
    }

    @TypeConverter
    fun toHashMap(value: String): HashMap<String,String>? {
        val mapType = object : TypeToken<HashMap<String, String>>() {}.type
        return Gson().fromJson(value, mapType)
    }
}