package com.example.retrofitcall.ui.screen

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.retrofitcall.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(title: String, backButtonClickHandler: () -> Unit, addPostButtonHandler: () -> Unit) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            BackNavIcon {
                backButtonClickHandler()
            }
        },
        actions = {
            AddPostButton {
                addPostButtonHandler()
            }
        },
        modifier = Modifier.shadow(16.dp)
    )
}

@Composable
fun AddPostButton(addPostButtonHandler: () -> Unit) {
    IconButton(onClick = { addPostButtonHandler() }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(R.string.add_new_post)
        )
    }
}

@Composable
fun BackNavIcon(backButtonClickHandler: () -> Unit) {
    IconButton(onClick = { backButtonClickHandler() }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back_button)
        )
    }
}
