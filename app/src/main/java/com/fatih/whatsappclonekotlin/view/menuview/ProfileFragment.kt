package com.fatih.whatsappclonekotlin.view.menuview

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fatih.whatsappclonekotlin.R
import com.fatih.whatsappclonekotlin.databinding.FragmentProfileBinding
import com.fatih.whatsappclonekotlin.util.Status
import com.fatih.whatsappclonekotlin.util.setImageUrl
import com.fatih.whatsappclonekotlin.viewmodel.ProfileFragmentViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment @Inject constructor(): Fragment(R.layout.fragment_profile) {


    private lateinit var binding:FragmentProfileBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private val viewModel:ProfileFragmentViewModel by viewModels()
    private lateinit var selectedUri:Uri
    private lateinit var bottomSheetDialog: BottomSheetDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation=TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition=animation
        sharedElementReturnTransition=animation
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false)
        val animation=AnimationUtils.loadAnimation(requireContext(),R.anim.alpha)
        binding.profileFabButton.startAnimation(animation)
        binding.fragmentProfileBackArrow.setOnClickListener {
            findNavController().navigateUp()
        }
        registerLauncher()
        binding.profileFabButton.setOnClickListener {
            askPermission(it)
        }
        binding.constraint1.setOnClickListener {
            openBottomSheetDialog()
        }
        binding.constraint2.setOnClickListener {
            openBottomSheetDialog()
        }
        binding.constraint3.setOnClickListener {
            openBottomSheetDialog()
        }
        observeLiveData()

        return binding.root
    }

    private fun openBottomSheetDialog(){
        bottomSheetDialog=BottomSheetDialog(requireContext(),R.style.BottomSheetDialogTheme)
        bottomSheetDialog.setContentView(R.layout.profile_bottom_sheet)
        bottomSheetDialog.findViewById<TextView>(R.id.bottomSheetCancel)?.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.findViewById<TextView>(R.id.bottomSheetApply)?.setOnClickListener {
            val name=bottomSheetDialog.findViewById<EditText>(R.id.bottomSheetNameText)?.text.toString()
            val status=bottomSheetDialog.findViewById<EditText>(R.id.bottomSheetStatusText)?.text.toString()
            val phone=bottomSheetDialog.findViewById<EditText>(R.id.bottomSheetPhoneText)?.text.toString()
            viewModel.setProfileInformations(name,status, phone)
        }
        bottomSheetDialog.findViewById<EditText>(R.id.bottomSheetNameText)?.requestFocus()
        bottomSheetDialog.show()
    }

    private fun observeLiveData(){
        viewModel.result.observe(viewLifecycleOwner){
            when(it.status){
                Status.SUCCESS->{
                    binding.profileFragmentProgressBar.visibility=View.GONE
                    findNavController().navigateUp()
                }
                Status.ERROR->{
                    binding.profileFragmentProgressBar.visibility=View.GONE
                    Toast.makeText(requireContext(),it.data,Toast.LENGTH_LONG).show()
                }
                Status.LOADING->{
                    binding.profileFragmentProgressBar.visibility=View.VISIBLE
                }
            }
        }
        viewModel.information.observe(viewLifecycleOwner){
            when(it.status){
                Status.LOADING->{
                    binding.profileFragmentProgressBar.visibility=View.VISIBLE
                }
                Status.SUCCESS->{
                    bottomSheetDialog.dismiss()
                    binding.profileFragmentProgressBar.visibility=View.GONE
                }
                Status.ERROR->{
                    binding.profileFragmentProgressBar.visibility=View.GONE
                    Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                }

            }
        }
    }


    private fun askPermission(view: View) {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                Snackbar.make(view, "Need to give permission", Snackbar.LENGTH_INDEFINITE).setAction("Yes") {
                        //request permission
                        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                    }.show()
            } else {
                //request permission
                permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        } else {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            resultLauncher.launch(intent)
            //go to gallery
        }
    }

    private fun registerLauncher() {

        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    resultLauncher.launch(intent)
                } else {
                    Toast.makeText(requireContext(), "Give permission", Toast.LENGTH_LONG).show()
                }
            }
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

                result.data?.let {
                    it.data?.let { uri ->
                        selectedUri=uri
                        binding.profileImage.setImageUrl(uri.toString())
                        viewModel.setProfileImage(selectedUri)
                    }
                }
            }

    }

}