package com.example.socialmediaapp.newpostfragment

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.socialmediaapp.R
import com.example.socialmediaapp.activitymodel.NewPostGetDataActivityModel
import com.example.socialmediaapp.databinding.FragmentNewPostBinding
import com.example.socialmediaapp.repository.PostRepository
import com.example.socialmediaapp.viewmodel.PostViewModel
import com.example.socialmediaapp.viewmodelfactory.PostVIewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.util.Date


class NewPostFragment : BottomSheetDialogFragment() {
lateinit   var binding: FragmentNewPostBinding
    lateinit var auth:FirebaseAuth
  lateinit var databasereference:FirebaseDatabase
  lateinit var storagereference:FirebaseStorage
  var usersName:String=""
    var usersProfileImage=""
   val  PICK_IMAGE_REQUEST:Int = 1
   var  postImageURi: Uri =Uri.parse("https://firebasestorage.googleapis.com/v0/b/internship2-c8b8c.appspot.com/o/userProfileImage%2Fuser%20(4).png?alt=media&token=408fe260-027f-49b6-ac1b-2e5d750b31bb")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       auth = FirebaseAuth.getInstance()
        databasereference= FirebaseDatabase.getInstance()
        storagereference=FirebaseStorage.getInstance()
        val inflatetransition= TransitionInflater.from(requireContext())
        enterTransition =  inflatetransition.inflateTransition(R.transition.slide_right_animation)

    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_new_post, container, false
        )
        fetchUsersNameProfileImage()
        var imageString = "data:image/jpeg;base64,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"



        binding.newPostFragmentInsertpost.setOnClickListener {



            binding.newPostFragmentProgressBar.visibility = View.VISIBLE
            val getpostdata = binding.newPostFragmentInsertMessage.text.toString()


            val newPost = NewPostGetDataActivityModel("", imageString, "","","",0,0,0 ,"","")

            if (postImageURi==Uri.parse("https://firebasestorage.googleapis.com/v0/b/internship2-c8b8c.appspot.com/o/userProfileImage%2Fuser%20(4).png?alt=media&token=408fe260-027f-49b6-ac1b-2e5d750b31bb")
            )

            {



                            newPost.imageUrl =""

                            // Set other post data
                            newPost.getdata =getpostdata
                            if (TextUtils.isEmpty(newPost.getdata))
                                newPost.getdata = " "

                            if(auth.currentUser!!.uid!=null)
                                newPost.postedBy = auth.currentUser!!.uid
                            else
                                newPost.postedBy=" "

                            newPost.postedAt= Date().time
                             newPost.userName=usersName
                newPost.usersProfileImage=usersProfileImage
                            // Push the new post data to the Firebase Realtime Database
                            databasereference.reference.child("post").push()
                                .setValue(newPost)
                                .addOnCompleteListener(requireActivity()) { task ->
                                    if (task.isSuccessful) {
                                        binding.newPostFragmentProgressBar.visibility = View.INVISIBLE
                                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                                    } else {
                                        Toast.makeText(requireContext(), "Failed to post: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                        binding.newPostFragmentProgressBar.visibility = View.INVISIBLE
                                    }
                                }
                        }


            else
            {
                val fileRef = storagereference.reference.child("postImages/${System.currentTimeMillis()}.jpg")

                fileRef.putFile(postImageURi!!)
                    .addOnSuccessListener { taskSnapshot ->

                        // Get the download URL of the uploaded image
                        fileRef.downloadUrl.addOnSuccessListener { uri ->
                            val imageURI = uri.toString()
                            newPost.imageUrl = imageURI

                            // Set other post data
                            newPost.getdata =getpostdata
                            if (TextUtils.isEmpty(newPost.getdata))
                                newPost.getdata = " "

                            if(auth.currentUser!!.uid!=null)
                                newPost.postedBy = auth.currentUser!!.uid
                            else
                                newPost.postedBy=" "

                            newPost.postedAt= Date().time
                            newPost.userName=usersName
                            newPost.usersProfileImage=usersProfileImage
                            // Push the new post data to the Firebase Realtime Database
                            databasereference.reference.child("post").push()
                                .setValue(newPost)
                                .addOnCompleteListener(requireActivity()) { task ->
                                    if (task.isSuccessful) {
                                        binding.newPostFragmentProgressBar.visibility = View.INVISIBLE
                                        Toast.makeText(requireContext(), "Success", Toast.LENGTH_LONG).show()
                                    } else {
                                        Toast.makeText(requireContext(), "Failed to post: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                                        binding.newPostFragmentProgressBar.visibility = View.INVISIBLE
                                    }
                                }
                        }
                        Toast.makeText(requireContext(), "Image uploaded successfully", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { exception ->
                        binding.newPostFragmentProgressBar.visibility = View.INVISIBLE
                        Toast.makeText(requireContext(), "Failed to upload image: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }

            binding.newpost = newPost
        }


        binding.newPostFragmentInsertImage.setOnClickListener()
        {
            insertPostImage()
        }



        return binding.root

        }

    private fun insertPostImage() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }
     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data!=null) {
           postImageURi = data.data!!
            binding.newPostFragmentInsertImage.setImageURI(postImageURi)
        }
    }
    private fun uploadImageToFirebase() {

    }


    private fun fetchUsersNameProfileImage() {
        val repository = PostRepository()
        val viewmodel = ViewModelProvider(this,
            PostVIewModelFactory(repository)
        ).get(PostViewModel::class.java)

        viewmodel.getUsers().observe(viewLifecycleOwner, Observer {
            Log.d("fanta",it.toString())
            val users= it[0]
            usersName=users.name
            usersProfileImage=users.profileImage
            Log.d("fantastic",users.profileImage)


        })
    }

}


