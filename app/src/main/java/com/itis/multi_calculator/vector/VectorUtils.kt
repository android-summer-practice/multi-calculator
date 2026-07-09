package com.itis.multi_calculator.vector

import android.content.Context
import com.itis.multi_calculator.R
import kotlin.math.pow
import kotlin.math.sqrt

private val SEPARATORS = listOf(",", ";", " ")

fun parseVector(text: String, context: Context): Vector2D {
    val trimmed = text.trim()

    if (trimmed.isEmpty()) {
        throw IllegalArgumentException(context.getString(R.string.vector_error_empty))
    }

    val invalidChars = trimmed.filter {
        !it.isDigit() && it != '.' && it != '-' && it != ',' && it != ';' && it != ' ' && it != '(' && it != ')'
    }.toSet()

    if (invalidChars.isNotEmpty()) {
        val charsStr = invalidChars.joinToString(", ") { "'$it'" }
        throw IllegalArgumentException(context.getString(R.string.vector_error_invalid_chars, charsStr))
    }

    val cleanText = trimmed.replace("(", "").replace(")", "")
    val allParts = cleanText.split(",", ";", " ").filter { it.isNotEmpty() }

    if (allParts.size != 2) {
        throw IllegalArgumentException(context.getString(R.string.vector_error_count, allParts.size))
    }

    val usedSeparators = SEPARATORS.filter { trimmed.contains(it) }

    if (usedSeparators.isEmpty()) {
        throw IllegalArgumentException(context.getString(R.string.vector_error_no_separator))
    }

    val x = allParts[0].toDoubleOrNull()
    val y = allParts[1].toDoubleOrNull()

    if (x == null || y == null) {
        throw IllegalArgumentException(context.getString(R.string.vector_error_invalid_numbers))
    }

    return Vector2D(x, y)
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