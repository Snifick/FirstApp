package com.example.weathercompose.BottomNAvig



import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.example.weathercompose.MainActivity
import com.example.weathercompose.Weather

@Composable
fun Screen1(){

   Weather(context =   LocalContext.current)
}
@Composable
fun Screen2(){
    Text(text = "Screen 2", modifier = Modifier
        .fillMaxSize()
        .wrapContentHeight(),
        textAlign = TextAlign.Center)
}
@Composable
fun Screen3(){
    Text(text = "Screen 3", modifier = Modifier
        .fillMaxSize()
        .wrapContentHeight(),
        textAlign = TextAlign.Center)
}
@Composable
fun Screen4(){
    Text(text = "Screen 4", modifier = Modifier
        .fillMaxSize()
        .wrapContentHeight(),
        textAlign = TextAlign.Center)
}