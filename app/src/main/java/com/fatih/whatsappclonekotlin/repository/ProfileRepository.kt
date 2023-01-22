package com.fatih.whatsappclonekotlin.repository

import android.net.Uri
import com.fatih.whatsappclonekotlin.util.Instance.currentUser
import com.fatih.whatsappclonekotlin.util.Resource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.StorageReference
import kotlinx.coroutines.tasks.await
import java.util.UUID
import javax.inject.Inject

class ProfileRepository @Inject constructor(private val fireStore:FirebaseFirestore,private val storageReference:StorageReference):ProfileRepositoryInterface {

    override suspend fun setProfileImage(url: Uri): Resource<String> {
        return try {
            val uuid=UUID.randomUUID()
            val task=storageReference.child("images").child("/$uuid").putFile(url)
            val result=task.await()
            if(task.isSuccessful){
              val uri=  storageReference.child("images").child("/$uuid").downloadUrl.await()
              uri?.let {
                  val resultFirestore=fireStore.collection("User").document(currentUser.value!!.uid).update("photo",it.toString())
                  resultFirestore.await()
                  if(resultFirestore.isSuccessful){
                      Resource.success(it.toString())
                  }else{
                      Resource.error(resultFirestore.exception?.message,null)
                  }
              }?:Resource.error("Uri is null",null)
            }else{
                Resource.error(result.error?.message,null)
            }

        }catch (e:Exception){
            Resource.error(e.message,null)
        }
    }

    override suspend fun setProfileInformations(name:String,status: String,phone:String): Resource<Boolean> {
        try {
            val phoneFilter=phone.filter {
                it!=' '
            }
            for(indice in phoneFilter.indices){
                Integer.valueOf(phoneFilter[indice].toString())
            }
        }catch (e:Exception){
            return Resource.error(false,e.message)
        }
        val dataHashMap= hashMapOf<String,String>()
        dataHashMap["userName"]=name
        dataHashMap["status"]=status
        dataHashMap["phone"]=phone
       return try{
           val result=fireStore.collection("User").document(currentUser.value!!.uid).update(dataHashMap.toMap())
           result.await()
           if(result.isSuccessful){
               Resource.success(true)
           }else{
               Resource.error(false,null)
           }
       }catch (e:Exception){
           Resource.error(null,e.message)
       }

    }
}