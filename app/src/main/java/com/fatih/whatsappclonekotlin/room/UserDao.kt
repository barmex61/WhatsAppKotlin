package com.fatih.whatsappclonekotlin.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fatih.whatsappclonekotlin.model.User

@Dao
interface UserDao {

    @Insert
    suspend fun insertUserInfo(user: User)
    @Query("UPDATE User SET photo = :imageUrlString WHERE uid =:idInput")
    suspend fun updateUserInfo(idInput: String,imageUrlString: String)
    @Query("SELECT * FROM User WHERE uid =:idInput")
    suspend fun getUserInfo(idInput:String):User
}