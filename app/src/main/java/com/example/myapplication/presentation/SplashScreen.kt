package com.example.myapplication.presentation


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.MainActivity
import com.example.myapplication.MainActivity.Companion.prefs
import com.example.myapplication.R
import com.example.myapplication.presentation.ScreenElements.scores
import com.example.myapplication.presentation.ScreenElements.tiempo
import com.example.myapplication.ui.theme.white
import kotlinx.coroutines.delay


var reset = true

@Composable
fun SplashScreen(navController: NavController){
    
    LaunchedEffect(key1 = true){
        delay(2000)
        navController.popBackStack()
        navController.navigate(Screen.StartScreen.route)

        tiempo.minutos = prefs.getMinutos()
        tiempo.segundos = prefs.getSegundos()
        scores.cpm = prefs.getCPM()
        scores.desplaza = prefs.getDes()
        scores.posicion = prefs.getPos()
        //prefs.delete()
    }


    LogoUner()
    SplashIcono()
    GLSLogo()
}

@Composable
fun LogoUner(){
    
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ){
        Spacer(modifier = Modifier.height(40.dp))
        Image(
            painter = painterResource(id = R.drawable.logo_ingenieria),
            contentDescription = "Logo Engineering",
            modifier = Modifier
                .scale(0.7f)
            )
    }
}

@Composable
fun SplashIcono() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.iconohd),
            contentDescription = "Logo App",
        )
        Text(
            text = "EntrenadorRCP",
            fontSize = 18.sp,
            color = Color.Black
        )
    }
}

@Composable
fun GLSLogo(){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Bottom
    ) {
        Image(
            painter = painterResource(id = R.drawable.glslogohd),
            contentDescription = "Logo GLS",
        )
        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview(){
    LogoUner()
    SplashIcono()
    GLSLogo()
}