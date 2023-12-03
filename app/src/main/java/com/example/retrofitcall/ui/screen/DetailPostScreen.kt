package com.example.retrofitcall.ui.screen

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.retrofitcall.api.ApiResponse
import com.example.retrofitcall.module.Post
import com.example.retrofitcall.module.User
import com.example.retrofitcall.viewmodel.MainViewModel

@Composable
fun DetailPostScreen(mainViewModel: MainViewModel, postId: Int) {
    val postDetailState by mainViewModel.postDetail.collectAsState()
    LaunchedEffect(key1 = postId) {
        mainViewModel.getPostDetail(postId)
    }
    when (postDetailState) {
        is ApiResponse.NotStarted, ApiResponse.Started -> {
            ShowLoadingScreen()
        }

        is ApiResponse.Success<*> -> {
            val postResponse = postDetailState as ApiResponse.Success<Post>
            ShowPostDetail(mainViewModel, postResponse.response)
        }

        is ApiResponse.Error -> {
            CloseApplication(errorCode = (postDetailState as ApiResponse.Error).errorCode)
        }
    }

}

@Composable
fun ShowPostDetail(mainViewModel: MainViewModel, post: Post) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Text(
            text = post.title,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        UserDetail(mainViewModel, post.userId)
        Text(
            text = post.body,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )
    }
}

@Composable
fun UserDetail(mainViewModel: MainViewModel, userId: Int) {
    val userDetailState by mainViewModel.userDetail.collectAsState()
    LaunchedEffect(key1 = userId) {
        mainViewModel.getUserDetail(userId)
    }
    Row(modifier = Modifier.height(48.dp)) {
        if (userDetailState is ApiResponse.Success<*>) {
            val userList = ((userDetailState as ApiResponse.Success<*>).response as List<*>)
            if (userList.isNotEmpty()) {
                val user = userList[0] as User
                Image(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = "User Icon",
                    modifier = Modifier.padding(end = 8.dp)
                )
                Text(
                    text = user.email,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }
        }
    }
}

@Composable
fun CloseApplication(errorCode: String) {
    val context = LocalContext.current as Activity
    AlertDialog(
        onDismissRequest = { context.finishAffinity() },
        confirmButton = {
            TextButton(onClick = { context.finishAffinity() }) {
                Text(
                    text = "Close Application",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        },
        title = { Text(text = "Retrofit Example") },
        text = { Text(text = "Error happened while fetching Data ...., errorCode : $errorCode") },
    )
}

/*
@Preview(showSystemUi = true)
@Composable
fun PreviewDetailPostScreen() {
    ShowPostDetail(
        Post(
            "quia et suscipit\nsuscipit recusandae consequuntur expedita et cum\nreprehenderit molestiae ut ut quas totam\nnostrum rerum est autem sunt rem eveniet architecto",
            1,
            "sunt aut facere repellat provident occaecati excepturi optio reprehenderit",
            1
        )
    )
}*/
