package com.fatih.whatsappclonekotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatih.whatsappclonekotlin.model.Message
import com.fatih.whatsappclonekotlin.repository.MessageRepositoryInterface
import com.fatih.whatsappclonekotlin.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageFragmentViewModel @Inject constructor(private val messageRepo:MessageRepositoryInterface) :ViewModel() {

    private val _messageResul=MutableLiveData<Resource<Boolean>>()
    val messageResult:LiveData<Resource<Boolean>>
    get() = _messageResul

    private val _messages=MutableLiveData<List<Message>>()
    val messages:LiveData<List<Message>>
    get() = _messages

    fun sendMessage(message: Message)=viewModelScope.launch {
        _messageResul.value= Resource.loading()
        try {
           _messageResul.value= messageRepo.sendMessage(message)
        }catch (e:Exception){
            _messageResul.value= Resource.error(false,e.message)
        }
    }

    fun getMessages(recieverUid:String)=viewModelScope.launch {
        messageRepo.getMessages(recieverUid){
            _messages.value=it
        }
    }

}