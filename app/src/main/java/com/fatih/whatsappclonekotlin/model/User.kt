package com.fatih.whatsappclonekotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.fatih.whatsappclonekotlin.room.HashMapTypeConverter


@Entity
@TypeConverters(HashMapTypeConverter::class)
data class User(val email:String="",
                val password:String="",
                val photo:String?="",
                val status:String?="",
                @PrimaryKey(autoGenerate = false)
                val uid:String="",
                val userName:String="",
                val phone:String="",
                val infoHashMap:HashMap<String,Message>?=null
)
