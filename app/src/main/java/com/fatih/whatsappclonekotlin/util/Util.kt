package com.fatih.whatsappclonekotlin.util

import android.content.Context
import android.content.res.ColorStateList
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fatih.whatsappclonekotlin.R
import com.fatih.whatsappclonekotlin.model.Message
import com.fatih.whatsappclonekotlin.util.Instance.currentUser
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

@BindingAdapter("android:downloadUrl")
fun ImageView.setImageUrl(url:String?){
    Glide.with(context).setDefaultRequestOptions(RequestOptions().error(R.drawable.whatsapp_logo)
        .centerCrop()).load(url).into(this)
}

@BindingAdapter("android:lastMessageText")
fun TextView.setLastMessageText(hashMap: HashMap<String,Message>?)
{
    val uid= currentUser.value?.uid
    val text=hashMap?.let {
        it[uid]?.message
    }
    this.text= text?:""
}
@BindingAdapter("android:dateText")
fun TextView.setDateText(hashMap: HashMap<String, Message>?){
    var myDate=""
    val uid= currentUser.value?.uid
    hashMap?.let {
        it[uid]?.date
    }?.let {
        val date = Date(it)
        val yesterday = Calendar.getInstance().apply { add(Calendar.DATE, -1) }.time
        val beforeYesterday = Calendar.getInstance().apply { add(Calendar.DATE, -2) }.time
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        myDate=when {
            date.before(yesterday) && date.after(beforeYesterday) -> "yesterday"
            date.after(yesterday)  -> format.format(date)
            else -> SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
        }
    }
    this.text=myDate

}

@BindingAdapter("android:setLastSeenTint")
fun ImageView.setLastSeenTint(hashMap: HashMap<String, Message>?){
    val uid= currentUser.value?.uid
    if(hashMap!=null){
        if(hashMap[uid]!=null){
            this.visibility=View.VISIBLE
            hashMap[uid]!!.isSeen?.let {
                if (it){
                    this.imageTintList= ColorStateList.valueOf(resources.getColor(R.color.tick))
                }else{
                    this.imageTintList= ColorStateList.valueOf(resources.getColor(R.color.tickUnseen))
                }
            }
        }else{
            this.visibility=View.INVISIBLE
        }
    }else{
        this.visibility=View.INVISIBLE
    }



}

@BindingAdapter("android:dateText2")
fun TextView.setDateText2(messageDate:Long?){
    var myDate=""
    messageDate?.let {
        val date = Date(it)
        val yesterday = Calendar.getInstance().apply { add(Calendar.DATE, -1) }.time
        val beforeYesterday = Calendar.getInstance().apply { add(Calendar.DATE, -2) }.time
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        myDate=when {
            date.before(yesterday) && date.after(beforeYesterday) -> "yesterday"
            date.after(yesterday)  -> format.format(date)
            else -> SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(date)
        }

    }
    this.text=myDate

}

