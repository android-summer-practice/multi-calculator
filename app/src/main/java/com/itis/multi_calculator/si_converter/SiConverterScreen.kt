package com.itis.multi_calculator.si_converter

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
    var inputValue by remember { mutableStateOf("") }
    var outputValue by remember { mutableStateOf("0.0") }

    val lengthUnits = listOf("Метры (м)", "Километры (км)", "Мили (ми)")
    val massUnits = listOf("Килограммы (кг)", "Фунты (лб)")
    val allUnits = lengthUnits + massUnits

    var inputUnit by remember { mutableStateOf(allUnits[0]) }
    var outputUnit by remember { mutableStateOf(allUnits[0]) }

    var inputExpanded by remember { mutableStateOf(false) }
    var outputExpanded by remember { mutableStateOf(false) }

    val availableOutputUnits = if (inputUnit in lengthUnits) lengthUnits else massUnits

    LaunchedEffect(inputUnit) {
        if (outputUnit !in availableOutputUnits) {
            outputUnit = availableOutputUnits[0]
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Конвертер в систему СИ",
            fontSize = 26.sp,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        OutlinedTextField(
            value = inputValue,
            onValueChange = { inputValue = it },
            label = { Text("Введите значение") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            trailingIcon = {
                if (inputValue.isNotEmpty()) {
                    IconButton(onClick = { inputValue = "" }) {
                        Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

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
                allUnits.forEach { unit ->
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
                availableOutputUnits.forEach { unit ->
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

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                outputValue = SiConverterLogic.convert(inputValue, inputUnit, outputUnit)
            },
            modifier = Modifier.fillMaxWidth().height(54.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Конвертировать", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(32.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Text(
                    text = "Результат:",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
                Text(
                    text = "$outputValue ${outputUnit.substringAfter(" ")}",
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(top = 8.dp),
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}
