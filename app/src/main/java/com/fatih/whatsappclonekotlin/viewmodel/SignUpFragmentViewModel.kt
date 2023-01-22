package com.fatih.whatsappclonekotlin.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fatih.whatsappclonekotlin.model.User
import com.fatih.whatsappclonekotlin.repository.AuthenticationRepositoryInterface
import com.fatih.whatsappclonekotlin.room.UserDao
import com.fatih.whatsappclonekotlin.util.Instance.currentUser
import com.fatih.whatsappclonekotlin.util.Resource
import com.fatih.whatsappclonekotlin.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpFragmentViewModel @Inject constructor(private val repository: AuthenticationRepositoryInterface,private val userDao: UserDao):ViewModel() {


    private val _signUpLiveData=MutableLiveData<Resource<String>>()
    val signUpLiveData:LiveData<Resource<String>>
        get() = _signUpLiveData




    fun signUpWithEmailAndPassword(userName:String,email:String,password:String)= viewModelScope.launch{
        _signUpLiveData.value= Resource.loading()
        try {
            repository.signUpWithEmailAndPassword(userName,email, password){ result->

                if(result.status==Status.SUCCESS){
                    viewModelScope.launch {
                        userDao.insertUserInfo(User(uid = currentUser.value!!.uid, userName = userName, email = email, password = password))
                    }.invokeOnCompletion {
                        _signUpLiveData.value=result
                    }
                }else{
                    _signUpLiveData.value= Resource.error(result.data!!,result.message)
                }
            }

        }catch (e:Exception){
            _signUpLiveData.value= Resource.error(e.message,e.message)
        }

    }



}