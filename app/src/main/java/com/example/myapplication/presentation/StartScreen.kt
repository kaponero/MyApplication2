package com.example.myapplication.presentation
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.vector.VectorProperty
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.MainActivity.Companion.prefs
import com.example.myapplication.R
import com.example.myapplication.presentation.ScreenElements.scores
import com.example.myapplication.presentation.ScreenElements.tiempo
import com.example.myapplication.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlin.math.roundToInt


@Composable
fun StartScreen(navController: NavController,onBluetoothStateChanged:()->Unit){

    MainScreen(navController,onBluetoothStateChanged)

    LaunchedEffect(false){

        prefs.saveTiempo(tiempo.minutos,tiempo.segundos)
        prefs.saveScores(scores.cpm,scores.desplaza,scores.posicion)
    }

    val puntaje = ((scores.cpm).toFloat()*10/60 *.5+ (scores.desplaza).toFloat()/180*.2+ (scores.posicion).toFloat()*10/60*.3).toInt()

    Column (modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Purple500)
                .weight(1f)
        ){
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,

            ){
                Text(
                    text = "Puntuación",
                    fontSize = 25.sp,
                    modifier = Modifier
                        .padding(15.dp),)

                CircularIndicator(
                    modifier = Modifier
                        .size(150.dp),
                    initialValue = puntaje,
                    primaryColor = white,
                    secondaryColor = Purple200 ,
                    circleRadius = 200f
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        )
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.8f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(220.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
            elevation = 10.dp,

        ){
            Row(
                modifier = Modifier
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Column() {
                    Text(text = "Tiempo.",
                        textAlign = TextAlign.Center,
                        color= gray,
                        fontSize = 10.sp)
                    Image(
                        painter = painterResource(id = R.drawable.timelapse),
                        contentDescription = "tiempo de entrenamiento",
                        modifier = Modifier.padding(5.dp,5.dp)
                    )
                }
                Text(text = "${if(tiempo.minutos>9){
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
                } minutos",
                    textAlign = TextAlign.Center,
                    color= black,
                    fontSize = 20.sp
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        Card(modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
            elevation = 10.dp,
            ){
            Row(
                modifier = Modifier
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Comp. por Min.",
                        textAlign = TextAlign.Center,
                        color= gray,
                        fontSize = 10.sp)
                    Image(
                        painter = painterResource(id = R.drawable.lat_minute),
                        contentDescription = "compresiones por minuto",
                        modifier = Modifier.padding(5.dp,5.dp))
                    Text(text = "${((scores.cpm).toFloat()/60*10).roundToInt()}%",
                        textAlign = TextAlign.Center,
                        color= black,
                        )
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally)
                {
                    Text(text = "Posición",
                        textAlign = TextAlign.Center,
                        color= gray,
                        fontSize = 10.sp)
                    Image(
                        painter = painterResource(id = R.drawable.hand_pos),
                        contentDescription = "posicion de la mano",
                        modifier = Modifier.padding(5.dp,5.dp),
                    )
                    Text(text = "${((scores.posicion).toFloat()/60*10).roundToInt()}%",
                        textAlign = TextAlign.Center,
                        color= black)
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Desplazamiento",
                        textAlign = TextAlign.Center,
                        color= gray,
                        fontSize = 10.sp)
                    Image(
                        painter = painterResource(id = R.drawable.compresion),
                        contentDescription = "compresiones",
                        modifier = Modifier
                            .rotate(90f)
                            .padding(5.dp)
                    )
                    Text(text = "${((scores.desplaza).toFloat()/180).roundToInt()}%",
                        textAlign = TextAlign.Center,
                        color= black)
                }
            }
        }
    }
}

@Composable
fun CircularIndicator(
    modifier: Modifier = Modifier,
    initialValue: Int,
    primaryColor: Color,
    secondaryColor:Color,
    minValue:Int = 0,
    maxValue:Int = 100,
    circleRadius:Float,
    //onPositionChange:(Int)->Unit

){
    var circleCenter by remember {
        mutableStateOf(Offset.Zero)
    }

    var positionValue by remember {
        mutableStateOf(initialValue)
    }
    
    Box(modifier = modifier){
        Canvas(modifier = Modifier
            .fillMaxSize(),
            ){
            val width = size.width
            val height = size.height
            val circleThickness = width/25f
            circleCenter = Offset(x = width/2f, y = height/2f)

            drawCircle(
                brush = Brush.radialGradient(
                    listOf(
                        primaryColor.copy(0.45f),
                        secondaryColor.copy(0.15f)
                    )
                ),
                radius = circleRadius,
                center = circleCenter
            )
            drawCircle(
                style = Stroke(
                    width = circleThickness
                ),
                color = secondaryColor,
                radius = circleRadius,
                center = circleCenter
            )
            drawArc(
                color = primaryColor,
                startAngle = 90f,
                sweepAngle = (360f/maxValue) * positionValue.toFloat(),
                style = Stroke(
                    width = circleThickness,
                    cap = StrokeCap.Round
                ),
                useCenter = false,
                size = Size(
                    width = circleRadius * 2f,
                    height = circleRadius * 2f
                ),
                topLeft = Offset(
                    (width - circleRadius * 2f)/2f,
                    (height - circleRadius * 2f)/2f
                )
            )
            drawContext.canvas.nativeCanvas.apply {
                drawIntoCanvas {
                    drawText(
                        "$positionValue",
                        circleCenter.x,
                        circleCenter.y + 45.dp.toPx()/3f,
                        Paint().apply {
                            textSize = 38.sp.toPx()
                            textAlign = Paint.Align.CENTER
                            color = white.toArgb()
                            isFakeBoldText = true
                        }
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){


    CircularIndicator(
        modifier = Modifier
            .size(250.dp)
            .background(Purple500),
        initialValue = 70,
        primaryColor = white,
        secondaryColor = Purple200 ,
        circleRadius = 230f
    )
}

