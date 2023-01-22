package com.fatih.whatsappclonekotlin.viewmodel

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import androidx.lifecycle.*
import com.fatih.whatsappclonekotlin.repository.ProfileRepositoryInterface
import com.fatih.whatsappclonekotlin.room.UserDao
import com.fatih.whatsappclonekotlin.util.Instance.currentUser
import com.fatih.whatsappclonekotlin.util.Resource
import com.fatih.whatsappclonekotlin.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileFragmentViewModel @Inject constructor(private val profileRepository: ProfileRepositoryInterface,
                                                   private val userDao: UserDao,
                                                   application: Application): AndroidViewModel(application) {

    private var sharedPreference:SharedPreferences=application.getSharedPreferences("com.fatih.whatsappclonekotlin", Context.MODE_PRIVATE)



    private val _result=MutableLiveData<Resource<String>>()
    val result: LiveData<Resource<String>>
    get() = _result

    private val _information=MutableLiveData<Resource<Boolean>>()
    val information:LiveData<Resource<Boolean>>
    get() = _information

    fun setProfileImage(url: Uri)=viewModelScope.launch {
        _result.value= Resource.loading()
        try {
            if(sharedPreference.getLong("timeImage",0L)!=System.currentTimeMillis()){
                _result.value=Resource.loading()
                _result.value=profileRepository.setProfileImage(url)
                if(_result.value!!.status==Status.SUCCESS){
                    sharedPreference.edit().putLong("timeImage",System.currentTimeMillis()).apply()
                    userDao.updateUserInfo(currentUser.value!!.uid,url.toString())
                    return@launch
                }
            }
            _result.value=Resource.success(userDao.getUserInfo(currentUser.value!!.uid).photo!!)
        }catch (e:Exception){
            _result.value= Resource.error(e.message,e.message)
        }


    }

    fun setProfileInformations(name:String,status:String,phone:String)=viewModelScope.launch {
        if(sharedPreference.getLong("timeInfo",0L)!=System.currentTimeMillis()){
            _information.value= Resource.loading()
            _information.value=profileRepository.setProfileInformations(name,status,phone)
            sharedPreference.edit().putLong("timeInfo",System.currentTimeMillis()).apply()
            return@launch
        }

    }

}