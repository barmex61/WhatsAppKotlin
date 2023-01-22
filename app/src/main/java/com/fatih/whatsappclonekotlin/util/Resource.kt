package com.fatih.whatsappclonekotlin.util

class Resource <T>(val data:T?,val message:String?,val status:Status) {

    companion object{

        fun <T> success(data:T):Resource<T>{
            return Resource(data,null,Status.SUCCESS)
        }
        fun <T> loading():Resource<T>{
            return  Resource(status = Status.LOADING, data = null, message = null)
        }
        fun <T> error(data:T?,message: String?):Resource<T>{
            return Resource(data = data, message = message,Status.ERROR)
        }
    }
}