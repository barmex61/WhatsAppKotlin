package com.fatih.whatsappclonekotlin.repository

import android.net.Uri
import androidx.lifecycle.LiveData
import com.fatih.whatsappclonekotlin.model.User
import com.fatih.whatsappclonekotlin.util.Resource

interface ProfileRepositoryInterface {

    suspend fun setProfileImage(url:Uri):Resource<String>
    suspend fun setProfileInformations(name:String,status:String,phone:String):Resource<Boolean>
    fun getUserInfo():LiveData<User>
    fun setImageUrl():LiveData<String>
}