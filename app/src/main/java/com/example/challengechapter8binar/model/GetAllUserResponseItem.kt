package com.example.challengechapter8binar.model

data class GetAllUserResponseItem(
    val alamat: String,
    val email: String,
    val id: String,
    val image: String,
    val name: String,
    val password: String,
    val tanggal_lahir: String,
    val username: String
)