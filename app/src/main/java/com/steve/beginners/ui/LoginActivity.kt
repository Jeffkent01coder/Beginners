package com.steve.beginners.ui

import android.app.ActivityOptions
import android.app.ProgressDialog
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.util.Pair
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.steve.beginners.MainActivity
import com.steve.beginners.R
import com.steve.beginners.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding:ActivityLoginBinding
    lateinit var progressBar:ProgressDialog
    lateinit var mAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        progressBar = ProgressDialog(this)
        mAuth = FirebaseAuth.getInstance()


        binding.tvLogin.setOnClickListener {
            val email=binding.EmailLogin.text.toString().trim()
            val password=binding.PasswordLogin.text.toString()

            if (email.isEmpty()){
                binding.EmailLogin.error="enter name"
                return@setOnClickListener
            }
            if (password.isEmpty()){
                binding.PasswordLogin.error="Enter your password"
                return@setOnClickListener
            }
            loginUser(email, password)
        }

    }

    private fun loginUser(email: String, password: String) {
        progressBar.setMessage("Please Wait...")
        progressBar.show()
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                progressBar.setMessage("Please Wait...")
                progressBar.show()
                if (!it.isSuccessful) return@addOnCompleteListener
                Log.d("Main", "Succesfully created user: ${it.result?.user?.uid}")
                progressBar.hide()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK.or(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(intent)
            }
            .addOnFailureListener {
                Log.d("Main", "Failed created user: ${it.message}")
            }
    }

}

