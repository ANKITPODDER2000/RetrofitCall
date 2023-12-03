package com.example.retrofitcall.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.retrofitcall.viewmodel.MainViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPostScreen(mainViewModel: MainViewModel) {
    var title by rememberSaveable { mutableStateOf("") }
    var body by rememberSaveable { mutableStateOf("") }
    var userId by rememberSaveable { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(text = "Add New Task", modifier = Modifier.padding(bottom = 16.dp))
        OutlinedTextField(
            value = title,
            label = { Text(text = "Task Title") },
            onValueChange = {
                title = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp), maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next)
        )
        OutlinedTextField(
            value = userId,
            label = { Text(text = "Task Title") },
            onValueChange = {
                userId = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp), maxLines = 1,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
        OutlinedTextField(
            value = body,
            label = { Text(text = "Task Body") },
            onValueChange = {
                body = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp)
                .padding(bottom = 16.dp), maxLines = 1,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                if (title.isEmpty() || body.isEmpty() || userId.isEmpty()) {
                    Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                    return@KeyboardActions
                }
                focusManager.clearFocus()
                CoroutineScope(Dispatchers.IO).launch {
                    val post = mainViewModel.createNewPost(title, body, userId.toInt()).await()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Post: $post", Toast.LENGTH_LONG).show()
                    }
                    body = ""
                    title = ""
                    userId = ""
                }
            })
        )
        OutlinedButton(
            onClick = {
                if (title.isEmpty() || body.isEmpty() || userId.isEmpty()) {
                    Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                    return@OutlinedButton
                }
                CoroutineScope(Dispatchers.IO).launch {
                    val post = mainViewModel.createNewPost(title, body, userId.toInt()).await()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Post: $post", Toast.LENGTH_LONG).show()
                    }
                    body = ""
                    title = ""
                    userId = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = "Create New Post")
        }
    }
}
