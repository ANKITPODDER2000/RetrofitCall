package com.example.retrofitcall.ui.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import com.example.retrofitcall.R
import com.example.retrofitcall.api.ApiResponse
import com.example.retrofitcall.module.RandomDog
import com.example.retrofitcall.ui.screen.util.DogBreed
import com.example.retrofitcall.viewmodel.MainViewModel

@Composable
fun RandomDogImageScree(mainViewModel: MainViewModel) {

    var selectedBreed by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            DogBreedDropDown(
                selectedBreed, Modifier.fillMaxWidth(0.65f)
            ) {
                selectedBreed = it
            }
            Spacer(modifier = Modifier.fillMaxWidth(0.1f))
            FetchButton(mainViewModel, selectedBreed)
        }
        ShowRandomDogImage(mainViewModel)
    }
}

@Composable
fun ShowRandomDogImage(mainViewModel: MainViewModel) {
    val randomImageState by mainViewModel.getRandomDog.collectAsState()

    if (randomImageState is ApiResponse.Started) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(modifier = Modifier.size(48.dp))
        }
    } else if (randomImageState is ApiResponse.Success<*>) {
        val randomDog = (randomImageState as ApiResponse.Success<*>).response as RandomDog
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = randomDog.message,
                contentDescription = "Random Dog Image",
                modifier = Modifier.fillMaxWidth(0.4f),
                placeholder = painterResource(R.drawable.loading),
                error = painterResource(id = R.drawable.loading_error),
                contentScale = ContentScale.FillWidth
            )
        }
    }

}

@Composable
fun FetchButton(mainViewModel: MainViewModel, selectedBreed: String) {
    Button(
        onClick = {
            if (selectedBreed.isNotEmpty()) mainViewModel.getRandomDog(selectedBreed)
        }, modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(top = 8.dp),
        shape = RoundedCornerShape(4.dp)
    ) {
        Text(text = "Get Random Dog")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DogBreedDropDown(
    breed: String,
    modifier: Modifier = Modifier,
    handleDismissReq: (value: String) -> Unit,
) {
    var isExpanded by rememberSaveable { mutableStateOf(false) }
    var selectedBreed by remember { mutableStateOf("") }
    var mTextFieldSize by remember { mutableStateOf(Size.Zero) }
    Column(modifier = modifier) {
        OutlinedTextField(
            value = breed,
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    mTextFieldSize = coordinates.size.toSize()
                }
                .clickable {
                    isExpanded = !isExpanded
                },
            label = { Text(text = "Please select a breed group", fontSize = 18.sp) },
            trailingIcon = {
                Icon(Icons.Filled.ArrowDropDown, "contentDescription",
                    Modifier.clickable { isExpanded = !isExpanded })
            },
            textStyle = TextStyle.Default.copy(fontSize = 18.sp),
            enabled = false

        )
        DropdownMenu(
            expanded = isExpanded,
            onDismissRequest = {
                handleDismissReq(selectedBreed)
                isExpanded = !isExpanded
            },
            modifier = Modifier
                .width(with(LocalDensity.current) { mTextFieldSize.width.toDp() })
                .height(400.dp)
        ) {
            DogBreed.dogBreedList.forEach { breed ->
                DropdownMenuItem(
                    text = { Text(text = breed, fontSize = 18.sp) },
                    onClick = {
                        selectedBreed = breed
                        handleDismissReq(selectedBreed)
                        isExpanded = !isExpanded
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 32.dp, vertical = 3.dp)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun PreviewDogBreedDropDown() {
    DogBreedDropDown("", Modifier) {}
}