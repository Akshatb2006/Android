package com.example.addcal.viewmodel

import androidx.lifecycle.ViewModel
import com.example.addcal.model.CalculatorData

class CalculateViewModel:ViewModel() {
    fun calculateSum(num1: Float, num2: Float): CalculatorData{
        val sum = num1 + num2
        return CalculatorData(num1, num2, sum)

    }
    fun calculateSub(num1: Float, num2: Float): CalculatorData{
        val sub = num1 - num2
        return CalculatorData(num1, num2, sub)
    }
    fun calculateMul(num1: Float, num2: Float): CalculatorData{
        val mul = num1 * num2
        return CalculatorData(num1, num2, mul)
    }
    fun calculateDiv(num1: Float, num2: Float): CalculatorData{
        val div = num1 / num2
        return CalculatorData(num1, num2, div)
    }
}