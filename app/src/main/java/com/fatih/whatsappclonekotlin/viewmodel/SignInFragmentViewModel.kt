package com.fatih.whatsappclonekotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatih.whatsappclonekotlin.repository.AuthenticationRepositoryInterface
import com.fatih.whatsappclonekotlin.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInFragmentViewModel @Inject constructor(private val authRepository:AuthenticationRepositoryInterface): ViewModel() {

    private val _status=MutableLiveData<Resource<String>>()
    val status:LiveData<Resource<String>>
    get() = _status

    fun signInWithEmailAndPassword(email: String,password: String)=viewModelScope.launch {
        _status.value= Resource.loading()
        if(email.isEmpty()&&password.isEmpty()){
            _status.value= Resource.error(data = "Fill the blank lines","Fill the blank lines")
            println(_status.value?.data)
            return@launch
        }
        authRepository.signInWithEmailAndPassword(email, password){
            _status.value=it
        }
    }


}