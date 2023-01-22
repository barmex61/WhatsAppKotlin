package com.fatih.whatsappclonekotlin.repository

import androidx.lifecycle.LiveData
import com.fatih.whatsappclonekotlin.model.User
import com.fatih.whatsappclonekotlin.util.Resource

interface ChatRepositoryInterface {

    suspend fun getUsersFromFirebase(): LiveData<Resource<List<User>>>
}