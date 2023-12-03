package com.example.retrofitcall.viewmodel

import com.example.retrofitcall.api.PostAPI
import com.example.retrofitcall.module.Post
import com.example.retrofitcall.module.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val postAPI: PostAPI) {

    fun getAllPost(): Deferred<Response<List<Post>>> {
        return CoroutineScope(Dispatchers.IO).async {
            postAPI.getAllPost()
        }
    }

    fun getDetailPost(postId: Int): Deferred<Response<Post>> {
        return CoroutineScope(Dispatchers.IO).async {
            postAPI.getDetailPost(postId)
        }
    }

    fun getUserDetail(userId: Int): Deferred<Response<List<User>>> {
        return CoroutineScope(Dispatchers.IO).async {
            postAPI.getUserDetail(userId)
        }
    }

}