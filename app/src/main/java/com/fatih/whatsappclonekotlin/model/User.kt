package com.fatih.whatsappclonekotlin.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class User(val email:String="",
                val password:String="",
                val photo:String?="",
                val status:String?="",
                @PrimaryKey(autoGenerate = false)
                val uid:String="",
                val userName:String="",
                val phone:String="",
                val infoHashMap:HashMap<String,String>?=null
)
