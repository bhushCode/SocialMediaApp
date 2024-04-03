package com.example.socialmediaapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.socialmediaapp.activitymodel.NewPostGetDataActivityModel
import com.example.socialmediaapp.model.UserModelClass
import com.example.socialmediaapp.repository.PostRepository

class PostViewModel(private val postRepository: PostRepository):ViewModel() {


    private var postLiveData: LiveData<List<NewPostGetDataActivityModel>> = postRepository.getPosts()

    fun getPosts(): LiveData<List<NewPostGetDataActivityModel>> {
        return postLiveData
    }


    //users data

        private var userLiveData: LiveData<List<UserModelClass>> = postRepository.getUsers()

    fun getUsers(): LiveData<List<UserModelClass>> {
        return userLiveData
    }
}