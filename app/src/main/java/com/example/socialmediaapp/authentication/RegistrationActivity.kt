package com.example.socialmediaapp.authentication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.socialmediaapp.R
import com.example.socialmediaapp.activity.HomeActivity
import com.example.socialmediaapp.databinding.ActivityRegistrationBinding

import com.example.socialmediaapp.model.UserModelClass
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class RegistrationActivity : AppCompatActivity() {
  private lateinit var auth:FirebaseAuth
  private  lateinit var name:String
  private  lateinit var email:String
  private  lateinit var password:String
  private  lateinit var confirm_password:String
    val emailPattern:Regex =Regex("[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+")


    lateinit var databasereference: FirebaseDatabase
    lateinit var storagereference: FirebaseStorage
    val  PICK_IMAGE_REQUEST:Int = 1
      var  userImageURi:Uri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/internship2-c8b8c.appspot.com/o/userProfileImage%2Fuser%20(4).png?alt=media&token=408fe260-027f-49b6-ac1b-2e5d750b31bb")

    lateinit var binding:ActivityRegistrationBinding
  override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
         binding= DataBindingUtil.setContentView(this,R.layout.activity_registration)
      auth= FirebaseAuth.getInstance()

      databasereference = FirebaseDatabase.getInstance()
      storagereference = FirebaseStorage.getInstance()

      binding.regBtn.setOnClickListener(){
                 binding.regProgressbar.visibility= View.VISIBLE
          registerUser()
      }
   binding.regSignin.setOnClickListener(){
       startActivity(Intent(this,LoginActivity::class.java))
   }


      binding.registerProfileImage.setOnClickListener{
          upLoadImage()
      }

    }

    private fun registerUser() {
        name = binding.regName.text.toString()
        email = binding.regEmail.text.toString()
        password = binding.regPassword.text.toString()
        confirm_password = binding.regConfirmpassword.text.toString()
        if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(name) && TextUtils.isEmpty(confirm_password)
        ) {
            binding.regProgressbar.visibility = View.INVISIBLE
            if (TextUtils.isEmpty(email) && TextUtils.isEmpty(password) && TextUtils.isEmpty(name) && TextUtils.isEmpty(confirm_password))
                Toast.makeText(this@RegistrationActivity, "fill all fields", Toast.LENGTH_SHORT).show()

                if (email.isEmpty())
                    Toast.makeText(this@RegistrationActivity, "enter email", Toast.LENGTH_SHORT).show()
                else if (name.isEmpty())
                    Toast.makeText(this@RegistrationActivity, "enter name", Toast.LENGTH_SHORT).show()
                else if (password.isEmpty())
                    Toast.makeText(this@RegistrationActivity, "enter password", Toast.LENGTH_SHORT).show()
                else if (confirm_password.isEmpty())
                    Toast.makeText(this@RegistrationActivity, "confirm your password", Toast.LENGTH_SHORT).show()
            } else if (!email.matches(emailPattern)) {
                binding.regProgressbar.visibility = View.INVISIBLE
            binding.regEmail.error = "invalid email" }

        else if (password.length < 6) {
                binding.regProgressbar.visibility = View.INVISIBLE

                binding.regPassword.error = "invalid password"
            }
        else if (confirm_password.length < 6) {
                binding.regProgressbar.visibility = View.INVISIBLE

                binding.regConfirmpassword.error = "invalid password"
            }
        else {
                auth.createUserWithEmailAndPassword(email, confirm_password)
                    .addOnCompleteListener(this,
                        OnCompleteListener { task ->
                            if (task.isSuccessful) {

                                if (userImageURi==Uri.parse("https://firebasestorage.googleapis.com/v0/b/internship2-c8b8c.appspot.com/o/userProfileImage%2Fuser%20(4).png?alt=media&token=408fe260-027f-49b6-ac1b-2e5d750b31bb"))
                                {

                                    val imageUri = "https://firebasestorage.googleapis.com/v0/b/internship2-c8b8c.appspot.com/o/userProfileImage%2Fuser%20(4).png?alt=media&token=408fe260-027f-49b6-ac1b-2e5d750b31bb"



                                    databasereference.reference.child("user").child(auth.currentUser!!.uid).setValue(UserModelClass(FirebaseAuth.getInstance().currentUser!!.uid,name,imageUri,0,0)).addOnCompleteListener(
                                        OnCompleteListener {
                                            if(task.isSuccessful)
                                            {
                                                binding.regProgressbar.visibility = View.INVISIBLE










                                                startActivity(Intent(this,HomeActivity::class.java))
                                            }
                                        })




                                }
                                else
                                {
                                    val reference = storagereference.reference.child("userProfileImage/${System.currentTimeMillis()}.jpg")

                                    reference.putFile(userImageURi).addOnSuccessListener { task ->
                                        reference.downloadUrl.addOnSuccessListener(this) { uri ->
                                            val downloadUri = uri.toString()
                                            Log.d("imagex", downloadUri)
                                            databasereference.reference.child("user").child(auth.currentUser!!.uid).setValue(UserModelClass(FirebaseAuth.getInstance().currentUser!!.uid,name, downloadUri, 0, 0)).addOnSuccessListener {
                                                binding.regProgressbar.visibility = View.INVISIBLE
                                                startActivity(Intent(this@RegistrationActivity, HomeActivity::class.java))
                                            }.addOnFailureListener {
                                                // Handle failure here
                                            }
                                        }.addOnFailureListener {
                                            // Handle failure here
                                        }
                                    }.addOnFailureListener {
                                        // Handle failure here
                                    }

                                }





                            } else {
                                binding.regProgressbar.visibility = View.INVISIBLE

                                Toast.makeText(this, "error in creating account", Toast.LENGTH_LONG)
                                    .show()


                            }
                        })
            }
        }



    private fun upLoadImage()
    {
        val intent= Intent()
        intent.type = "image/*"
        intent.action=Intent.ACTION_GET_CONTENT
startActivityForResult(intent,PICK_IMAGE_REQUEST,null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK)
        {
            if(data!=null && data.data !=null)
            {
                userImageURi = data.data!!
                binding.registerProfileImage.setImageURI(userImageURi)
            }

        }
    }
    }

