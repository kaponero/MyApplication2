package com.example.myapplication.presentation


import android.bluetooth.BluetoothAdapter
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.example.myapplication.MainActivity.Companion.prefs
import com.example.myapplication.R
import com.example.myapplication.data.ConnectionState
import com.example.myapplication.presentation.ScreenElements.*
import com.example.myapplication.presentation.permissions.PermissionUtils
import com.example.myapplication.presentation.permissions.SystemBroadcastReceiver
import com.example.myapplication.ui.theme.*
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FrequencyScreen(navController: NavController,
                    onBluetoothStateChanged:()->Unit,
                    viewModel: RCPViewModel = hiltViewModel()
){
    //MainScreen(navController,onBluetoothStateChanged)

    SystemBroadcastReceiver(systemAction = BluetoothAdapter.ACTION_STATE_CHANGED){ bluetoothState ->
        val action = bluetoothState?.action ?:return@SystemBroadcastReceiver
        if (action == BluetoothAdapter.ACTION_STATE_CHANGED){
            onBluetoothStateChanged()
        }
    }

    val permissionState = rememberMultiplePermissionsState(permissions = PermissionUtils.permissions)
    val lifecycleOwner = LocalLifecycleOwner.current
    val bleConnectionState = viewModel.connectionState

    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver{_,event ->
                if(event == Lifecycle.Event.ON_START){
                    permissionState.launchMultiplePermissionRequest()
                    if(permissionState.allPermissionsGranted && bleConnectionState == ConnectionState.Disconnected){
                        viewModel.reconnect()
                    }
                }
                if(event == Lifecycle.Event.ON_STOP){
                    if (bleConnectionState == ConnectionState.Connected){
                        viewModel.disconnect()
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )

    LaunchedEffect(key1 = permissionState.allPermissionsGranted){
        if(permissionState.allPermissionsGranted){
            if(bleConnectionState == ConnectionState.Uninitialized){
                viewModel.initializeConnection()
            }
        }
    }


    LaunchedEffect(false){
        reset_tiempo()
        reset_scores()
    }

    //interfaz grafica
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                //.height(580.dp)
                //.aspectRatio(1f)
                .background(
                    darkGray,
                    //RoundedCornerShape(10.dp)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if(bleConnectionState == ConnectionState.CurrentlyInitializing){
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    CircularProgressIndicator(
                        color = Bordo
                    )
                    if(viewModel.initializingMessage != null){
                        Text(
                            text = viewModel.initializingMessage!!
                        )
                    }
                }
            }else if(!permissionState.allPermissionsGranted){
                Text(
                    text = "Vaya a la configuración de la aplicación y permita los permisos que faltan.",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }else if(viewModel.errorMessage != null){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = viewModel.errorMessage!!
                    )
                    Button(
                        onClick = {
                            if(permissionState.allPermissionsGranted){
                                viewModel.initializeConnection()
                            }
                        }
                    ) {
                        Text(
                            "Intentar nuevamente"
                        )
                    }
                }
            }else if(bleConnectionState == ConnectionState.Connected){
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(0.dp, 15.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ){
                    rememberCountdownTimerState(viewModel.frequency,viewModel.compresion,viewModel.position)
                    Text(
                        text = "${if(tiempo.minutos>9){
                            tiempo.minutos
                        }
                        else{
                            "0" +tiempo.minutos
                        }
                        } : ${if(tiempo.segundos>9){
                            tiempo.segundos
                        }
                        else{
                            "0" +tiempo.segundos
                        }
                        }" ,
                        style = MaterialTheme.typography.h6,
                        color = Color.White,
                        fontSize = 40.sp
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ){
                        Column(
                            modifier = Modifier
                                .weight(2f),
                            horizontalAlignment = Alignment.CenterHorizontally,

                        ) {
                        Spacer(modifier = Modifier.height(30.dp))
                            ArcIndicator(
                                modifier = Modifier
                                    .size(250.dp),
                                initialValue = viewModel.frequency,
                                primaryColor = white,
                                secondaryColor = Purple200,
                                terciaryColor = Purple700,
                                circleRadius = 230f
                                )
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            BarIndicator(
                                modifier = Modifier
                                    .size(250.dp),
                                initialValue = viewModel.compresion,
                                primaryColor = white,
                                secondaryColor = Purple200 ,
                                terciaryColor = Purple700,
                                circleRadius = 230f
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    val posision_manos: String
                    if(viewModel.position == 1 ){
                        Image(
                            painter = painterResource(id = R.drawable.hand_ok),
                            contentDescription = "mano ok",
                            modifier = Modifier
                                .size(80.dp)
                                .border(
                                    BorderStroke(4.dp, white),
                                    CircleShape
                                )
                                .padding(10.dp)
                        )
                    }else{
                        Image(
                            painter = painterResource(id = R.drawable.hand_no),
                            contentDescription = "mano no",
                            modifier = Modifier
                                .size(80.dp)
                                .border(
                                    BorderStroke(4.dp, white),
                                    CircleShape
                                )
                                .padding(10.dp)
                        )}
                    Spacer(modifier = Modifier.height(80.dp))
                    Row() {
                        Button(onClick = {
                            reset_tiempo()
                            reset_scores()
                        }) {
                            Text("Reiniciar")
                        }
                        Spacer(modifier = Modifier.width(20.dp))
                        Button(onClick = {
                            if (bleConnectionState == ConnectionState.Connected){
                                viewModel.disconnect()
                            }
                            navController.navigate(Screen.StartScreen.route) {
                                //popUpTo(Screen.StartScreen.route)
                            }
                        }) {
                            Text("Terminar")
                        }
                    }
                    if (tiempo.minutos*60+tiempo.segundos==600){
                        navController.navigate(Screen.StartScreen.route)
                    }

                }
            }else if(bleConnectionState == ConnectionState.Disconnected){
                Button(onClick = {
                    viewModel.initializeConnection()
                }) {
                    Text("Iniciar nuevamente")
                }
            }
        }
    }
}






