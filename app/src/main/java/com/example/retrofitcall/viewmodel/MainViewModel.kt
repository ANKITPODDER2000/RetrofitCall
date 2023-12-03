package com.example.retrofitcall.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitcall.api.ApiResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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

}