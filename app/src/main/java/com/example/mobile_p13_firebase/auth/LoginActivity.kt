package com.example.mobile_p13_firebase.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mobile_p13_firebase.homepage.MainActivity
import com.example.mobile_p13_firebase.databinding.ActivityLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        with(binding){
            loginBtLogin.setOnClickListener{

                val email = loginEtEmail.text.toString()
                val password = loginEtPassword.text.toString()
                loginUser(email, password)
            }

            loginBtToSignup.setOnClickListener{
                val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
        }

    }

    private fun loginUser(email: String, password: String) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    showToast("signInWithEmail:success")
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    showToast("signInWithEmail:failure")
                }
            }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}