/*
import android.bluetooth.BluetoothAdapter
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.example.myapplication.data.ConnectionState
import com.example.myapplication.presentation.permissions.PermissionUtils
import com.example.myapplication.presentation.permissions.SystemBroadcastReceiver
import com.example.myapplication.ui.theme.Bordo
import com.example.myapplication.ui.theme.darkGray
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FrequencyScreen(navController: NavController,
    onBluetoothStateChanged:()->Unit,
    viewModel: RCPViewModel = hiltViewModel()
){
    SystemBroadcastReceiver(systemAction = BluetoothAdapter.ACTION_STATE_CHANGED){ bluetoothState ->
        val action = bluetoothState?.action ?:return@SystemBroadcastReceiver
        if (action == BluetoothAdapter.ACTION_STATE_CHANGED){
            onBluetoothStateChanged()
        }
    }

    val permissionState = rememberMultiplePermissionsState(permissions = PermissionUtils.permissions)
    val lifecycleOwner = LocalLifecycleOwner.current
    val bleConnectionState = viewModel.connectionState

    DisposableEffect(
        key1 = lifecycleOwner,
        effect = {
            val observer = LifecycleEventObserver{_,event ->
                if(event == Lifecycle.Event.ON_START){
                    permissionState.launchMultiplePermissionRequest()
                    if(permissionState.allPermissionsGranted && bleConnectionState == ConnectionState.Disconnected){
                        viewModel.reconnect()
                    }
                }
                if(event == Lifecycle.Event.ON_STOP){
                    if (bleConnectionState == ConnectionState.Connected){
                        viewModel.disconnect()
                    }
                }
            }
            lifecycleOwner.lifecycle.addObserver(observer)

            onDispose {
                lifecycleOwner.lifecycle.removeObserver(observer)
            }
        }
    )

    LaunchedEffect(key1 = permissionState.allPermissionsGranted){
        if(permissionState.allPermissionsGranted){
            if(bleConnectionState == ConnectionState.Uninitialized){
                viewModel.initializeConnection()
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ){
        Column(
            modifier = Modifier
                .fillMaxWidth(0.6f)
                .aspectRatio(1f)
                .border(
                    BorderStroke(
                        5.dp, darkGray
                    ),
                    RoundedCornerShape(10.dp)
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            if(bleConnectionState == ConnectionState.CurrentlyInitializing){
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    CircularProgressIndicator(
                        color = Bordo
                    )
                    if(viewModel.initializingMessage != null){
                        Text(
                            text = viewModel.initializingMessage!!
                        )
                    }
                }
            }else if(!permissionState.allPermissionsGranted){
                Text(
                    text = "Go to the app setting and allow the missing permissions.",
                    style = MaterialTheme.typography.body2,
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center
                )
            }else if(viewModel.errorMessage != null){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = viewModel.errorMessage!!
                    )
                    Button(
                        onClick = {
                            if(permissionState.allPermissionsGranted){
                                viewModel.initializeConnection()
                            }
                        }
                    ) {
                        Text(
                            "Try again"
                        )
                    }
                }
            }else if(bleConnectionState == ConnectionState.Connected){
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text(
                        text = "CPM: ${viewModel.frequency}",
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = "Profundidad: ${viewModel.compresion}",
                        style = MaterialTheme.typography.h6
                    )
                    var posision_manos: String
                    if(viewModel.position == 0 ){
                        posision_manos = "No"
                    }else
                        posision_manos = "Ok"
                    Text(
                        text = "Posicion: " + posision_manos,
                        style = MaterialTheme.typography.h6
                    )
                }
            }else if(bleConnectionState == ConnectionState.Disconnected){
                Button(onClick = {
                    viewModel.initializeConnection()
                }) {
                    Text("Initialize again")
                }
            }
        }
    }

    //menu(navController)
}

*/