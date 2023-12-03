package com.example.retrofitcall.ui.screen

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.retrofitcall.ui.screen.util.NavRoute
import com.example.retrofitcall.viewmodel.MainViewModel

private const val TAG = "MainScreen"

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainScreen(
    mainViewModel: MainViewModel = viewModel(),
    navHostController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navHostController.currentBackStackEntryAsState()
    val context = LocalContext.current as Activity

    Scaffold(
        topBar = {
            AppBar(
                navBackStackEntry?.destination?.route ?: "Home Screen",
                {
                    if (navHostController.previousBackStackEntry == null) context.finishAffinity()
                    else navHostController.navigateUp()
                },
                {
                    navHostController.navigate(NavRoute.CREATE_NEW_POST.name)
                }
            )
        }
    ) { paddingValues ->
        NavHost(
            navController = navHostController,
            startDestination = NavRoute.HOME_SCREEN.name,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(NavRoute.HOME_SCREEN.name) {
                HomeScreen(
                    { navHostController.navigate(NavRoute.ALL_POST_SCREEN.name) },
                    { navHostController.navigate(NavRoute.DOG_API.name) }
                )
            }
            composable(NavRoute.ALL_POST_SCREEN.name) {
                PostsScreen(mainViewModel) { postId ->
                    navHostController.navigate("${NavRoute.SHOW_DETAIL_POST.name}/$postId")
                }
            }
            composable("${NavRoute.SHOW_DETAIL_POST.name}/{id}") {
                val postId = try {
                    it.arguments?.get("id").toString().toInt()
                } catch (e: Exception) {
                    Log.e(TAG, "MainScreen: error : ${e.message}")
                    -1
                }
                DetailPostScreen(mainViewModel, postId)
            }
            composable(NavRoute.CREATE_NEW_POST.name) {
                AddPostScreen(mainViewModel)
            }
            composable(NavRoute.DOG_API.name) {
                RandomDogImageScree(mainViewModel)
            }
        }
    }
}