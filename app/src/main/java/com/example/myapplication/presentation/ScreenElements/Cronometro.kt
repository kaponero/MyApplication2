package com.example.myapplication.presentation.ScreenElements

import android.os.SystemClock
import androidx.compose.runtime.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

@Composable
fun rememberCountdownTimerState(
    endMillis : Long = 600000,
    step : Long = 1000,
): tiempo{
    val timerLeft = remember { mutableStateOf(0) }
    LaunchedEffect(endMillis, step){
        while (isActive && timerLeft.value < endMillis) {
            timerLeft.value = ((timerLeft.value + step).toInt())
            tiempo.segundos +=1
            if (tiempo.segundos == 60){
                tiempo.segundos = 0
                tiempo.minutos+=1
            }
            delay(step)
        }
    }
    return tiempo
}

object tiempo{

    var minutos = 0
    var segundos = 0
}

var reset = false

fun reset_tiempo(){
    if (reset){
        tiempo.segundos = 0
        tiempo.minutos = 0
        reset = false
    }
}