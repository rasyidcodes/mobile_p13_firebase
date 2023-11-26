package com.example.mobile_p13_firebase.addbuku

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.mobile_p13_firebase.R
import com.example.mobile_p13_firebase.databinding.ActivityAddBukuBinding
import com.example.mobile_p13_firebase.databinding.ActivityLoginBinding
import com.example.mobile_p13_firebase.homepage.Buku
import com.google.firebase.firestore.FirebaseFirestore

class AddBukuActivity : AppCompatActivity() {


    private lateinit var binding: ActivityAddBukuBinding
    private val firestore = FirebaseFirestore.getInstance()
    private val puisiCollectionRef  = firestore.collection("buku")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddBukuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding){
            addBTTambah.setOnClickListener{
                var buku = addETBuku.text.toString()
                var penulis = addETPenulis.text.toString()
                var genre = addETGenre.text.toString()
                var harga = addETHarga.text.toString()


                if (buku == "" || penulis == "" || genre == "" || harga == "" ){
                    showToast("Cant Empty Data!")
                }else{
                    try {
                        val integerHarga = harga.toInt()
                        add(
                            Buku(
                                buku = buku,
                                penulis = penulis,
                                genre = genre,
                                harga = integerHarga
                            )
                        )
                        showToast("INSERTED!")
                    } catch (e: NumberFormatException) {
                        showToast("Harga must number!")
                    }
                }
            }
        }

    }

    private fun add(buku: Buku){
        puisiCollectionRef.add(buku).addOnFailureListener { e ->
            Log.d("AddBukuActivity", "Error adding buku", e)
            showToast(e.toString())
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}