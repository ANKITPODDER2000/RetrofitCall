package com.example.retrofitcall.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.retrofitcall.api.ApiResponse
import com.example.retrofitcall.module.Post
import com.example.retrofitcall.viewmodel.MainViewModel

@Composable
fun PostsScreen(mainViewModel: MainViewModel, showDetailPost: (postId: Int) -> Unit) {
    val allPostResponse by mainViewModel.allPostResponse.collectAsState()
    LaunchedEffect(key1 = true) {
        mainViewModel.getAllPosts()
    }
    when (allPostResponse) {
        is ApiResponse.NotStarted, ApiResponse.Started -> {
            ShowLoadingScreen()
        }

        is ApiResponse.Success<*> -> {
            ShowAllPost((allPostResponse as ApiResponse.Success<List<Post>>).response) {postId ->
                showDetailPost(postId)
            }
        }

        is ApiResponse.Error -> {
            Text("Error")
        }
    }
}

@Composable
fun ShowAllPost(response: List<Post>, showDetailPost: (postId: Int) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        items(response) {
            ShowPost(it) { postId ->
                showDetailPost(postId)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowPost(post: Post, showDetailPost: (postId: Int) -> Unit) {
    post.id?.let {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            shadowElevation = 2.dp,
            tonalElevation = 8.dp,
            shape = RoundedCornerShape(8.dp),
            onClick = {
                showDetailPost(it)
            }
        )
        {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)) {
                Text(text = post.title, modifier = Modifier.padding(bottom = 8.dp), fontSize = 24.sp)
                Text(text = post.body, fontSize = 18.sp, maxLines = 1, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewShowPost() {
    ShowPost(Post("Hello World","Test", 1, 1)) {

    }
}

@Composable
fun ShowLoadingScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp)
        )
    }
}
