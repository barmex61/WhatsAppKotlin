package com.fatih.whatsappclonekotlin.view.logview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fatih.whatsappclonekotlin.R
import com.fatih.whatsappclonekotlin.databinding.FragmentSignInBinding
import com.fatih.whatsappclonekotlin.util.Instance.currentUser
import com.fatih.whatsappclonekotlin.util.Status
import com.fatih.whatsappclonekotlin.viewmodel.SignInFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private lateinit var binding:FragmentSignInBinding
    private val viewModel:SignInFragmentViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater, R.layout.fragment_sign_in,container,false)
        binding.signUpText.setOnClickListener {
            findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToSignUpFragment())
        }
        binding.signInButton.setOnClickListener {
            signIn()
        }
        observeLiveData()
        return binding.root
    }

    private fun observeLiveData(){
        viewModel.status.observe(viewLifecycleOwner){
            when(it.status){
                Status.ERROR->{
                    binding.signInProgressBar.visibility=View.GONE
                    Toast.makeText(requireContext(),it.data,Toast.LENGTH_LONG).show()
                }
                Status.SUCCESS->{
                    binding.signInProgressBar.visibility=View.GONE
                    findNavController().popBackStack(R.id.signInFragment, true)
                    findNavController().navigate(R.id.tabLayoutFragment)}
                Status.LOADING->{
                    binding.signInProgressBar.visibility=View.VISIBLE
                }
            }
        }
        currentUser.observe(viewLifecycleOwner){
            it?.let {
                findNavController().popBackStack(R.id.signInFragment, true)
                findNavController().navigate(R.id.tabLayoutFragment)
            }
        }

    }

    private fun signIn(){
        val email=binding.emailText.text.toString()
        val password=binding.passwordText.text.toString()
        viewModel.signInWithEmailAndPassword(email, password)
    }
}