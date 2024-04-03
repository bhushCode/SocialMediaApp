package com.example.socialmediaapp.classes

import android.content.Context
import android.widget.Toast
import com.example.socialmediaapp.R
import com.example.socialmediaapp.activitymodel.NewPostGetDataActivityModel
import com.example.socialmediaapp.adapter.PostAdapter
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.coroutines.coroutineContext

class LikesHandleClass(private val context:Context) {
    lateinit var databaseReference: DatabaseReference

     fun insertLikes(post: NewPostGetDataActivityModel, holder: PostAdapter.ViewHolder) {
        FirebaseDatabase.getInstance().reference.child("post").child(post.postId).child("like").child(
            FirebaseAuth.getInstance().currentUser!!.uid)
            .setValue(true).addOnSuccessListener(OnSuccessListener {

                FirebaseDatabase.getInstance().reference.child("post").child(post.postId).child("postLike")
                    .setValue(post.postLike+1)
                    .addOnSuccessListener(OnSuccessListener {
                        holder.like.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_like_post_24,0,0,0,)
                    })

            }) }
     fun checkLike(post: NewPostGetDataActivityModel, holder: PostAdapter.ViewHolder)
    {


        FirebaseDatabase.getInstance().reference.child("post").child(post.postId).child("like").addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.hasChild(FirebaseAuth.getInstance().currentUser!!.uid)) else insertLikes(post,holder)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context,"unable to like", Toast.LENGTH_LONG).show()
            }

        })


    }
     fun checkLikedOrNot(post: NewPostGetDataActivityModel, holder: PostAdapter.ViewHolder)
    {


        FirebaseDatabase.getInstance().reference.child("post").child(post.postId).child("like").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(postsnapshot in snapshot.children)
                {

                    if(postsnapshot.key == FirebaseAuth.getInstance().currentUser!!.uid)
                    {
                        if(postsnapshot.value == true)
                        {
                            holder.like.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.baseline_like_post_24,0,0,0,)

                        }
                    }
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


    }

}