package com.fatih.whatsappclonekotlin.repository

import com.fatih.whatsappclonekotlin.model.Message
import com.fatih.whatsappclonekotlin.util.Instance.currentUser
import com.fatih.whatsappclonekotlin.util.Resource
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class MessageRepository @Inject constructor(private val firestore: FirebaseFirestore) :MessageRepositoryInterface {

    override suspend fun sendMessage(message: Message):Resource<Boolean> {
        val documentId = if(message.senderUid > message.recieverUid){
            message.senderUid+message.recieverUid
        }else{
            message.recieverUid+message.senderUid
        }
        try {
            val result=firestore.collection("Chat").document(documentId).collection(documentId).add(message)
            result.await()
            if(result.isSuccessful){
                val newInfo = hashMapOf(message.recieverUid to message)
                val update = mapOf("infoHashMap" to FieldValue.arrayUnion(newInfo))
                val result2=firestore.collection("User").document(message.senderUid).update(update)
                result2.await()
                val newInfo2 = hashMapOf(message.senderUid to message)
                val update2 = mapOf("infoHashMap" to FieldValue.arrayUnion(newInfo2))
                val result3=firestore.collection("User").document(message.recieverUid).update(update2)
                result3.await()
                return if(result2.isSuccessful&&result3.isSuccessful){
                    Resource.success(true)
                }else{
                    Resource.error(false,"Error")
                }
            }else{
             return Resource.error(false,"Error")
            }
        }catch (e:Exception){
            return Resource.error(false,e.message)
        }

    }

    override suspend fun getMessages(recieverUid:String,messages: (List<Message>) -> Unit) {
        val documentId = if(currentUser.value!!.uid > recieverUid){
            currentUser.value!!.uid+recieverUid
        }else{
            recieverUid+currentUser.value!!.uid
        }
        try {
            firestore.collection("Chat").document(documentId).collection(documentId).orderBy("date",Query.Direction.ASCENDING).addSnapshotListener { value, error ->
                error?.let {
                    messages(listOf())
                }
                value?.let {
                    val messageList= mutableListOf<Message>()
                    for(data in it.documents){
                        data.toObject(Message::class.java)?.let { message->
                            messageList.add(message)
                        }
                    }
                    messages(messageList)
                }
            }
        }catch (e:Exception){
            messages(listOf())
        }
    }
}