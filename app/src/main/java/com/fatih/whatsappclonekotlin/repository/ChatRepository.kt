package com.fatih.whatsappclonekotlin.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.fatih.whatsappclonekotlin.model.User
import com.fatih.whatsappclonekotlin.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ChatRepository @Inject constructor(private val firestore: FirebaseFirestore ):ChatRepositoryInterface {

    override suspend fun getUsersFromFirebase(): LiveData<Resource<List<User>>> = liveData(Dispatchers.IO) {
       try {
           val task=firestore.collection("User").get().await()
           val users = mutableListOf<User>()
           for(document in task.documents){
               val user = document.toObject(User::class.java)
               user?.let { it->
                   users.add(it)
               }
           }
           emit(Resource.success(users))
        }catch (e:Exception){
            emit(Resource.error(null,e.message))
        }
    }
}