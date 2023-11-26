package com.example.mobile_p13_firebase.homepage

import BukuAdapter
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobile_p13_firebase.R
import com.example.mobile_p13_firebase.addbuku.AddBukuActivity

import com.example.mobile_p13_firebase.databinding.ActivityMainBinding
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private  lateinit var binding : ActivityMainBinding

    private lateinit var bukuAdapter: BukuAdapter
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(this)
        bukuAdapter = BukuAdapter()
        recyclerView.adapter = bukuAdapter

        // Fetch and observe buku data from Firestore
        fetchDataAndObserve()

        with(binding) {
            addButton.setOnClickListener {
                val intent = Intent(this@MainActivity, AddBukuActivity::class.java)
                startActivity(intent)
            }
        }
    }
    private fun fetchDataAndObserve() {
        val bukuCollection = firestore.collection("buku")

        // Observe Firestore changes
        bukuCollection.addSnapshotListener { snapshot, exception ->
            if (exception != null) {
                showToast(this@MainActivity, "Error fetching data from Firestore")
                return@addSnapshotListener
            }

            snapshot?.let { documents ->
                val bukus = mutableListOf<Buku>()
                for (document in documents) {
                    val bukuId = document.id
                    val buku = document.toObject(Buku::class.java).copy(id = bukuId)
                    bukus.add(buku)
                }

                // Update the UI with the Firestore data
                bukuAdapter.setBukus(bukus)
            }
        }
    }

    private fun showToast(context: Context, message: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(context, message, duration).show()
    }

}