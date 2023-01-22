package com.fatih.whatsappclonekotlin.repository

import android.net.Uri
import com.fatih.whatsappclonekotlin.util.Resource

interface ProfileRepositoryInterface {

    suspend fun setProfileImage(url:Uri):Resource<String>
    suspend fun setProfileInformations(name:String,status:String,phone:String):Resource<Boolean>
}