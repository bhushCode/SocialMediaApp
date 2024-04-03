package com.example.socialmediaapp.model

data class CommentsModelClass(
    var message:String="",
    var postedAt:Long=0,
    var postId:String="",
    var userId:String="",
    var userName:String="",
    var userProfileImage:String=""
)
