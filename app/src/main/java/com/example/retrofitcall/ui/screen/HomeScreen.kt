package com.example.retrofitcall.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.retrofitcall.R

@Composable
fun HomeScreen(showAllPosts: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { showAllPosts() }, modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(R.string.show_all_posts))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen {

    }
}