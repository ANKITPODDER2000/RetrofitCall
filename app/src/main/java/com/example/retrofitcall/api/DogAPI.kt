package com.example.retrofitcall.api

import com.example.retrofitcall.module.RandomDog
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface DogAPI {

    @Headers("Static-Header1: 1", "Static-Header2: 1")
    @GET("api/breed/{breed}/images/random")
    suspend fun getRandomDog(@Path("breed") breed: String) : Response<RandomDog>

}