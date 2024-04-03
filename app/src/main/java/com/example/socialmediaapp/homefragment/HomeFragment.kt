package com.example.socialmediaapp.homefragment

import android.os.Bundle
import android.transition.TransitionInflater
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialmediaapp.R
import com.example.socialmediaapp.activitymodel.NewPostGetDataActivityModel
import com.example.socialmediaapp.adapter.PostAdapter
import com.example.socialmediaapp.databinding.FragmentHomeBinding
import com.example.socialmediaapp.newpostfragment.NewPostFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class HomeFragment : Fragment() {

lateinit var firebaseDatabase:FirebaseDatabase
    lateinit var binding:FragmentHomeBinding
    lateinit var auth:FirebaseAuth

    private lateinit var database: DatabaseReference
    private lateinit var postAdapter: PostAdapter
    private val postList = mutableListOf<NewPostGetDataActivityModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        val inflatetransition=TransitionInflater.from(requireContext())
        enterTransition =  inflatetransition.inflateTransition(R.transition.slide_right_animation)




    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        database = FirebaseDatabase.getInstance().reference.child("post")
        binding.homeFragmentRecyclerView .layoutManager = LinearLayoutManager(requireContext())
        postAdapter = PostAdapter(childFragmentManager,requireContext(), postList)
        binding.homeFragmentRecyclerView.adapter = postAdapter
       // fetchPosts()






        return binding.root

    }

//
//  private   fun fetchPosts() {
//
//      database.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                postList.clear()
//                for (postSnapshot in snapshot.children) {
//                    val uid = postSnapshot.key
//                    val text = postSnapshot.child("getdata").getValue(String::class.java)
//                    val imageUrl = postSnapshot.child("imageUrl").getValue(String::class.java)
//                    val message = postSnapshot.child("message").getValue(String::class.java)
//                    val postedAt = postSnapshot.child("postedAt").getValue(Long::class.java)
//                    val postedBy = postSnapshot.child("postedBy").getValue(String::class.java)
//                    if (uid != null && text != null && imageUrl != null ) {
//                        postList.add(NewPostGetDataActivityModel(text, imageUrl,message.toString(),postedBy!!,postedAt!!))
//                        postList.reverse()
//                    }
//                }
//                postAdapter.notifyDataSetChanged()
//            }
//            override fun onCancelled(error: DatabaseError) {
//                // Handle error
//            }
//        })
//      Toast.makeText(requireContext(),"runns",Toast.LENGTH_LONG).show()
//
//
//  }



}