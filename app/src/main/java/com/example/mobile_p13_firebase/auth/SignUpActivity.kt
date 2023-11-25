package com.example.mobile_p13_firebase.auth

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mobile_p13_firebase.R
import com.example.mobile_p13_firebase.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        with(binding){
            signupBtSignup.setOnClickListener{

                    val email = signupEtEmail.text.toString()
                    val password = signupEtPassword.text.toString()
                    insert(email, password)
            }
        }

    }

    private fun insert(email: String, password: String) {

        Toast.makeText(
            baseContext,
            "execute",
            Toast.LENGTH_SHORT,
        ).show()
        auth.createUserWithEmailAndPassword("email@ahhahah.com", "Passjj1999000")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    Toast.makeText(
                        baseContext,
                        "createUserWithEmail:success",
                        Toast.LENGTH_SHORT,
                    ).show()
                    val user = auth.currentUser
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }

        Toast.makeText(
            baseContext,
            "DONE",
            Toast.LENGTH_SHORT,
        ).show()
    }
}