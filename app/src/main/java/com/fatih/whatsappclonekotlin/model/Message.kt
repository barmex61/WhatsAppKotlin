package com.fatih.whatsappclonekotlin.model


data class Message(val senderUid:String="",val recieverUid:String="",val message:String="",val messageUid:String="",val isSeen:Boolean?=null,val date:Long=0L)
