package com.fatih.whatsappclonekotlin.view.feedview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.fatih.whatsappclonekotlin.R
import com.fatih.whatsappclonekotlin.databinding.FragmentChatBinding
import com.fatih.whatsappclonekotlin.repository.ChatRepositoryInterface
import com.fatih.whatsappclonekotlin.util.Status
import com.fatih.whatsappclonekotlin.view.adapter.ChatFragmentAdapter
import com.fatih.whatsappclonekotlin.viewmodel.ChatFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ChatFragment @Inject constructor(): Fragment(R.layout.fragment_chat) {

    private lateinit var adapter: ChatFragmentAdapter
    private lateinit var binding:FragmentChatBinding
    private var viewModel:ChatFragmentViewModel?=null
    @Inject
    lateinit var repository: ChatRepositoryInterface


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_chat,container,false)
        viewModel= activity?.run {
            ViewModelProvider(this)[ChatFragmentViewModel::class.java]
        }
        adapter= ChatFragmentAdapter()
        adapter.userList=viewModel?.chatUser?.value?.data?: listOf()
        binding.recyclerView.layoutManager=LinearLayoutManager(requireContext())
        binding.recyclerView.adapter=adapter
        viewModel?.getChatUsers()
        viewModel?.let {
            observeLiveData(it)
        }
        adapter.setMyListener {
            findNavController().navigate(TabLayoutFragmentDirections.actionTabLayoutFragmentToMessageFragment(it.uid))
        }
        return binding.root
    }


    private fun observeLiveData(viewModel: ChatFragmentViewModel){

        viewModel.chatUser.observe(viewLifecycleOwner){
            it?.let {
                when(it.status){
                    Status.LOADING->{}
                    Status.SUCCESS->{
                        adapter.userList=it.data?: listOf()
                    }
                    Status.ERROR->{
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }

}