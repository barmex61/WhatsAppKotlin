package com.fatih.whatsappclonekotlin.repository

import com.fatih.whatsappclonekotlin.model.Message
import com.fatih.whatsappclonekotlin.util.Resource

interface MessageRepositoryInterface {

    suspend fun sendMessage(message:Message):Resource<Boolean>
    suspend fun getMessages(recieverUid:String,messages:(List<Message>)->Unit)
}