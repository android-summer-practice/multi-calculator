package com.itis.multi_calculator.vector

import kotlin.math.pow
import kotlin.math.sqrt

fun parseVector(text: String): Vector2D? {
    val parts = text.trim().split(",", ";", " ")

    if (parts.size != 2) return null

    val x = parts[0].toDoubleOrNull()
    val y = parts[1].toDoubleOrNull()

    return if (x != null && y != null) {
        Vector2D(x, y)
    } else {
        null
    }
}

fun sum(a: Vector2D, b: Vector2D): Vector2D {
    return Vector2D(a.x + b.x, a.y + b.y)
}

fun subtraction(a: Vector2D, b: Vector2D): Vector2D {
    return Vector2D(a.x - b.x, a.y - b.y)
}

fun product(a: Vector2D, b: Vector2D): Double {
    return a.x * b.x + a.y * b.y
}

fun distance(a: Vector2D, b: Vector2D): Double {
    return sqrt((a.x - b.x).pow(2) + (a.y - b.y).pow(2))
}