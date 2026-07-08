package com.itis.multi_calculator.vector

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itis.multi_calculator.R

@Composable
fun VectorsScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    var vectorAText by remember { mutableStateOf("") }
    var vectorBText by remember { mutableStateOf("") }
    val defaultResult = stringResource(R.string.vector_default_result)
    var resultText by remember { mutableStateOf(defaultResult) }
    var isError by remember { mutableStateOf(false) }

    fun performOperation(operation: (Vector2D, Vector2D) -> Any) {
        try {
            val a = parseVector(vectorAText, context)
            val b = parseVector(vectorBText, context)
            val result = operation(a, b)
            resultText = context.getString(R.string.vector_result, result.toString())
            isError = false
        } catch (e: IllegalArgumentException) {
            resultText = "Ошибка: ${e.message}"
            isError = true
        } catch (e: Exception) {
            resultText = "Ошибка: ${e.message}"
            isError = true
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
                Text(stringResource(R.string.vector_menu))
            }
            Text(
                text = stringResource(R.string.vector_title),
                fontSize = 24.sp,
                modifier = Modifier.padding(end = 16.dp)
            )
            Spacer(modifier = Modifier.width(64.dp))
        }

        Spacer(modifier = Modifier.height(40.dp))

        OutlinedTextField(
            value = vectorAText,
            onValueChange = {
                vectorAText = it
                if (isError) {
                    isError = false
                    resultText = defaultResult
                }
            },
            label = { Text(stringResource(R.string.vector_a_label)) },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = vectorBText,
            onValueChange = {
                vectorBText = it
                if (isError) {
                    isError = false
                    resultText = defaultResult
                }
            },
            label = { Text(stringResource(R.string.vector_b_label)) },
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
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(stringResource(R.string.vector_sum))
            }
            Button(
                onClick = { performOperation { a, b -> subtraction(a, b) } },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(stringResource(R.string.vector_subtraction))
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
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(stringResource(R.string.vector_product))
            }
            Button(
                onClick = { performOperation { a, b -> distance(a, b) } },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(stringResource(R.string.vector_distance))
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = resultText,
            fontSize = 18.sp,
            color = if (isError) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.vector_format_hint),
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}