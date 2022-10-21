package com.example.myapplication.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.presentation.components.BottomNavigationBar
import com.example.myapplication.presentation.Screen
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

