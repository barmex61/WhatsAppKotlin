package com.fatih.whatsappclonekotlin.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fatih.whatsappclonekotlin.R
import com.fatih.whatsappclonekotlin.util.Instance.currentUser

@BindingAdapter("android:downloadUrl")
fun ImageView.setImageUrl(url:String?){
    Glide.with(context).setDefaultRequestOptions(RequestOptions().error(R.drawable.whatsapp_logo)
        .centerCrop()).load(url).into(this)
}

@BindingAdapter("android:customText")
fun TextView.setCustomText(hashMap: HashMap<String,String>?)
{
    val uid= currentUser.value?.uid
    val text=hashMap?.let {
        it[uid]
    }
    this.text= text?:"Selam fatih ne haber"
}


