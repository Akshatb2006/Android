package com.example.counter

import androidx.lifecycle.ViewModel

class MyViewModel:ViewModel() {
    private var counter =0

    fun getInitialCounter():Int{
        return counter
    }
    fun incrementCounter():Int{
        return ++counter
    }
    fun decreementCounter():Int{
        if(counter>0) {
            return --counter
        }
        return counter

    }
}