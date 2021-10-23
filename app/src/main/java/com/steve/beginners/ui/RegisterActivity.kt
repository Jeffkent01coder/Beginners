package com.steve.beginners.ui

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.steve.beginners.MainActivity
import com.steve.beginners.R
import com.steve.beginners.databinding.ActivityLoginBinding
import com.steve.beginners.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityRegisterBinding
    lateinit var progressBar:ProgressDialog
    lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        progressBar = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()


        binding.finish.setOnClickListener {
            val email = binding.email.text.toString()
            val password = binding.pass1.text.toString()
            if (email.isEmpty()) {
                binding.email.error = "Enter Email"
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                binding.pass1.error = "Enter Password"
                return@setOnClickListener
            }
            register(email,password)
        }
    }

    private fun register(email: String, password: String) {
        progressBar.setMessage("Please wait")
        progressBar.show()
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d("Main", "Successfully created user: ${it.result!!.user!!.uid}")
                val intent=Intent(this,MainActivity::class.java)
                startActivity(intent)
                progressBar.hide()

            }
            .addOnFailureListener{
                Log.d("Main","Failed to create a user ${it.message}")
                Toast.makeText(this,"Authentication failed",Toast.LENGTH_LONG).show()
            }
    }
}