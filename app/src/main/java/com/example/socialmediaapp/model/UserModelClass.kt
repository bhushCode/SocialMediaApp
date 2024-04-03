package com.example.socialmediaapp.model

data class UserModelClass(
    var userId:String="",
    var name:String="",
    var profileImage:String="",
    var followers:Int=0,
    var following:Int=0
)
