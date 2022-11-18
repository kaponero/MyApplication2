package com.example.myapplication.presentation

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.myapplication.presentation.components.BottomNavigationBar
import com.example.myapplication.presentation.Screen.*


@Composable
fun MainScreen(navController: NavController,
               onBluetoothStateChanged:()->Unit){

    val navigationItems = listOf(
        StartScreen,
        FrequencyScreen,
        About
    )

    Scaffold(
        bottomBar = { BottomNavigationBar(navController as NavHostController, items = navigationItems)}
    ) {

    }
}

