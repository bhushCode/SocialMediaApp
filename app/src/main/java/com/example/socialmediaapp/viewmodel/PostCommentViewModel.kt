package com.example.socialmediaapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.socialmediaapp.activitymodel.NewPostGetDataActivityModel
import com.example.socialmediaapp.model.CommentsModelClass
import com.example.socialmediaapp.repository.PostCommentsRepository

class PostCommentViewModel(private val commentsRepository: PostCommentsRepository):ViewModel() {

    private var commentsLiveData: LiveData<List<CommentsModelClass>> = commentsRepository.getComments()

   fun getPosts(): LiveData<List<CommentsModelClass>> {
        return commentsLiveData
    }
}