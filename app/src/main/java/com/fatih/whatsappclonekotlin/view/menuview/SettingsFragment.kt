package com.fatih.whatsappclonekotlin.view.menuview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.fatih.whatsappclonekotlin.R
import com.fatih.whatsappclonekotlin.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private lateinit var binding:FragmentSettingsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_settings,container,false)
        binding.fragmentSettingsBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.linearLayoutSettings.setOnClickListener {
            val extras= FragmentNavigatorExtras(binding.profileImage to "image_big")
            findNavController().navigate(R.id.action_settingsFragment_to_profileFragment,null,null,extras)
        }
        return binding.root
    }

}