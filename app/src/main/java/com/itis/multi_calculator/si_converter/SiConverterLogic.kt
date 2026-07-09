package com.itis.multi_calculator.si_converter

import java.util.Locale

object SiConverterLogic {

    // Коэффициенты перевода в базовые единицы (в метры для длины, в кг для массы)
    private val lengthRates = mapOf(
        "Метры (м)" to 1.0,
        "Километры (км)" to 1000.0,
        "Мили (ми)" to 1609.344
    )

    private val massRates = mapOf(
        "Килограммы (кг)" to 1.0,
        "Фунты (лб)" to 0.45359237
    )

    /**
     * Главный метод конвертации. На вход идут строка-число, исходная единица и целевая.
     */
    fun convert(valueStr: String, fromUnit: String, toUnit: String): String {
        // Проверяем ввод на корректность и заменяем запятые на точки
        val value = valueStr.trim().replace(",", ".").toDoubleOrNull()
            ?: return "Ошибка ввода"

        // Если переводим из одной и той же единицы в неё же
        if (fromUnit == toUnit) {
            return formatResult(value)
        }

        return when {
            // Если обе единицы — это меры длины
            lengthRates.containsKey(fromUnit) && lengthRates.containsKey(toUnit) -> {
                val valueInMeters = value * (lengthRates[fromUnit] ?: 1.0)
                val finalValue = valueInMeters / (lengthRates[toUnit] ?: 1.0)
                formatResult(finalValue)
            }
            // Если обе единицы — это меры массы
            massRates.containsKey(fromUnit) && massRates.containsKey(toUnit) -> {
                val valueInKg = value * (massRates[fromUnit] ?: 1.0)
                val finalValue = valueInKg / (massRates[toUnit] ?: 1.0)
                formatResult(finalValue)
            }
            // Если пользователь пытается скрестить метры с килограммами
            else -> "Несовместимые типы"
        }
    }

    // Убираем длинные хвосты после запятой для красоты
    private fun formatResult(value: Double): String {
        return String.format(Locale.US, "%.4f", value)
            .dropLastWhile { it == '0' }
            .dropLastWhile { it == '.' }
    }
}
