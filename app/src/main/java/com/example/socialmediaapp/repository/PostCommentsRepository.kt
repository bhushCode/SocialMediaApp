package com.example.socialmediaapp.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.socialmediaapp.activitymodel.NewPostGetDataActivityModel
import com.example.socialmediaapp.model.CommentsModelClass
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class PostCommentsRepository(private val postId:String) {

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference("post").child(postId).child("comments")
    private val commentLiveData: MutableLiveData<List<CommentsModelClass>> = MutableLiveData()

    fun getComments(): LiveData<List<CommentsModelClass>> {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val posts = mutableListOf<CommentsModelClass>()
                for (postSnapshot in snapshot.children) {
                    val post = postSnapshot.getValue(CommentsModelClass::class.java)

                    val uid = postSnapshot.key
                    Log.d("Hula","post uid = $uid")
                    databaseReference.child(uid.toString()).child("postId").setValue(uid.toString()).addOnSuccessListener{ Log.d("Hula","success")
                    }

                    post?.let { posts.add(it) }
                }
                commentLiveData.value = posts
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
        return commentLiveData
    }




}