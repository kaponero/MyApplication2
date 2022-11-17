package com.example.myapplication.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.ConnectionState
import com.example.myapplication.data.FrequencyReceiveManager
import com.example.myapplication.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RCPViewModel @Inject constructor(
    private val frequencyReceiveManager: FrequencyReceiveManager
): ViewModel(){

    var initializingMessage by mutableStateOf<String?>(null)
        private set

    var errorMessage by mutableStateOf<String?>(null)
        private set

    var frequency by mutableStateOf(0)
        private set

    var compresion by mutableStateOf(0)
        private set

    var position by mutableStateOf(0)
        private set

    var refresco by mutableStateOf(0)
        private set

    var connectionState by mutableStateOf<ConnectionState>(ConnectionState.Uninitialized)

    private fun subscribeToChanges(){
        viewModelScope.launch {
            frequencyReceiveManager.data.collect{ result ->
                when(result){
                    is Resource.Success -> {
                        connectionState = result.data.connectionState
                        frequency = result.data.frequency
                        compresion = result.data.compression
                        position = result.data.position
                        refresco = result.data.refresh
                    }

                    is Resource.Loading -> {
                        initializingMessage = result.message
                        connectionState = ConnectionState.CurrentlyInitializing
                    }

                    is Resource.Error -> {
                        errorMessage = result.errorMessage
                        connectionState = ConnectionState.Uninitialized
                    }
                }
            }
        }
    }

    fun disconnect(){
        frequencyReceiveManager.disconnect()
    }

    fun reconnect(){
        frequencyReceiveManager.reconnect()
    }

    fun initializeConnection(){
        errorMessage = null
        subscribeToChanges()
        frequencyReceiveManager.startReceiving()
    }

    override fun onCleared() {
        super.onCleared()
        frequencyReceiveManager.closeConnection()
    }
}