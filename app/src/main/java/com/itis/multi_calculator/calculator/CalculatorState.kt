package com.itis.multi_calculator.calculator

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.itis.multi_calculator.history.HistoryManager

class CalculatorState {
    var numberA by mutableStateOf("")
    var numberB by mutableStateOf("")
    var resultText by mutableStateOf("")
    var selectedOperation by mutableStateOf("+")
    var expanded by mutableStateOf(false)

    val operations = listOf("+", "-", "*", "/")

    fun onOperationSelected(operation: String) {
        selectedOperation = operation
        expanded = false
        resultText = ""
    }

    fun onCalculate() {
        resultText = calculate(numberA, numberB, selectedOperation)
        if (resultText.isNotEmpty() && !resultText.startsWith("Error")) {
            HistoryManager.addHistoryItem("$numberA $selectedOperation $numberB", resultText)
        }
    }
}

@Composable
fun rememberCalculatorState(): CalculatorState {
    return remember { CalculatorState() }
}