package com.example.socialmediaapp.viewmodelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.socialmediaapp.repository.PostCommentsRepository
import com.example.socialmediaapp.viewmodel.PostCommentViewModel

class PostCommentViewModelFactory(private val repository:PostCommentsRepository):ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PostCommentViewModel(repository) as T
    }
}