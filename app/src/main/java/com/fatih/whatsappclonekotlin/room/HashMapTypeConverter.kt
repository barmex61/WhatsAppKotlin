package com.fatih.whatsappclonekotlin.room

import androidx.room.TypeConverter
import com.fatih.whatsappclonekotlin.model.Message
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class HashMapTypeConverter {
    @TypeConverter
    fun fromInfoHashMap(infoHashMap: HashMap<String, Message>?): String {
        if (infoHashMap == null) {
            return ""
        }
        val gson = Gson()
        return gson.toJson(infoHashMap)
    }

    @TypeConverter
    fun toInfoHashMap(infoHashMapString: String?): HashMap<String, Message>? {
        if (infoHashMapString == null || infoHashMapString == "") {
            return null
        }
        val gson = Gson()
        val type = object : TypeToken<HashMap<String, Message>>() {}.type
        return gson.fromJson(infoHashMapString, type)
    }
}