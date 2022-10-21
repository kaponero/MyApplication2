package com.example.myapplication.presentation.ScreenElements

import android.content.Context

class Prefs(val context:Context) {

    val SHARED_NAME = "RCPdtb"
    val SHARED_MINUTOS = "minutos"
    val SHARED_SEGUNDOS = "segundos"

    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun saveTiempo(min:Int, seg:Int){
        storage.edit().putInt(SHARED_MINUTOS,min).apply()
        storage.edit().putInt(SHARED_SEGUNDOS,seg).apply()
    }

    fun getSegundos(): Int {
         val dato = storage.getInt(SHARED_SEGUNDOS,99)
        return dato
    }

    fun getMinutos(): Int {
        val dato = storage.getInt(SHARED_MINUTOS,99)
        return dato
    }

}

var actualizar_datos = true