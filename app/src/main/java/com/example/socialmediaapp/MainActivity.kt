package com.example.socialmediaapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.socialmediaapp.authentication.LoginActivity
import com.example.socialmediaapp.repository.PostRepository
import com.example.socialmediaapp.viewmodel.PostViewModel
import com.example.socialmediaapp.viewmodelfactory.PostVIewModelFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this@MainActivity,LoginActivity::class.java))


    }
}