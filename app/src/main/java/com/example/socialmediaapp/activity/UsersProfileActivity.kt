package com.example.socialmediaapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.socialmediaapp.R
import com.example.socialmediaapp.databinding.ActivityUsersProfileBinding
import com.example.socialmediaapp.repository.PostRepository
import com.example.socialmediaapp.viewmodel.PostViewModel
import com.example.socialmediaapp.viewmodelfactory.PostVIewModelFactory
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UsersProfileActivity : AppCompatActivity() {
    private lateinit var databaseReference: DatabaseReference
    lateinit var binding:ActivityUsersProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_users_profile)
        val uid =intent.getStringExtra("userId")
        fetchUsersData(uid)
        databaseReference = FirebaseDatabase.getInstance().reference.child("")
        binding.userProfileActivityFollowBtn.setOnClickListener()
        {

        }
    }
    private fun fetchUsersData(uid: String?)
    {
            val repository = PostRepository()
            val viewmodel = ViewModelProvider(this, PostVIewModelFactory(repository)).get(
                PostViewModel::class.java)

            viewmodel.getUsers().observe(this, Observer {
                Log.d("fanta",it.toString())

                it.forEach{
                    user->
                    if(user.userId==uid.toString()){
                        binding.userProfileActivityTxtUsername.text=user.name
                        binding.userProfileActivityTxtFollowers.text="${user.followers} followers"
                        binding.userProfileActivityTxtFollowing.text="${user.following} following"
                        Log.d("fantastic",user.profileImage)
                        Glide.with(this).load(user.profileImage).into(binding.userProfileActivityProfileImage)

                    }
                }


            })

    }
}