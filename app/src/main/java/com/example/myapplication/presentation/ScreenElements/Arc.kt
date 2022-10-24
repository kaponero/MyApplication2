package com.example.myapplication.presentation.ScreenElements

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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.presentation.CircularIndicator
import com.example.myapplication.ui.theme.Purple200
import com.example.myapplication.ui.theme.Purple500
import com.example.myapplication.ui.theme.green
import com.example.myapplication.ui.theme.white

@Composable
fun ArcIndicator(
    modifier: Modifier = Modifier,
    initialValue: Int,
    primaryColor: Color,
    secondaryColor: Color,
    minValue:Int = 0,
    maxValue:Int = 100,
    circleRadius:Float,

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
                color = secondaryColor,
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
                color = primaryColor,
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
                color = secondaryColor,
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


            rotate(degrees = initialValue.toFloat(), circleCenter){
                drawPath(path,
                    color=primaryColor,
                )
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
        initialValue = 45,
        primaryColor = white,
        secondaryColor = Purple200 ,
        circleRadius = 230f
    )
}