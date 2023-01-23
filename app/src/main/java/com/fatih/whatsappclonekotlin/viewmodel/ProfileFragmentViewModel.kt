package com.fatih.whatsappclonekotlin.viewmodel

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.fatih.whatsappclonekotlin.repository.ProfileRepositoryInterface
import com.fatih.whatsappclonekotlin.room.UserDao
import com.fatih.whatsappclonekotlin.util.Instance.currentUser
import com.fatih.whatsappclonekotlin.util.Resource
import com.fatih.whatsappclonekotlin.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(private val profileRepository: ProfileRepositoryInterface,
                                                   private val userDao: UserDao,
                                                   application: Application): AndroidViewModel(application) {

    private val _result=MutableLiveData<Resource<String>>()
    val result: LiveData<Resource<String>>
    get() = _result

    private val _information=MutableLiveData<Resource<Boolean>>()
    val information:LiveData<Resource<Boolean>>
    get() = _information

    val userInfo=profileRepository.getUserInfo()

    fun setProfileImage(url: Uri)=viewModelScope.launch {
        _result.value= Resource.loading()
        try {
            _result.value=profileRepository.setProfileImage(url)
            if(_result.value!!.status==Status.SUCCESS){
                userDao.updateUserImage(currentUser.value!!.uid,_result.value!!.data!!)
            }else{
                _result.value=Resource.error(null,null)
            }
        }catch (e:Exception){
            _result.value= Resource.error(e.message,e.message)
        }
    }

    fun setProfileInformations(name:String,status:String,phone:String)=viewModelScope.launch {
       try {
           _information.value= Resource.loading()
           _information.value=profileRepository.setProfileInformations(name,status,phone)
           if(_information.value!!.status==Status.SUCCESS){
               viewModelScope.launch(Dispatchers.IO) {
                   userDao.updateUserInfo(name,status,phone, currentUser.value!!.uid)
                   println(userDao.getUserInfo(currentUser.value!!.uid).value?.userName)
               }
           }else{
               _information.value=Resource.error(null,null)
           }
       }catch (e:Exception){
            _information.value= Resource.error(null,e.message)
       }
    }

}