package com.itis.multi_calculator.si_converter

import java.util.Locale

object SiConverterLogic {

    private const val RATE_KM = 1000.0
    private const val RATE_MI = 1609.344
    private const val RATE_LB = 0.45359237

    fun convert(valueStr: String, fromUnit: String, toUnit: String): String {
        val value = valueStr.trim().replace(",", ".").toDoubleOrNull()
            ?: return "Ошибка"

        if (fromUnit == toUnit) {
            return formatResult(value)
        }

        val lengthRates = mapOf("m" to 1.0, "km" to RATE_KM, "mi" to RATE_MI)
        val massRates = mapOf("kg" to 1.0, "lb" to RATE_LB)

        return when {
            lengthRates.containsKey(fromUnit) && lengthRates.containsKey(toUnit) -> {
                val valueInMeters = value * (lengthRates[fromUnit] ?: 1.0)
                val finalValue = valueInMeters / (lengthRates[toUnit] ?: 1.0)
                formatResult(finalValue)
            }
            massRates.containsKey(fromUnit) && massRates.containsKey(toUnit) -> {
                val valueInKg = value * (massRates[fromUnit] ?: 1.0)
                val finalValue = valueInKg / (massRates[toUnit] ?: 1.0)
                formatResult(finalValue)
            }
            else -> ""
        }
    }

    private fun formatResult(value: Double): String {
        return String.format(Locale.US, "%.4f", value)
            .dropLastWhile { it == '0' }
            .dropLastWhile { it == '.' }
    }
}
