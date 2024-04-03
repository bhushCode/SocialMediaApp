package com.example.socialmediaapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.example.socialmediaapp.activitymodel.NewPostGetDataActivityModel
import com.example.socialmediaapp.adapter.PostCommentsAdapter
import com.example.socialmediaapp.model.CommentsModelClass
import com.example.socialmediaapp.model.UserModelClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PostRepository() {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("post")
    private val postLiveData: MutableLiveData<List<NewPostGetDataActivityModel>> = MutableLiveData()

    fun getPosts(): LiveData<List<NewPostGetDataActivityModel>> {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val posts = mutableListOf<NewPostGetDataActivityModel>()
                for (postSnapshot in snapshot.children) {
                    val post = postSnapshot.getValue(NewPostGetDataActivityModel::class.java)

                    val uid = postSnapshot.key
                    Log.d("Hula","post uid = $uid")
                    databaseReference.child(uid.toString()).child("postId").setValue(uid.toString()).addOnSuccessListener{ Log.d("Hula","success")
                    }

                    post?.let { posts.add(it) }
                }
                postLiveData.value = posts
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
        return postLiveData
    }
    //get user's data
    private val databaseUserReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("user")
    private val userLiveData: MutableLiveData<List<UserModelClass>> = MutableLiveData()

    fun getUsers(): LiveData<List<UserModelClass>> {
        databaseUserReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = mutableListOf<UserModelClass>()
                for (userSnapshot in snapshot.children) {

                        val user = userSnapshot.getValue(UserModelClass::class.java)
                        val uid = userSnapshot.key
                        Log.d("Hula", "post uid = $uid")
                        user?.let { users.add(it) }

                }
                userLiveData.value = users
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
        return userLiveData
    }


}