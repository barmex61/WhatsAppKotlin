package com.fatih.whatsappclonekotlin.util


import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

object Instance {

    private val firebaseAuth=FirebaseAuth.getInstance()
    var currentUser: MutableLiveData<FirebaseUser?> = MutableLiveData<FirebaseUser?>(firebaseAuth.currentUser)
    fun setCurrentUser(){
        currentUser.value=firebaseAuth.currentUser
    }

}