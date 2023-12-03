package com.example.retrofitcall.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(ApiConstant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Provides
    @Singleton
    fun getPostApi(retrofit: Retrofit): PostAPI {
        return retrofit.create(PostAPI::class.java)
    }
}