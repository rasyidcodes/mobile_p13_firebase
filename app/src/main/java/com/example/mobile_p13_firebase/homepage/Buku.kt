package com.example.mobile_p13_firebase.homepage

import com.google.firebase.database.Exclude

// com.example.mobile_p12.tugas.homepage.Buku
data class Buku(
    val id : String = "",
    val buku: String = "",
    val penulis: String = "",
    val genre: String = "",
    val harga: Int = 0
)
