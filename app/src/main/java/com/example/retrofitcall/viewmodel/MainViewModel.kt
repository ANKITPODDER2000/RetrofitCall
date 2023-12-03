package com.example.retrofitcall.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitcall.api.ApiResponse
import com.example.retrofitcall.module.Post
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val mainRepository: MainRepository) : ViewModel() {

    private val _allPostResponse: MutableStateFlow<ApiResponse> = MutableStateFlow(ApiResponse.NotStarted)
    val allPostResponse = _allPostResponse.asStateFlow()

    private val _postDetail: MutableStateFlow<ApiResponse> = MutableStateFlow(ApiResponse.NotStarted)
    val postDetail = _postDetail.asStateFlow()

    private val _userDetail: MutableStateFlow<ApiResponse> = MutableStateFlow(ApiResponse.NotStarted)
    val userDetail = _userDetail.asStateFlow()

    private val _getRandomDog: MutableStateFlow<ApiResponse> = MutableStateFlow(ApiResponse.NotStarted)
    val getRandomDog = _getRandomDog.asStateFlow()

    fun getAllPosts() {
        _allPostResponse.value = ApiResponse.Started
        viewModelScope.launch((Dispatchers.IO)) {
            val response = mainRepository.getAllPost().await()
            if (response.isSuccessful && response.body() != null) _allPostResponse.value =
                ApiResponse.Success(response.body())
            else _allPostResponse.value = ApiResponse.Error("Error code is : ${response.code()}")
        }
    }

    fun getPostDetail(postId: Int) {
        _postDetail.value = ApiResponse.Started
        viewModelScope.launch((Dispatchers.IO)) {
            val response = mainRepository.getDetailPost(postId).await()
            if (response.isSuccessful && response.body() != null) _postDetail.value =
                ApiResponse.Success(response.body())
            else _postDetail.value = ApiResponse.Error("Error code is : ${response.code()}")
        }
    }

    fun getUserDetail(userId: Int) {
        _userDetail.value = ApiResponse.Started
        viewModelScope.launch((Dispatchers.IO)) {
            val response = mainRepository.getUserDetail(userId).await()
            if (response.isSuccessful && response.body() != null) _userDetail.value =
                ApiResponse.Success(response.body())
            else _userDetail.value = ApiResponse.Error("Error code is : ${response.code()}")
        }
    }

    fun createNewPost(title: String, body: String, userId: Int): Deferred<Post?> {
        val post = Post(body, title, userId)
        return viewModelScope.async(Dispatchers.IO) {
            val response = mainRepository.createNewPost(post).await()
            response.body()
        }
    }

    fun getRandomDog(breed: String) {
        _getRandomDog.value = ApiResponse.Started
        viewModelScope.launch((Dispatchers.IO)) {
            val response = mainRepository.getRandomDog(breed).await()
            if (response.isSuccessful && response.body() != null) _getRandomDog.value =
                ApiResponse.Success(response.body())
            else _getRandomDog.value = ApiResponse.Error("Error code is : ${response.code()}")
        }
    }

}