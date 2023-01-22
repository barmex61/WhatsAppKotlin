package com.fatih.whatsappclonekotlin.view.feedview

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.fatih.whatsappclonekotlin.R
import com.fatih.whatsappclonekotlin.databinding.FragmentTabLayoutBinding
import com.fatih.whatsappclonekotlin.view.adapter.TabLayoutFragmentAdapter
import com.fatih.whatsappclonekotlin.viewmodel.TabLayoutViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TabLayoutFragment @Inject constructor(): Fragment(){

    private lateinit var binding:FragmentTabLayoutBinding
    private lateinit var viewPager2: ViewPager2
    private lateinit var tabLayout: TabLayout
    private lateinit var toolbar: Toolbar
    private val viewModel:TabLayoutViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_tab_layout,container,false)
        toolbar=binding.tabLayoutToolbar
        toolbar.inflateMenu(R.menu.my_menu)
        toolbar.overflowIcon?.setTint(Color.WHITE)
        toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.logOut->{
                    viewModel.logOut().invokeOnCompletion {
                        findNavController().popBackStack(R.id.tabLayoutFragment,false)
                        findNavController().navigate(TabLayoutFragmentDirections.actionTabLayoutFragmentToSignInFragment())
                    }
                }
                R.id.settings->{
                    findNavController().navigate(TabLayoutFragmentDirections.actionTabLayoutFragmentToSettingsFragment())
                }
            }
            true
        }

        val fragmentList= arrayListOf(ChatFragment(),StatusFragment(),CallFragment())
        val adapter= TabLayoutFragmentAdapter(requireActivity().supportFragmentManager,lifecycle,fragmentList)
        viewPager2=binding.viewPager
        tabLayout=binding.tabLayout
        viewPager2.adapter=adapter
        TabLayoutMediator(tabLayout,viewPager2){tab,position->
            when(position){
                1->{tab.text="Durum"}
                2->{tab.text="Aramalar"}
                0->{tab.text="Sohbetler"}
            }
        }.attach()
        return binding.root
    }


}