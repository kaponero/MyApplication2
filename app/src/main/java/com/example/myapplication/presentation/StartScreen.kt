package com.example.myapplication.presentation
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.myapplication.MainActivity.Companion.prefs
import com.example.myapplication.presentation.ScreenElements.tiempo
import com.example.myapplication.ui.theme.*


val fondo_resultado = Brush.verticalGradient(
    listOf(Bordo, DarkBordo))


@Composable
fun StartScreen(navController: NavController,onBluetoothStateChanged:()->Unit){

    MainScreen(navController,onBluetoothStateChanged)

    LaunchedEffect(false){

        prefs.saveTiempo(tiempo.minutos,tiempo.segundos)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .offset(0.dp, -30.dp),
        contentAlignment = Alignment.Center){
        Column(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .fillMaxHeight(0.8f)
                .background(
                    brush = fondo_resultado,
                    RoundedCornerShape(20.dp)
                ),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Text(
                modifier = Modifier
                    .padding(20.dp),
                        text = "Ultimo resultado",
                style = MaterialTheme.typography.h5,
                color = white)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(white, RoundedCornerShape(5.dp))
                    .height(200.dp)
            ){
                Row(){
                    Box (
                        modifier = Modifier.fillMaxWidth(0.5f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(20.dp,0.dp,0.dp,0.dp),
                            text = "Tiempo: ",
                            style = MaterialTheme.typography.h6,
                            color = gray)
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        contentAlignment = Alignment.CenterStart
                    ){
                        Text(
                            modifier = Modifier
                                .padding(20.dp, 0.dp, 0.dp, 0.dp),
                            text = "${if(tiempo.minutos>9){
                                tiempo.minutos
                            }
                            else{
                                "0" + tiempo.minutos
                            }
                            } : ${if(tiempo.segundos>9){
                                tiempo.segundos
                            }
                            else{
                                "0" + tiempo.segundos
                            }
                            }",
                            style = MaterialTheme.typography.h6,
                            color = gray
                        )
                    }
                }
                Divider()
                Row()
                {
                    Box (
                        modifier = Modifier.fillMaxWidth(0.5f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(20.dp,0.dp,0.dp,0.dp),
                            text = "Compresiones: ",
                            style = MaterialTheme.typography.h6,
                            color = gray)
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        contentAlignment = Alignment.CenterStart
                    ){
                        Text(
                            modifier = Modifier
                                .padding(20.dp, 0.dp, 0.dp, 0.dp),
                            text = "120",
                            style = MaterialTheme.typography.h6,
                            color = gray
                        )
                    }
                }
                //Spacer(modifier = Modifier.height(20.dp))
                Divider()
                Row() {
                    Box (
                        modifier = Modifier.fillMaxWidth(0.5f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(20.dp,0.dp,0.dp,0.dp),
                            text = "Distancia: ",
                            style = MaterialTheme.typography.h6,
                            color = gray)
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        contentAlignment = Alignment.CenterStart
                    ){
                        Text(
                            modifier = Modifier
                                .padding(20.dp, 0.dp, 0.dp, 0.dp),
                            text = "5 cm",
                            style = MaterialTheme.typography.h6,
                            color = gray
                        )
                    }
                }
                //Spacer(modifier = Modifier.height(20.dp))
                Divider()
                Row() {
                    Box (
                        modifier = Modifier.fillMaxWidth(0.5f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(20.dp,0.dp,0.dp,0.dp),
                            text = "Posicion: ",
                            style = MaterialTheme.typography.h6,
                            color = gray)
                    }
                    Box(
                        modifier = Modifier.fillMaxWidth(0.5f),
                        contentAlignment = Alignment.CenterStart
                    ){
                        Text(
                            modifier = Modifier
                                .padding(20.dp, 0.dp, 0.dp, 0.dp),
                            text = "ok",
                            style = MaterialTheme.typography.h6,
                            color = gray
                        )
                    }
                }
                Divider()
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Aprobado",
                style = MaterialTheme.typography.h4,
                color = ligtgreen,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }

}




