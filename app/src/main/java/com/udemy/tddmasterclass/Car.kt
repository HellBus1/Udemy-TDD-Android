package com.udemy.tddmasterclass

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Car(
    val engine: Engine,
    var fuel: Double,
) {
    fun turnOn() {
        fuel -= 0.5
        CoroutineScope(Dispatchers.Main).launch {
            engine.turnOn().collect {
                Log.d("COURSE", "collected engine temperature: $it")
            }
        }
    }
}