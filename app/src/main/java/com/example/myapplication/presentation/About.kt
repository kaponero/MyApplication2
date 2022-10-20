package com.example.myapplication.presentation

import android.bluetooth.BluetoothAdapter
import android.widget.Chronometer
import android.widget.TextView
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import com.example.myapplication.data.ConnectionState
import com.example.myapplication.presentation.ScreenElements.rememberCountdownTimerState
import com.example.myapplication.presentation.ScreenElements.reset_tiempo
import com.example.myapplication.presentation.ScreenElements.tiempo
import com.example.myapplication.presentation.permissions.PermissionUtils
import com.example.myapplication.presentation.permissions.SystemBroadcastReceiver
import com.example.myapplication.ui.theme.Bordo
import com.example.myapplication.ui.theme.darkBlue
import com.example.myapplication.ui.theme.darkGray
import com.example.myapplication.ui.theme.gray
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun About(navController: NavController,
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

    reset_tiempo()

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
                    gray,
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
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    rememberCountdownTimerState()
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
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = "CPM: ${viewModel.frequency}",
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        text = "Profundidad: ${viewModel.compresion}",
                        style = MaterialTheme.typography.h6
                    )
                    val posision_manos: String
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
                    Text("Iniciar nuevamente")
                }
            }
        }
    }
}

