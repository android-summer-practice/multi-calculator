package com.itis.multi_calculator.si_converter

import java.util.Locale

object SiConverterLogic {

    private val lengthRates = mapOf(
        "Метры (м)" to 1.0,
        "Километры (км)" to 1000.0,
        "Мили (ми)" to 1609.344
    )

    private val massRates = mapOf(
        "Килограммы (кг)" to 1.0,
        "Фунты (лб)" to 0.45359237
    )

    fun convert(valueStr: String, fromUnit: String, toUnit: String): String {
        val value = valueStr.trim().replace(",", ".").toDoubleOrNull()
            ?: return "Ошибка ввода"

        if (fromUnit == toUnit) {
            return formatResult(value)
        }

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
            else -> "Несовместимые типы"
        }
    }

    private fun formatResult(value: Double): String {
        return String.format(Locale.US, "%.4f", value)
            .dropLastWhile { it == '0' }
            .dropLastWhile { it == '.' }
    }
}
