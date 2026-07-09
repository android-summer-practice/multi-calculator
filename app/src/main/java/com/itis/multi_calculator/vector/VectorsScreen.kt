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
import com.itis.multi_calculator.history.HistoryManager

@Composable
fun VectorsScreen(
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit
) {
    val context = LocalContext.current
    var vectorAText by remember { mutableStateOf("") }
    var vectorBText by remember { mutableStateOf("") }
    val defaultResult = stringResource(R.string.vector_default_result)
    var resultText by remember { mutableStateOf(defaultResult) }
    var isError by remember { mutableStateOf(false) }

    fun performOperation(opName: String, operation: (Vector2D, Vector2D) -> Any) {
        try {
            val a = parseVector(vectorAText, context)
            val b = parseVector(vectorBText, context)
            val result = operation(a, b)
            val resStr = result.toString()
            resultText = context.getString(R.string.vector_result, resStr)
            isError = false
            HistoryManager.addHistoryItem("$opName: $a, $b", resStr)
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
                onClick = onMenuClick,
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
            val sumText = stringResource(R.string.vector_sum)
            val subText = stringResource(R.string.vector_subtraction)
            Button(
                onClick = { performOperation(sumText) { a, b -> sum(a, b) } },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(sumText)
            }
            Button(
                onClick = { performOperation(subText) { a, b -> subtraction(a, b) } },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(subText)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val prodText = stringResource(R.string.vector_product)
            val distText = stringResource(R.string.vector_distance)
            Button(
                onClick = { performOperation(prodText) { a, b -> product(a, b) } },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 8.dp)
            ) {
                Text(prodText)
            }
            Button(
                onClick = { performOperation(distText) { a, b -> distance(a, b) } },
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(distText)
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