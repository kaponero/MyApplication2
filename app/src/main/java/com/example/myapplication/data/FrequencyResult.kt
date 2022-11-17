package com.example.myapplication.data

data class FrequencyResult(
    val frequency:Int,
    val compression:Int,
    val position:Int,
    val refresh:Int,
    val connectionState: ConnectionState
)
