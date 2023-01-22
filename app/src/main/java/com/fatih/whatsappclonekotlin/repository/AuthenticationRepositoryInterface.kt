package com.fatih.whatsappclonekotlin.repository

import com.fatih.whatsappclonekotlin.util.Resource


interface AuthenticationRepositoryInterface {

    suspend fun signUpWithEmailAndPassword(userName:String,email:String,password:String,lambda:(Resource<String>)->Unit)
    suspend fun signInWithEmailAndPassword(email: String,password: String,lambda: (Resource<String>) -> Unit)
    suspend fun logOut()
}