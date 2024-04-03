package com.example.socialmediaapp.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.socialmediaapp.R
import com.example.socialmediaapp.activitymodel.NewPostGetDataActivityModel
import com.example.socialmediaapp.adapter.PostAdapter
import com.example.socialmediaapp.databinding.FragmentHomeBinding
import com.example.socialmediaapp.databinding.FragmentPostBinding
import com.example.socialmediaapp.newpostfragment.NewPostFragment
import com.example.socialmediaapp.repository.PostRepository
import com.example.socialmediaapp.viewmodel.PostViewModel
import com.example.socialmediaapp.viewmodelfactory.PostVIewModelFactory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class PostFragment : Fragment() {
    lateinit var firebaseDatabase: FirebaseDatabase
    lateinit var binding: FragmentPostBinding
    lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private lateinit var postAdapter: PostAdapter
    private val postList = mutableListOf<NewPostGetDataActivityModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_post, container, false)
        database = FirebaseDatabase.getInstance().reference.child("post")
        binding.postFragmentRecyclerView .layoutManager = LinearLayoutManager(requireContext())

       // fetchPosts()



      fetchPostsViewModel()




        binding.postFragmentFloatingbtnNewpost.setOnClickListener{

          //  callFragment(NewPostFragment())

            val newPostFragment = NewPostFragment()
            newPostFragment.show(childFragmentManager,newPostFragment.tag)
        }


        return binding.root
    }

    private fun fetchPostsViewModel() {
        postAdapter = PostAdapter(childFragmentManager,requireContext(), postList,)

        val repository = PostRepository()
        val viewmodel = ViewModelProvider(this, PostVIewModelFactory(repository)).get(
            PostViewModel::class.java)
        viewmodel.getPosts().observe(viewLifecycleOwner, Observer {posts->

            postList.clear()
            posts.forEach { post ->
                Log.d("Hula","${post}")
            postList.add(post)
            }
            postList.reverse()
            binding.postFragmentRecyclerView.adapter = postAdapter
        })
    }

    fun  callFragment(fragment: Fragment) {
        childFragmentManager.beginTransaction().replace(R.id.post_frameLayout,fragment).commit()
        binding.postFragmentRecyclerView.visibility =View.GONE
        binding.postFrameLayout.visibility = View.VISIBLE
    }
//
//    private   fun fetchPosts() {
//        database.addValueEventListener(object : ValueEventListener {
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
//        }) }

}