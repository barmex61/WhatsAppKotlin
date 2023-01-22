package com.fatih.whatsappclonekotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatih.whatsappclonekotlin.model.User
import com.fatih.whatsappclonekotlin.repository.ChatRepositoryInterface
import com.fatih.whatsappclonekotlin.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatFragmentViewModel @Inject constructor(private val chatRepository: ChatRepositoryInterface) :ViewModel(){

    private var _chatUsers=MutableLiveData<Resource<List<User>>>()
    val chatUser:LiveData<Resource<List<User>>>
    get() = _chatUsers

    init {
        viewModelScope.launch {
            _chatUsers= chatRepository.getUsersFromFirebase() as MutableLiveData<Resource<List<User>>>
        }
    }
    fun getChatUsers()=viewModelScope.launch {
        _chatUsers.value= Resource.loading()
        _chatUsers= chatRepository.getUsersFromFirebase() as MutableLiveData<Resource<List<User>>>
    }




}