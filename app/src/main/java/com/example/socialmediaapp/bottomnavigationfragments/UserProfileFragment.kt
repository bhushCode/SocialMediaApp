package com.example.socialmediaapp.bottomnavigationfragments

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.socialmediaapp.R
import com.example.socialmediaapp.databinding.FragmentUserProfileBinding
import com.example.socialmediaapp.model.UserModelClass
import com.example.socialmediaapp.repository.PostRepository
import com.example.socialmediaapp.viewmodel.PostViewModel
import com.example.socialmediaapp.viewmodelfactory.PostVIewModelFactory
import com.google.firebase.auth.FirebaseAuth

class UserProfileFragment : Fragment() {
private lateinit var binding:FragmentUserProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_profile, container, false)

        fetchUserData()



        return binding.root
    }

    private fun fetchUserData() {
        val repository = PostRepository()
        val viewmodel = ViewModelProvider(this,PostVIewModelFactory(repository)).get(PostViewModel::class.java)

        viewmodel.getUsers().observe(viewLifecycleOwner, Observer {
            Log.d("fanta",it.toString())
            it.forEach{ users->

                if(users.userId.equals(FirebaseAuth.getInstance().currentUser!!.uid)){

                    binding.userProfileTxtUsername.text=users.name
                    binding.userProfileTxtFollowers.text="${users.followers} followers"
                    binding.userProfileTxtFollowing.text="${users.following} following"
                    Log.d("fantastic",users.profileImage)
                    Glide.with(requireContext()).load(users.profileImage).into(binding.userProfileFragmentProfileImage)

                }
            }


        })
    }


}