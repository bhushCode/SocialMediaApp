package com.example.socialmediaapp.activitymodel

data class NewPostGetDataActivityModel(
    var message:String="",
    var imageUrl:String="",
    var getdata:String="",
    var postedBy:String="",
    var postId:String="",
    var postedAt:Long=0,
    var postLike:Long = 0,
    var commentCount:Long=0,
    var userName:String="",
    var usersProfileImage:String=""

)
