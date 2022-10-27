package com.example.myapplication.presentation.ScreenElements

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.presentation.CircularIndicator
import com.example.myapplication.ui.theme.*

@Composable
fun ArcIndicator(
    modifier: Modifier = Modifier,
    initialValue: Int,
    primaryColor: Color,
    secondaryColor: Color,
    terciaryColor: Color,
    circleRadius:Float,


){

    var danger_colo: Color = secondaryColor
    var ok_colo: Color = secondaryColor

    var degrees_frec = 0
    if(initialValue<=0){
        degrees_frec = 0
    }else if (initialValue<100){
        degrees_frec = initialValue*44/100
        danger_colo = terciaryColor
    } else if (initialValue<121){
        degrees_frec = (initialValue - 100)*90/20 + 45
        ok_colo = hand_green
    }else if (initialValue<160){
        degrees_frec = 136/121*initialValue
        danger_colo = terciaryColor
    }else
        degrees_frec = 0



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


            drawArc(
                brush = Brush.radialGradient(
                    listOf(
                        primaryColor.copy(0.45f),
                        secondaryColor.copy(0.15f)
                    )),
                startAngle = 180f,
                sweepAngle = (180f),
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
            drawArc(
                color = danger_colo,
                startAngle = 180f,
                sweepAngle = (45f),
                style = Stroke(
                    width = circleThickness
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
            drawArc(
                color = ok_colo,
                startAngle = 225f,
                sweepAngle = (90f),
                style = Stroke(
                    width = circleThickness
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
            drawArc(
                color = danger_colo,
                startAngle = 315f,
                sweepAngle = (45f),
                style = Stroke(
                    width = circleThickness
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
            drawCircle(
                color = primaryColor,
                radius = circleRadius/20f,
                center = circleCenter
            )

            val path = Path().apply{
                moveTo(width/2f, height/2f - circleRadius/20f)
                lineTo(width/2f-circleRadius*.9f,height/2f)
                lineTo(width/2f ,height/2f+ circleRadius/20f)
            }


            rotate(degrees = degrees_frec.toFloat(), circleCenter){
                drawPath(path,
                    color=primaryColor,
                )
            }

            drawContext.canvas.nativeCanvas.apply {
                drawIntoCanvas {
                    drawText(
                        "${
                            if (initialValue<=0){
                                0
                            }else if(initialValue>=160){
                                160
                            }else
                                initialValue} cpm",
                        circleCenter.x,
                        circleCenter.y + 60.dp.toPx()/1f,
                        Paint().apply {
                            textSize = 25.sp.toPx()
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


    ArcIndicator(
        modifier = Modifier
            .size(250.dp)
            .background(Purple500),
        initialValue = 90,
        primaryColor = white,
        secondaryColor = Purple200 ,
        terciaryColor = Purple700,
        circleRadius = 230f
    )
}