package com.itis.multi_calculator.calculator

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

sealed class Screen(val route: String) {
    object Calculator : Screen("calculator_screen")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(navController: NavController) {
    var numberA by remember { mutableStateOf("") }
    var numberB by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("") }

    val operations = listOf("+", "-", "*", "/")
    var expanded by remember { mutableStateOf(false) }
    var selectedOperation by remember { mutableStateOf(operations[0]) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(end = 16.dp),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Calculator",
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Normal,
                            color = Color.Black
                        )
                    }
                },
                navigationIcon = {
                    OutlinedButton(
                        onClick = { },
                        shape = CircleShape,
                        modifier = Modifier.padding(start = 16.dp).size(60.dp),
                        contentPadding = PaddingValues(0.dp),
                        border = BorderStroke(1.dp, Color.Gray)
                    ) {
                        Text("Меню", color = Color.Black, fontSize = 14.sp)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))

            OutlinedTextField(
                value = numberA,
                onValueChange = { numberA = it },
                placeholder = { Text("Number A", fontSize = 20.sp, color = Color.Gray) },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                shape = CircleShape,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(20.dp))

            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                modifier = Modifier.width(180.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                    readOnly = true,
                    value = selectedOperation,
                    onValueChange = {},
                    shape = CircleShape,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Gray,
                        unfocusedBorderColor = Color.LightGray
                    )
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    operations.forEach { operation ->
                        DropdownMenuItem(
                            text = { Text(operation, fontSize = 18.sp) },
                            onClick = {
                                selectedOperation = operation
                                expanded = false
                                resultText = ""
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            OutlinedTextField(
                value = numberB,
                onValueChange = { numberB = it },
                placeholder = { Text("Number B", fontSize = 20.sp, color = Color.Gray) },
                maxLines = 1,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                shape = CircleShape,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Gray,
                    unfocusedBorderColor = Color.LightGray
                )
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = {
                    val a = numberA.toDoubleOrNull()
                    val b = numberB.toDoubleOrNull()
                    if (a != null && b != null) {
                        val res = when (selectedOperation) {
                            "+" -> a + b
                            "-" -> a - b
                            "*" -> a * b
                            "/" -> if (b != 0.0) a / b else Double.NaN
                            else -> 0.0
                        }
                        resultText = if (res.isNaN()) "Деление на ноль!" else "Result: $res"
                    } else {
                        resultText = "Ошибка ввода"
                    }
                },
                shape = RoundedCornerShape(40.dp),
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .height(100.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                border = BorderStroke(1.dp, Color.LightGray)
            ) {
                Text(text = "Result", fontSize = 26.sp, fontWeight = FontWeight.Normal)
            }

            if (resultText.isNotEmpty()) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = resultText,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Calculator.route
    ) {
        composable(Screen.Calculator.route) { CalculatorScreen(navController) }
    }
}