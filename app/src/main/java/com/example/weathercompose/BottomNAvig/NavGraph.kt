package com.example.weathercompose.BottomNAvig

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph

import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun NavGraph(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = "screen_1") {
        composable("screen_1") {
            Screen1()


        }
        composable("screen_2") {
            com.example.weathercompose.BottomNAvig.Screen2()
        }
        composable("screen_3") {
            Screen3()
        }
        composable("screen_4") {
            com.example.weathercompose.BottomNAvig.Screen4()
        }
    }
}