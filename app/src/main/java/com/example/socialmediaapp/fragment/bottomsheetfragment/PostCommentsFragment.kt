package com.example.socialmediaapp.fragment.bottomsheetfragment

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialmediaapp.R
import com.example.socialmediaapp.adapter.PostCommentsAdapter
import com.example.socialmediaapp.databinding.FragmentPostCommentsBinding
import com.example.socialmediaapp.model.CommentsModelClass
import com.example.socialmediaapp.repository.PostCommentsRepository
import com.example.socialmediaapp.viewmodel.PostCommentViewModel
import com.example.socialmediaapp.viewmodelfactory.PostCommentViewModelFactory
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.ArrayList
import java.util.Date

class PostCommentsFragment : BottomSheetDialogFragment() {
lateinit var binding:FragmentPostCommentsBinding
lateinit var databaseReference: DatabaseReference
lateinit var txt_comments:String
   lateinit var  postId:String

    private lateinit var postAdapter: PostCommentsAdapter

    private val commentList = mutableListOf<CommentsModelClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        databaseReference=FirebaseDatabase.getInstance().reference.child("post")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_post_comments,container,false)
          binding.postCommentItemLayoutRecyclerView.layoutManager=LinearLayoutManager(requireContext())
        val bundle  = arguments
        postId =  bundle!!.getString("postId").toString()

        fetchPostsViewModel()


        var commentCount = bundle!!.getLong("commentCount")
        binding.sendCommentBtn.setOnClickListener()
        {
            txt_comments=binding.edtComment.text.toString()

            if(TextUtils.isEmpty(txt_comments))
            {
                Toast.makeText(requireContext(),"enter valid comment",Toast.LENGTH_LONG).show()
            }
            else
            {
                if(TextUtils.isEmpty(postId))
                {

                }
                else{

                databaseReference.child(postId).child("comments").push().setValue(
                    CommentsModelClass(txt_comments,Date().time,postId,FirebaseAuth.getInstance().currentUser!!.uid)).addOnCompleteListener(OnCompleteListener {

                    binding.edtComment.setText("")

                    FirebaseDatabase.getInstance().reference.child("post").child(postId)
                        .addListenerForSingleValueEvent(object :ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {






                             val comment = snapshot.child("commentCount").getValue(Long::class.java)!!


                                    FirebaseDatabase.getInstance().reference.child("post").child(postId).child("commentCount")
                                        .setValue(comment+1)
                                        .addOnSuccessListener(OnSuccessListener {

                                        })




                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }

                        })





                    Toast.makeText(requireContext(),"comment added",Toast.LENGTH_LONG).show()
                })
                }

            }

        }
     return binding.root
    }
    private  fun fetchUserData()
    {
    }


    private fun fetchPostsViewModel() {
        postAdapter = PostCommentsAdapter(requireContext(),commentList)

        val repository = PostCommentsRepository(postId)
        val viewmodel = ViewModelProvider(this, PostCommentViewModelFactory(repository)).get(
            PostCommentViewModel::class.java)
        viewmodel.getPosts().observe(viewLifecycleOwner, Observer {comments->

            commentList.clear()
            comments.forEach { comment ->
                Log.d("Hula","${comment}")
                commentList.add(comment)
                commentList.reverse()
            }
            binding.postCommentItemLayoutRecyclerView.adapter = postAdapter
        })
    }
}