package com.example.myapplication.data

import com.example.myapplication.util.Resource
import kotlinx.coroutines.flow.MutableSharedFlow

interface FrequencyReceiveManager {

    val data: MutableSharedFlow<Resource<FrequencyResult>>

    fun reconnect()

    fun disconnect()

    fun startReceiving()

    fun closeConnection()

}