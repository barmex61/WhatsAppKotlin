package com.fatih.whatsappclonekotlin.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.fatih.whatsappclonekotlin.model.User


@Dao
interface UserDao {

    @Insert
    suspend fun insertUserInfo(user: User)
    @Query("UPDATE User SET photo = :imageUrlString WHERE uid =:idInput")
    suspend fun updateUserImage(idInput: String,imageUrlString: String)
    @Query("UPDATE User SET userName = :userNameInput, status = :statusInput, phone = :phoneInput WHERE uid =:idInput")
    suspend fun updateUserInfo(userNameInput:String,statusInput:String,phoneInput:String,idInput:String)
    @Query("SELECT * FROM User WHERE uid =:idInput")
    fun getUserInfo(idInput:String):LiveData<User>
    @Query("SELECT photo FROM User WHERE uid =:idInput")
    fun getUserImageUrl(idInput: String):LiveData<String>
}