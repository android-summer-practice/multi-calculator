package com.itis.multi_calculator

import com.itis.multi_calculator.vector.VectorsScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.itis.multi_calculator.calculator.CalculatorScreen
import com.itis.multi_calculator.calculator.MenuScreen
import com.itis.multi_calculator.history.HistoryScreen
import com.itis.multi_calculator.si_converter.SiConverterScreen
import com.itis.multi_calculator.ui.theme.MulticalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MulticalculatorTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "menu",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("menu") {
                            MenuScreen(
                                onNavigateToCalculator = { navController.navigate("calculator") },
                                onNavigateToVectors = { navController.navigate("vectors") },
                                onNavigateToSi = { navController.navigate("si_converter") },
                                onNavigateToHistory = { navController.navigate("history") }
                            )
                        }
                        composable("calculator") {
                            CalculatorScreen(onMenuClick = { navController.popBackStack() })
                        }
                        composable("vectors") {
                            VectorsScreen(onMenuClick = { navController.popBackStack() })
                        }
                        composable("si_converter") {
                            SiConverterScreen(onMenuClick = { navController.popBackStack() })
                        }
                        composable("history") {
                            HistoryScreen(onMenuClick = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}
