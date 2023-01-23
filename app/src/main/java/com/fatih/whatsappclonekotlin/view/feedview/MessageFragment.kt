package com.fatih.whatsappclonekotlin.view.feedview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatih.whatsappclonekotlin.R
import com.fatih.whatsappclonekotlin.databinding.FragmentMessageBinding
import com.fatih.whatsappclonekotlin.model.Message
import com.fatih.whatsappclonekotlin.util.Instance.currentUser
import com.fatih.whatsappclonekotlin.util.Status
import com.fatih.whatsappclonekotlin.view.adapter.MessageAdapter
import com.fatih.whatsappclonekotlin.viewmodel.MessageFragmentViewModel
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MessageFragment @Inject constructor() : Fragment(R.layout.fragment_message) {

    private var recieverUid:String=""
    private lateinit var binding:FragmentMessageBinding
    private lateinit var viewModel:MessageFragmentViewModel
    private lateinit var adapter:MessageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            recieverUid=MessageFragmentArgs.fromBundle(it).uid
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_message,container,false)
        viewModel=ViewModelProvider(this)[MessageFragmentViewModel::class.java]
        adapter=MessageAdapter()
        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerView.adapter=adapter
        binding.send.setOnClickListener {
            sendMessage()
        }
        viewModel.getMessages(recieverUid)
        observeLiveData()
        return binding.root
    }

    private fun observeLiveData(){

        viewModel.messageResult.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS->{
                    viewModel.getMessages(recieverUid)
                    binding.recyclerView.smoothScrollToPosition(adapter.itemCount-1)
                }
                Status.ERROR->{
                    println("error")
                }
                Status.LOADING->{
                    println("loading")
                }
            }
        }
        viewModel.messages.observe(viewLifecycleOwner){
                adapter.messageList=it
        }
    }

    private fun sendMessage(){
        if(binding.messageText.text.toString().isNotEmpty()){
            val message=binding.messageText.text.toString()
            val senderUid=currentUser.value!!.uid
            val longDate = Timestamp.now().toDate().time
            val messageUid = UUID.randomUUID().toString()
            val messageSent=Message(senderUid,recieverUid,message,messageUid,false,longDate)
            viewModel.sendMessage(messageSent)
            binding.messageText.setText("")
        }
    }

}