package com.fatih.whatsappclonekotlin.view.logview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.fatih.whatsappclonekotlin.R
import com.fatih.whatsappclonekotlin.util.Instance.currentUser
import com.fatih.whatsappclonekotlin.util.Status
import com.fatih.whatsappclonekotlin.viewmodel.ChatFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CustomSplashScreenFragment @Inject constructor() : Fragment(R.layout.fragment_splash_screen) {

    private var viewModel:ChatFragmentViewModel?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel= activity?.run {
            ViewModelProviders.of(this)[ChatFragmentViewModel::class.java]
        }
        currentUser.value?.let {
            viewModel?.getChatUsers()
            viewModel?.chatUser?.observe(viewLifecycleOwner){
                when(it.status){
                    Status.SUCCESS->{
                        findNavController().navigate(R.id.action_customSplashScreenFragment_to_signInFragment)
                    }
                    Status.LOADING->{}
                    Status.ERROR->{Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()}
                }
            }
        }?:findNavController().navigate(R.id.action_customSplashScreenFragment_to_signInFragment)

        return super.onCreateView(inflater, container, savedInstanceState)

    }
}