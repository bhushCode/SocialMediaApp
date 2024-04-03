package com.example.socialmediaapp.adapter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialmediaapp.R
import com.example.socialmediaapp.activity.UsersProfileActivity
import com.example.socialmediaapp.activitymodel.NewPostGetDataActivityModel
import com.example.socialmediaapp.classes.LikesHandleClass
import com.example.socialmediaapp.fragment.bottomsheetfragment.PostCommentsFragment
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.imageview.ShapeableImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView

class PostAdapter(private val fragmentmanager:FragmentManager,private val context: Context, private val postList: List<NewPostGetDataActivityModel>) : RecyclerView.Adapter<PostAdapter.ViewHolder>() {

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.item_post_layout_design_textview)
        val imageView: ImageView = itemView.findViewById(R.id.item_post_layout_design_postImage)
        val like:TextView = itemView.findViewById(R.id.item_post_layout_design_like)
        val comment:TextView = itemView.findViewById(R.id.item_post_layout_design_comment)
        val usersName:TextView =itemView.findViewById(R.id.item_post_layout_design_users_name)
        val profileImage: CircleImageView =itemView.findViewById(R.id.item_post_layout_design_profile_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post_layout_design, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = postList[position]
        holder.textView.text = post.getdata
        holder.usersName.text=post.userName
        Glide.with(context).load(post.imageUrl).into(holder.imageView)
        Glide.with(context).load(post.usersProfileImage).into(holder.profileImage)
        holder.like.text= post.postLike.toString()
        holder.comment.text=post.commentCount.toString()
        val likesHandleClass =LikesHandleClass(context)

likesHandleClass.checkLikedOrNot(post,holder)
        holder.like.setOnClickListener()
        {
            val zero:Long = 0
            if(post.postLike > zero)
            {
                Log.d("Hula","pressed 1")
                likesHandleClass.checkLike(post, holder)
            }
            else
            {
                likesHandleClass.insertLikes(post, holder)
            }
        }
        holder.comment.setOnClickListener()
        {
            val postCommentsFragment = PostCommentsFragment()
            val bundle:Bundle = Bundle()
            bundle.putString("postId",post.postId)
            bundle.putLong("commentCount",post.commentCount)
               postCommentsFragment.arguments = bundle
            postCommentsFragment.show(fragmentmanager,postCommentsFragment.tag)
        }


        holder.usersName.setOnClickListener()
        {
            val intent = Intent(context,UsersProfileActivity::class.java)
            intent.putExtra("userId",post.postedBy)
            intent.putExtra("userName",post.userName)
           context.startActivity(intent)
        }
        holder.profileImage.setOnClickListener()
        {
            val intent = Intent(context,UsersProfileActivity::class.java)
            intent.putExtra("userName",post.userName)
            intent.putExtra("userId",post.postedBy)
            context.startActivity(intent)
        }
    }
    override fun getItemCount(): Int {
        return postList.size
    }
}

