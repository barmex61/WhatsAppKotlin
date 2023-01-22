package com.fatih.whatsappclonekotlin.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fatih.whatsappclonekotlin.R
import com.fatih.whatsappclonekotlin.databinding.ChatFragmentRecyclerRowBinding
import com.fatih.whatsappclonekotlin.model.User

class ChatFragmentAdapter: RecyclerView.Adapter<ChatFragmentAdapter.ChatFragmentViewHolder>() {

    private val diffUtil=object:DiffUtil.ItemCallback<User>(){
        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem==newItem
        }

        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem==newItem
        }
    }

    private val asyncListDiffer=AsyncListDiffer(this,diffUtil)

    var userList:List<User>
        get() = asyncListDiffer.currentList
        set(value) = asyncListDiffer.submitList(value)

    class ChatFragmentViewHolder(val binding:ChatFragmentRecyclerRowBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatFragmentViewHolder {
        val recyclerRow=DataBindingUtil.inflate<ChatFragmentRecyclerRowBinding>(LayoutInflater.from(parent.context)
        , R.layout.chat_fragment_recycler_row,parent,false)
        return ChatFragmentViewHolder(recyclerRow)
    }

    override fun onBindViewHolder(holder: ChatFragmentViewHolder, position: Int) {
        holder.binding.user=userList[position]
    }

    override fun getItemCount(): Int {
       return userList.size
    }
}