package com.fatih.whatsappclonekotlin.viewmodel

import androidx.lifecycle.ViewModel
import com.fatih.whatsappclonekotlin.repository.ProfileRepositoryInterface
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsFragmentViewModel @Inject constructor(private val profileRepo: ProfileRepositoryInterface):ViewModel() {

    val imageUrl=profileRepo.setImageUrl()
}