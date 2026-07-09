package com.itis.multi_calculator.history

import androidx.compose.runtime.mutableStateListOf

object HistoryManager {
    private val _history = mutableStateListOf<HistoryItem>()
    val history: List<HistoryItem> get() = _history

    fun addHistoryItem(expression: String, result: String) {
        _history.add(0, HistoryItem(expression, result))
    }

    fun clearHistory() {
        _history.clear()
    }
}
