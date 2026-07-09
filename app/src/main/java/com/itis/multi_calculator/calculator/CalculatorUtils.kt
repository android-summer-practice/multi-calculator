package com.itis.multi_calculator.calculator

fun calculate(numA: String, numB: String, operation: String): String {
    val a = numA.toDoubleOrNull()
    val b = numB.toDoubleOrNull()

    if (a == null || b == null) return "Ошибка"

    return when (operation) {
        "+" -> (a + b).toString()
        "-" -> (a - b).toString()
        "*" -> (a * b).toString()
        "/" -> if (b != 0.0) (a / b).toString() else "Деление на ноль"
        else -> ""
    }
}