package com.example.retrofitcall.module

data class Post(
    val body: String,
    val title: String,
    val userId: Int,
    val id: Int? = null
)