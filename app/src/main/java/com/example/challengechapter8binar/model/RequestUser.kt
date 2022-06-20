package com.example.challengechapter8binar.model

import com.google.gson.annotations.SerializedName

data class RequestUser(
    @SerializedName("alamat")
    val alamat: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("password")
    val password: String,
    @SerializedName("tanggal_lahir")
    val tanggal_lahir: String,
    @SerializedName("username")
    val username: String
)