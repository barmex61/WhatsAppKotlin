package com.fatih.whatsappclonekotlin.view.logview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fatih.whatsappclonekotlin.R
import com.fatih.whatsappclonekotlin.databinding.FragmentSignUpBinding
import com.fatih.whatsappclonekotlin.util.Status
import com.fatih.whatsappclonekotlin.viewmodel.SignUpFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private lateinit var binding:FragmentSignUpBinding
    private val viewModel:SignUpFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_sign_up,container,false)
        binding.haveAccountText.setOnClickListener { findNavController().navigateUp() }
        binding.signUpButton.setOnClickListener {
            signUp()
        }
        observeLiveData()
        return binding.root
    }
    private fun observeLiveData(){
        viewModel.signUpLiveData.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS->{
                    binding.progressBarSignUp.visibility=View.GONE
                    findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToTabLayoutFragment2())
                }
                Status.LOADING->{
                    println("loading")
                    binding.progressBarSignUp.visibility=View.VISIBLE
                }
                Status.ERROR->{
                    binding.progressBarSignUp.visibility=View.GONE
                    Toast.makeText(requireContext(),it.data,Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun signUp(){
        val email=binding.emailText.text.toString()
        val userName=binding.userNameText.text.toString()
        val password=binding.passwordText.text.toString()
        viewModel.signUpWithEmailAndPassword(userName, email, password)
    }

}