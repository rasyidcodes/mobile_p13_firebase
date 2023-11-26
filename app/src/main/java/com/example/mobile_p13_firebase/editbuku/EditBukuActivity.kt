package com.example.mobile_p12.tugas.editbuku

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobile_p13_firebase.databinding.ActivityEditBukuBinding
import com.example.mobile_p13_firebase.homepage.MainActivity

import com.google.firebase.firestore.FirebaseFirestore

class EditBukuActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditBukuBinding
    private val firestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBukuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val id = intent.getStringExtra("id")
        val bukures = intent.getStringExtra("buku")
        val penulisres = intent.getStringExtra("penulis")
        val genreres = intent.getStringExtra("genre")
        val hargares = intent.getStringExtra("harga")

        with(binding) {
            // Add null checks for each retrieved extra
            editETBuku.text = bukures?.let { Editable.Factory.getInstance().newEditable(it) }
            editETPenulis.text = penulisres?.let { Editable.Factory.getInstance().newEditable(it) }
            editETGenre.text = genreres?.let { Editable.Factory.getInstance().newEditable(it) }
            editETHarga.text = hargares?.let { Editable.Factory.getInstance().newEditable(it) }

            editBTEdit.setOnClickListener {
                val buku = editETBuku.text.toString()
                val penulis = editETPenulis.text.toString()
                val genre = editETGenre.text.toString()
                val harga = editETHarga.text.toString()

                if (buku.isBlank() || penulis.isBlank() || genre.isBlank() || harga.isBlank()) {
                    showToast("Cant Empty Data!")
                } else {
                    try {
                        val integerHarga = harga.toInt()

                        id?.let {
                            // Update data in Firestore
                            firestore.collection("buku").document(it)
                                .update(
                                    mapOf(
                                        "buku" to buku,
                                        "penulis" to penulis,
                                        "genre" to genre,
                                        "harga" to integerHarga
                                    )
                                )
                                .addOnSuccessListener {
                                    showToast("Data Updated successfully")
                                    val intent = Intent(this@EditBukuActivity, MainActivity::class.java)
                                    startActivity(intent)
                                }
                                .addOnFailureListener { e ->
                                    showToast("Failed to update data: $e")
                                }
                        }
                    } catch (e: NumberFormatException) {
                        showToast("Harga must be a number!")
                    }
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
