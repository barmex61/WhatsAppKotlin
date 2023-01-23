package com.fatih.whatsappclonekotlin.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.fatih.whatsappclonekotlin.model.User

@Database(entities = [User::class], version = 5)
abstract class UserDb:RoomDatabase() {
    abstract fun userDao():UserDao
}