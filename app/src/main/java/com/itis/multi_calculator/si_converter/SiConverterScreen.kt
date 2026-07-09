package com.itis.multi_calculator.si_converter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SiConverterScreen() {
    // Состояния для хранения введенных данных и результатов
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("0.0") }

    // Списки доступных единиц измерения
    val units = listOf("Метры (м)", "Километры (км)", "Мили (ми)", "Килограммы (кг)", "Фунты (лб)")

    // Исправлено: теперь храним ОДНУ строку, а не весь список
    var inputUnit by remember { mutableStateOf(units[0]) }
    var outputUnit by remember { mutableStateOf(units[0]) }

    // Состояния для раскрытия выпадающих списков
    var inputExpanded by remember { mutableStateOf(false) }
    var outputExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Конвертер в систему СИ",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Поле ввода числа
        OutlinedTextField(
            value = inputValue,
            onValueChange = { inputValue = it },
            label = { Text("Введите значение") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Выбор исходной единицы (Из чего переводим)
        ExposedDropdownMenuBox(
            expanded = inputExpanded,
            onExpandedChange = { inputExpanded = !inputExpanded }
        ) {
            OutlinedTextField(
                value = inputUnit,
                onValueChange = {},
                readOnly = true,
                label = { Text("Из какой единицы") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = inputExpanded) },
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = inputExpanded,
                onDismissRequest = { inputExpanded = false }
            ) {
                units.forEach { unit ->
                    DropdownMenuItem(
                        text = { Text(unit) },
                        onClick = {
                            inputUnit = unit
                            inputExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Выбор целевой единицы (В какую переводим)
        ExposedDropdownMenuBox(
            expanded = outputExpanded,
            onExpandedChange = { outputExpanded = !outputExpanded }
        ) {
            OutlinedTextField(
                value = outputUnit,
                onValueChange = {},
                readOnly = true,
                label = { Text("В какую единицу") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = outputExpanded) },
                modifier = Modifier.menuAnchor(MenuAnchorType.PrimaryNotEditable).fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = outputExpanded,
                onDismissRequest = { outputExpanded = false }
            ) {
                units.forEach { unit ->
                    DropdownMenuItem(
                        text = { Text(unit) },
                        onClick = {
                            outputUnit = unit
                            outputExpanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Кнопка для запуска расчета (логика расчета будет на следующем этапе)
        Button(
            onClick = {
                outputValue = inputValue.toDoubleOrNull()?.toString() ?: "Ошибка ввода"
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Конвертировать", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Блок вывода результата
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = "Результат:", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    text = "$outputValue $outputUnit",
                    fontSize = 22.sp,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}