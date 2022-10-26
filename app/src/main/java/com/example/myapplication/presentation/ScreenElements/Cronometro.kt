package com.example.myapplication.presentation.ScreenElements

import android.os.SystemClock
import androidx.compose.runtime.*
import com.example.myapplication.MainActivity.Companion.prefs
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.properties.Delegates

@Composable
fun rememberCountdownTimerState(
    cpm_value : Int,
    des_value: Int,
    pos_value :Int,
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
            if(cpm_value>99 && cpm_value<121){
                scores.cpm +=1
            }
            if (pos_value==1){
                scores.posicion+=1
            }
            scores.desplaza+=des_value

            delay(step)
        }
    }
    return tiempo
}

object tiempo{

    var minutos = 0
    var segundos = 0
}

object scores{
    var cpm:Long = 0
    var desplaza:Long = 0
    var posicion:Long = 0
}

fun reset_scores(){
    scores.cpm = 0
    scores.desplaza = 0
    scores.posicion = 0
}

fun reset_tiempo(){

    tiempo.segundos = 0
    tiempo.minutos = 0
}

@Composable
fun calculo_des(des_value : Int){
    val maximo = remember { mutableStateOf(0) }
    if(des_value>maximo.value){
        maximo.value = des_value
    }
}