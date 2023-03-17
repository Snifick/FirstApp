package com.example.weathercompose.BottomNAvig

import android.annotation.SuppressLint
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScrean() {
    val navController = rememberNavController()
    Scaffold(

    ){
            NavGraph(navHostController = navController)
    }
}