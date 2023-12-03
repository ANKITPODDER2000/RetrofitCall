package com.example.retrofitcall.api

import com.example.retrofitcall.module.Post
import com.example.retrofitcall.module.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PostAPI {
    @GET("posts")
    suspend fun getAllPost(): Response<List<Post>>

    @GET("posts/{postId}")
    suspend fun getDetailPost(@Path("postId") postId: Int): Response<Post>

    @GET("users")
    suspend fun getUserDetail(@Query("id") userId: Int): Response<List<User>>

    @POST("posts")
    suspend fun createNewPost(
        @Body post: Post,
    ): Response<Post>


}