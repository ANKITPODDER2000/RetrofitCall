package com.example.retrofitcall.viewmodel

import com.example.retrofitcall.api.DogAPI
import com.example.retrofitcall.api.PostAPI
import com.example.retrofitcall.module.Post
import com.example.retrofitcall.module.RandomDog
import com.example.retrofitcall.module.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import retrofit2.Response
import javax.inject.Inject

class MainRepository @Inject constructor(private val postAPI: PostAPI, private val dogAPI: DogAPI) {

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

    fun createNewPost(post: Post): Deferred<Response<Post>> {
        return CoroutineScope(Dispatchers.IO).async {
            postAPI.createNewPost(post)
        }
    }

    fun getRandomDog(breed: String): Deferred<Response<RandomDog>> {
        return CoroutineScope(Dispatchers.IO).async {
            dogAPI.getRandomDog(breed)
        }
    }

}