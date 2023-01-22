package com.fatih.whatsappclonekotlin.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatih.whatsappclonekotlin.repository.AuthenticationRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TabLayoutViewModel @Inject constructor(private val authRepository:AuthenticationRepositoryInterface):ViewModel() {



    fun logOut()=viewModelScope.launch{
        authRepository.logOut()
    }
}