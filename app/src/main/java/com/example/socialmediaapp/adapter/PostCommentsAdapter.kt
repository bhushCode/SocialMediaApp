package com.example.socialmediaapp.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.socialmediaapp.R
import com.example.socialmediaapp.activitymodel.NewPostGetDataActivityModel
import com.example.socialmediaapp.model.CommentsModelClass
import de.hdodenhof.circleimageview.CircleImageView

class PostCommentsAdapter(private val context: Context, private val commentList: List<CommentsModelClass> ): RecyclerView.Adapter<PostCommentsAdapter.CommentViewHolder>() {


    class  CommentViewHolder(v:View):RecyclerView.ViewHolder(v)
    {

        val txt_comment:TextView = v.findViewById(R.id.post_comment_userComment)
        val userName:TextView=v.findViewById(R.id.post_comment_user_name)
        val userProfileImage=v.findViewById<CircleImageView>(R.id.post_comment_profile_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.post_comment_item_layout,parent,false)
        return CommentViewHolder(view)
    }

    override fun getItemCount(): Int {
        return commentList.size
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment= commentList[position]
        holder.txt_comment.text = comment.message

    }
}