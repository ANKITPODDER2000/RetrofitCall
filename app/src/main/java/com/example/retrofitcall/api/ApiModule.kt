package com.example.retrofitcall.api

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return httpLoggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient
            .Builder()
            /*.addInterceptor(Interceptor {
                val req = it.request()
                val newReq = req.newBuilder().headers(
                    Headers.headersOf(
                        "Interceptor-Header1",
                        "1",
                        "Interceptor-Header2",
                        "2"
                    )
                ).build()
                it.proceed(newReq)
            })*/
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun getRetrofitInstance(client: OkHttpClient): Retrofit.Builder {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create()).client(client)
    }

    @Provides
    @Singleton
    fun getPostApi(retrofitBuilder: Retrofit.Builder): PostAPI {
        return retrofitBuilder.baseUrl(ApiConstant.POST_BASE_URL).build()
            .create(PostAPI::class.java)
    }

    @Provides
    @Singleton
    fun getDogApi(retrofitBuilder: Retrofit.Builder): DogAPI {
        return retrofitBuilder.baseUrl(ApiConstant.IMAGE_BASE_URL).build()
            .create(DogAPI::class.java)
    }
}