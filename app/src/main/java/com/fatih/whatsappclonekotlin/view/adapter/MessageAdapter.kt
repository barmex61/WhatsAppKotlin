package com.fatih.whatsappclonekotlin.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fatih.whatsappclonekotlin.R
import com.fatih.whatsappclonekotlin.databinding.MessageFragmentRowBinding
import com.fatih.whatsappclonekotlin.model.Message
import com.fatih.whatsappclonekotlin.model.User
import com.fatih.whatsappclonekotlin.util.Instance.currentUser

class MessageAdapter: RecyclerView.Adapter<MessageAdapter.MessageViewHolder>() {

    private val diffUtil=object: DiffUtil.ItemCallback<Message>(){
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem==newItem
        }

    }

    private val asyncListDiffer= AsyncListDiffer(this,diffUtil)

    var messageList:List<Message>
        get() = asyncListDiffer.currentList
        set(value) = asyncListDiffer.submitList(value)

    class MessageViewHolder(val binding:MessageFragmentRowBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding=DataBindingUtil.inflate<MessageFragmentRowBinding>(LayoutInflater.from(parent.context),
            R.layout.message_fragment_row,parent,false)

        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.binding.message=messageList[position]
        if (messageList[position].senderUid==currentUser.value!!.uid){
            holder.binding.messageFragmentRecieverRow.visibility=View.GONE
            holder.binding.messageFragmentSenderRow.visibility=View.VISIBLE
        }else{
            holder.binding.messageFragmentSenderRow.visibility=View.GONE
            holder.binding.messageFragmentRecieverRow.visibility=View.VISIBLE
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }
}