package com.itis.multi_calculator.vector

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun VectorsScreen(modifier: Modifier = Modifier) {
    var vectorAText by remember { mutableStateOf("") }
    var vectorBText by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("Результат отобразится здесь") }

    fun performOperation(operation: (Vector2D, Vector2D) -> Any) {
        val a = parseVector(vectorAText)
        val b = parseVector(vectorBText)
        if (a != null && b != null) {
            val result = operation(a, b)
            resultText = "Результат: $result"
        } else {
            resultText = "Ошибка: проверьте формат (x,y)"
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = {  },
                shape = RoundedCornerShape(50)
            ) {
                Text("Меню")
            }
            Text(
                text = "Vectors",
                fontSize = 24.sp,
                modifier = Modifier.padding(end = 16.dp)
            )
            Spacer(modifier = Modifier.width(64.dp))
        }

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = vectorAText,
            onValueChange = { vectorAText = it },
            label = { Text("Vector A (x,y)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = vectorBText,
            onValueChange = { vectorBText = it },
            label = { Text("Vector B (x,y)") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { performOperation { a, b -> sum(a, b) } },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Text("Сумма")
            }
            Button(
                onClick = { performOperation { a, b -> subtraction(a, b) } },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ) {
                Text("Вычитание")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { performOperation { a, b -> product(a, b) } },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            ) {
                Text("Умножение")
            }
            Button(
                onClick = { performOperation { a, b -> distance(a, b) } },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier.weight(1f).padding(start = 8.dp)
            ) {
                Text("Расстояние")
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = resultText,
            fontSize = 18.sp
        )
    }
}
