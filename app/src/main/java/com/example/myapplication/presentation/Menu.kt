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

/*
@Composable
fun menu(
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxSize(),
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.SpaceAround

    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_home_24),
            contentDescription = "Home",
            modifier = Modifier
                .size(50.dp)
                .clickable {

                }
        )
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_play_circle_outline_24),
            contentDescription = "Play",
            modifier = Modifier
                .size(50.dp)
                .clickable {
                    navController.navigate(FrequencyScreen.route) {
                        /*popUpTo(Screen.StartScreen.route) {
                        inclusive = true
                    }*/
                    }
                }
        )
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_verified_24),
            contentDescription = "Results",
            modifier = Modifier
                .size(50.dp)
                .clickable {

                }
        )
        Image(
            painter = painterResource(id = R.drawable.ic_baseline_help_center_24),
            contentDescription = "About",
            modifier = Modifier
                .size(50.dp)
                .clickable {

                }
        )
    }
}*/

@Composable
fun MainScreen(navController: NavController,
               onBluetoothStateChanged:()->Unit){

    //val navController = rememberNavController()
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

